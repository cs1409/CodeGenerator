package runtime.application;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.generatedClasses.*;


public class BlockchainWriteRepository {

	private static final String CHAIN = "chain1";
	private static boolean locked; 
	private boolean isTest = false;

	public BlockchainWriteRepository() {}

	public BlockchainWriteRepository(boolean isTest) {
		this.isTest = isTest;
	}

	// This methods loops through each attribute and super class attribute of an object, each time calling addItemInitial. The result of addItemInitial is stored in a blockchain event (called metadata) under the JSON key "data".
	public void writeToDatabaseInitial(Object object) throws Exception {
		if (!locked) {
			Field[] superAttributes = object.getClass().getSuperclass().getDeclaredFields();
			Field[] attributes = object.getClass().getDeclaredFields();

			JSONObject dataObject = new JSONObject();

			for (Field attribute : attributes) {
				dataObject.put(attribute.getName(), addItemInitial(object, attribute.getName(), false));
			}

			for (Field superAttribute : superAttributes) {
				if (superAttribute.getName().equals("id") || superAttribute.getType().equals(BlockchainWriteRepository.class) || superAttribute.getName().equals("isTest")) {
					continue;
				} else {
					dataObject.put(superAttribute.getName(), addItemInitial(object, superAttribute.getName(), true));
				}
			}

			JSONObject metadata = generateMetadata(null, object, EventType.INITIALCREATION);
			String key = generateKey(null, object, EventType.INITIALCREATION); // USEFUL FOR TESTING AND BLOCKCHAIN FETCHING

			JSONObject blockchainCheck = BlockchainApi.getItems(key, metadata.get("class").toString());
			JSONArray result = (JSONArray) blockchainCheck.get("result");

			if (result == null) {
				System.out.println("An error has occured. Check if a stream exists on Multichain and the node is subscribed");
			} else {
				if (result.isEmpty()) {
					metadata.put("data", dataObject);

					BlockchainApi.addStreamItem(key, metadata, CHAIN, metadata.get("class").toString(), EventType.INITIALCREATION);
				} else {
					System.out.println("Object already written to blockchain. To modify object attributes, use set methods.");
				}
			}
		}
	}

	// This method is called when a set method on an object is invoked. It creates a new blockchain event with the new value
	public void addItem(Object object, String attributeName, EventType event, Object value) throws Exception {
		if (!locked) {
			String key = generateKey(attributeName, object, event);
			JSONObject metadata = generateMetadata(attributeName, object, event);
			JSONObject dataObject = new JSONObject();

			// These block, along with the following if statement, checks if the object has initially been written to the blockchain using writeWholeObjectToBlockchain()
			String keyForBlockchainCheck = generateKey(null, object, EventType.INITIALCREATION);
			JSONObject blockchainCheck = BlockchainApi.getItems(keyForBlockchainCheck, metadata.get("class").toString());
			JSONArray result = (JSONArray) blockchainCheck.get("result");

			if (result.isEmpty()) {
				System.out.println("NOTE: Set attribute has not been updated on the blockchain as the object has not been previously written. Object must be written initially using writeWholeObjectToBlockchain.\n");
			} else {
				String className = object.getClass().getName().substring(object.getClass().getName().lastIndexOf('.') + 1);		
				if (Collection.class.isAssignableFrom(value.getClass())) {
					ArrayList<Object> valueArray = (ArrayList<Object>) value;

					Object arrayValue = valueArray.get(0);
					boolean isInstance = isTest ? tests.generatedClasses.IsInstanceHelper.isInstance(arrayValue) : application.generatedClasses.IsInstanceHelper.isInstance(arrayValue); // checks if the value type is an instance of one of the generated classes CHANGE THE PATH IF THE CLASSES ARE MOVED ELSEWHERE
					if (isInstance)  {
						// collection of references
						ArrayList<Object> arrayOfValues = (ArrayList<Object>) valueArray;
						ArrayList<Object> newArrayOfValues = new ArrayList<Object>();
						for (Object entry: arrayOfValues) {
							if (entry == null) {
								continue;
							}
							newArrayOfValues.add(entry.getClass().getMethod("getId", null).invoke(entry, null));	
							dataObject.put(attributeName, newArrayOfValues);
						}			
						metadata.put("data", dataObject);
						BlockchainApi.addStreamItem(key, metadata, CHAIN, className, event);
					} else {
						// collection of primitives
						ArrayList<Object> arrayOfValues = (ArrayList<Object>) valueArray;
						dataObject.put(attributeName, arrayOfValues);
						metadata.put("data", dataObject);
						BlockchainApi.addStreamItem(key, metadata, CHAIN, className, event);
					}
				} else {
					boolean isInstance = isTest ? tests.generatedClasses.IsInstanceHelper.isInstance(value) : application.generatedClasses.IsInstanceHelper.isInstance(value);  // checks if the value type is an instance of one of the generated classes CHANGE THE PATH IF THE CLASSES ARE MOVED ELSEWHERE
					if (isInstance) {
						//single reference
						String referenceTypeGuid = value.getClass().getMethod("getId", null).invoke(value, null).toString();
						dataObject.put(attributeName, referenceTypeGuid);
						metadata.put("data", dataObject);
						BlockchainApi.addStreamItem(key, metadata, CHAIN, className, event);
					} else {
						// single primitive
						dataObject.put(attributeName, value);
						metadata.put("data", dataObject);
						BlockchainApi.addStreamItem(key, metadata, CHAIN, className, event);
					}
				}
			}
		}	
	}

	public void deleteItem(Object object) throws Exception {

		String className = object.getClass().getName().substring(object.getClass().getName().lastIndexOf('.') + 1);

		String key = generateKey(null, object, EventType.DELETE);
		JSONObject metadata = generateMetadata(null, object, EventType.DELETE);
		JSONObject dataObject = new JSONObject();
		metadata.put("data", dataObject);

		BlockchainApi.addStreamItem(key, metadata, CHAIN, className, EventType.DELETE);
	}

	private Object addItemInitial(Object object, String attributeName, boolean isSuperClass) throws Exception {

		Object attributeValue = isSuperClass ? object.getClass().getSuperclass().getMethod(String.format("get%s", attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1)), null).invoke(object, null): object.getClass().getMethod(String.format("get%s", attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1)), null).invoke(object, null) ;

		if (attributeValue == null) {
			return null;
		}

		if (Collection.class.isAssignableFrom(attributeValue.getClass())) {

			ArrayList<Object> valueArray = (ArrayList<Object>) attributeValue;

			if (valueArray.isEmpty()) {
				return new ArrayList<>();
			}
			Object arrayValue = valueArray.get(0);
			boolean isInstance = isTest ?  tests.generatedClasses.IsInstanceHelper.isInstance(arrayValue) : application.generatedClasses.IsInstanceHelper.isInstance(arrayValue);
			if (isInstance)  { 
				// collection of references
				ArrayList<Object> arrayOfValues = (ArrayList<Object>) valueArray;
				ArrayList<Object> newArrayOfValues = new ArrayList<Object>();
				for (Object entry: arrayOfValues) {
					if (entry == null) {
						continue;
					}
					newArrayOfValues.add(entry.getClass().getMethod("getId", null).invoke(entry, null));					
				}			
				return newArrayOfValues;
			} else {
				// collection of primitives
				ArrayList<Object> arrayOfValues = (ArrayList<Object>) valueArray;
				return arrayOfValues;
			}
		} else {
			boolean isInstance = isTest ? tests.generatedClasses.IsInstanceHelper.isInstance(attributeValue) : application.generatedClasses.IsInstanceHelper.isInstance(attributeValue);
			if (isInstance) {
				//single reference
				String referenceTypeGuid = attributeValue.getClass().getMethod("getId", null).invoke(attributeValue, null).toString();
				return referenceTypeGuid;
			} else {
				return attributeValue;
			}
		}
	}

	// this method generates the data for the blockchain event. the data object gets inserted into the metadata object in the methods that call this method
	public JSONObject generateMetadata(String attributeName, Object object, EventType event) throws Exception {

		String className = object.getClass().getName().substring(object.getClass().getName().lastIndexOf('.') + 1);

		JSONObject jsonObject = new JSONObject();

		Method nameOfGetGuidMethod;
		try {
			nameOfGetGuidMethod = object.getClass().getMethod("getId", null);
			try {
				String objectGuid = nameOfGetGuidMethod.invoke(object, null).toString();
				jsonObject.put("_id", objectGuid);
				jsonObject.put("class", className);
				jsonObject.put("event", event.toString());
				if (event != EventType.INITIALCREATION || event != EventType.DELETE) {
					jsonObject.put("attribute", attributeName);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw e;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw e;
		}
		return jsonObject;
	}

	// given an instance and its attribute name, a key will be appropriately generated
	// keys are used for retrieval of events from the blockchain. When (and if) Multichain add query by value functionality, this could be deprecated
	public String generateKey(String attributeName, Object object, EventType event) throws Exception {

		String className = object.getClass().getName().substring(object.getClass().getName().lastIndexOf('.') + 1);

		Method nameOfGetGuidMethod;
		try {
			nameOfGetGuidMethod = object.getClass().getMethod("getId", null);
			try {
				String objectGuid = nameOfGetGuidMethod.invoke(object, null).toString();
				if (attributeName != null && event != EventType.INITIALCREATION) {
					String key = String.format("%s_%s_%s_%s", className, objectGuid, event.toString(), attributeName);
					return key;
				} else {
					String key = String.format("%s_%s_%s", className, objectGuid, event.toString());
					return key;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw e;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// these can be called to temporarily stop writing to the blockchain (and therefore the state database) for whatever reason
	public static void lockWrite() {		
		locked = true;	
	}

	public static void unlockWrite() {	
		locked = false;
	}
}
