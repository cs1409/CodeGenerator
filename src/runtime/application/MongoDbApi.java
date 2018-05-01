package runtime.application;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;


public class MongoDbApi {
	
	public static Document getDocument(String collectionName, String attributeName, Object value, MongoDatabase db) {
		
		MongoCollection<Document> collection = db.getCollection(collectionName);
		
		// currently, only one value matching the query is returned. TODO: For future work, a collection of matching results should be returned.
		FindIterable<Document> findIterable = collection.find(eq(attributeName, value));
		
		Document document = findIterable.first();
		
		if (document == null) {
			System.out.print("There is no item matching this name");  
			return null;
		}	
		return document;	
	}
}
