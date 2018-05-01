package runtime.application;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.*;

import runtime.application.*;
import tests.generatedClasses.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;


public class MongoDbReadRepository {

	protected MongoDatabase db;
	protected boolean isTest = false;
	private int recursionLevel = 0; 

	public MongoDbReadRepository(MongoDatabase db) {
		this.db = db;
	}

	public MongoDbReadRepository(MongoDatabase db, boolean isTest) {
		this.isTest = isTest;
		this.db = db;	
	}

	protected void resolveReferences(Document document, MongoDatabase db, String collectionName)  {

		for (Iterator iterator = document.keySet().iterator(); iterator.hasNext();) {
			
			String key = (String) iterator.next();
			
			Class<?> classOfCollection = null;
			try {
				String classPackage = isTest ? "tests.generatedClasses." : "application.generatedClasses.";
				classOfCollection = Class.forName(classPackage + collectionName); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Field[] classAttributes = classOfCollection.getDeclaredFields();
			Field[] superClassAttributes = classOfCollection.getSuperclass().getDeclaredFields();
			Field[] allAttributes = ArrayUtils.addAll(classAttributes, superClassAttributes);
			
			// this for loop is responsible for comparing the attribute of the class to the attribute of the mongoDB document. From this, it can find the type of the attribute. For example, if home is a reference attribute of Person, with type House, there is no link between attribute type 'home' and class House in mongoDB. The below code will find the the class for Person and check to see the type of 'home' 
			for (Field attribute: allAttributes) {				
				if (key.equals(attribute.getName())) {	
					try {
						// this block of code is to find the parameterized type of a collection
						Type genericFieldType = attribute.getGenericType();
						Type attributeType = attribute.getType();
						if(genericFieldType instanceof ParameterizedType){
							ParameterizedType aType = (ParameterizedType) genericFieldType;
							Type[] fieldArgTypes = aType.getActualTypeArguments();
							attributeType = fieldArgTypes[0];
						}
						boolean isInstance = isTest ? tests.generatedClasses.IsInstanceHelper.isClass(attributeType):application.generatedClasses.IsInstanceHelper.isClass(attributeType); // CHANGE THE PATH IF THE GENERATED CLASSES ARE MOVED ELSEHWHERE

						if (isInstance) {

							Object reference = document.get(key);

							if (reference == null) {
								continue;
							}

							MongoCollection<Document> referenceCollection = null;

							referenceCollection = db.getCollection(attributeType.toString().substring(attributeType.toString().lastIndexOf('.') + 1));

							if (Collection.class.isAssignableFrom(reference.getClass())) {

								ArrayList<Object> referenceArray = (ArrayList<Object>) reference;

								ArrayList<Object> referenceDocumentArray = new ArrayList<Object>();

								for (Object singleReference: referenceArray) {
													
									FindIterable<Document> referenceFindIterable = referenceCollection.find(eq("_id", singleReference));

									Document referenceDocument = referenceFindIterable.first();

									if (referenceDocument == null) {
										System.out.println("A reference has not been found in the database.");
										continue;
									}
									String referenceCollectionName = referenceCollection.getNamespace().toString().substring(referenceCollection.getNamespace().toString().lastIndexOf('.') + 1);		
									
									if (recursionLevel < 3) { // The upper bound on the recursion level dictates how many levels of object references will be returned. Increasing this number will slow performance but increase the amount of relations mapped to objects.
										recursionLevel++;
										resolveReferences(referenceDocument, db, referenceCollectionName);
										recursionLevel--;

									} else {
										referenceDocument = null;
									}

									referenceDocumentArray.add(referenceDocument);
									document.put(key, referenceDocumentArray);
									
								}
							} else {

								FindIterable<Document> referenceFindIterable = referenceCollection.find(eq("_id", reference));

								Document referenceDocument = referenceFindIterable.first();

								if (referenceDocument == null) {
									System.out.println("A reference has not been found in the database.");
									document.replace(key, null);
									continue;
								}
								String referenceCollectionName = referenceCollection.getNamespace().toString().substring(referenceCollection.getNamespace().toString().lastIndexOf('.') + 1);								
								
								if (recursionLevel < 3) { // The upper bound on the recursion level dictates how many levels of object references will be returned. Increasing this number will slow performance but increase the amount of relations mapped to objects.
									recursionLevel++;
									resolveReferences(referenceDocument, db, referenceCollectionName);
									recursionLevel--;
								} else {
									referenceDocument = null;
								}
								
								document.put(key, referenceDocument);	

							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}	
			}
		}
	}	
}








