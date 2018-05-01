package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import runtime.application.BlockchainApi;
import runtime.application.BlockchainWriteRepository;
import runtime.application.EventType;
import tests.generatedClasses.Author;
import tests.generatedClasses.Book;
import tests.generatedClasses.Publisher;

class DatabaseRepositoryTests {
	
	private static final String DATABASENAME = "test_database"; // DO NOT CHANGE
	private static final String MONGOCLIENTURI = "mongodb://18.217.200.73:27017";


	@Test
	void initialObjectConstructionTest1() throws Exception {
		
		Publisher publisher1 = new Publisher("Fromage");
		Publisher publisher2 = new Publisher("Brie");
		Publisher publisher3 = new Publisher("Chedder");
		publisher1.writeWholeObjectToBlockchain();
		publisher2.writeWholeObjectToBlockchain();
		publisher3.writeWholeObjectToBlockchain();
		String publisher1Id = publisher1.getId();
		String publisher2Id = publisher2.getId();
		String publisher3Id = publisher3.getId();
		
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		publishers.add(publisher1);
		publishers.add(publisher2);
		publishers.add(publisher3);
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();
		
		
		// returns the nested json object which contains the data		
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s", book1.getId(), EventType.INITIALCREATION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds the comparison json for book
		JSONObject comparisonJson = new JSONObject();
		comparisonJson.put("owner", null);
		comparisonJson.put("copies", 6);
		comparisonJson.put("author", author1.getId());
		comparisonJson.put("name", "Harry Potter");
		ArrayList<String> listOfPublisherReferences = new ArrayList<String>();
		listOfPublisherReferences.add(publisher1.getId());
		listOfPublisherReferences.add(publisher2.getId());
		listOfPublisherReferences.add(publisher3.getId());	
		comparisonJson.put("publishers", listOfPublisherReferences);
		
		// drops any data written to mongoDB
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
	
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonJson.toString()));		
	}
	
	@Test
	void initialObjectConstructionTest2() throws Exception {
		
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();

		
		// returns the nested json object which contains the data		
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s", book1.getId(), EventType.INITIALCREATION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds the comparison json for book
		JSONObject comparisonJson = new JSONObject();
		comparisonJson.put("owner", null);
		comparisonJson.put("copies", 6);
		comparisonJson.put("author",author1.getId());
		comparisonJson.put("name", "Harry Potter");
		ArrayList<Publisher> publishersToCompare = new ArrayList<Publisher>();
		comparisonJson.put("publishers", publishersToCompare);
		
		// drops any data written to mongoDB
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonJson.toString()));
	}
	
	// CREATES A SET ATTRIBUTE EVENT - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest1() throws NoSuchFieldException, SecurityException, Exception {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();
		
		book1.setName("Lord of the Rings");	
		
		// returns the nested json object which contains the data		
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_name", book1.getId(), EventType.SET), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds the comparison json
		JSONObject comparisonJson = new JSONObject();
		comparisonJson.put("name", "Lord of the Rings");
		
		// drops any data written to mongoDB
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonJson.toString()));	
	}
	
	// CREATES A SET REFERENCE EVENT - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest2() throws NoSuchFieldException, SecurityException, Exception {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();
		Author author2 = new Author("JK Tolkien", "1981/03/34");
		author2.writeWholeObjectToBlockchain();	
		book1.setAuthor(author2);
		
		// returns the nested json object which contains the data		
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_author", book1.getId(), EventType.SET), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds the comparison json
		JSONObject comparisonJson = new JSONObject();
		comparisonJson.put("author", author2.getId());
		
		// drops any data written to mongoDB
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonJson.toString()));	
	}
	
	// ADDS A COLLECTION OF REFERENCE TYPES TO A COLLECTION - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest3() throws NoSuchFieldException, SecurityException, Exception {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();			
		ArrayList<Publisher> publishersToAdd = new ArrayList<Publisher>();	
		Publisher publisher1 = new Publisher("Fromage");
		Publisher publisher2 = new Publisher("Brie");
		Publisher publisher3 = new Publisher("Chedder");	
		publishersToAdd.add(publisher1);
		publishersToAdd.add(publisher2);
		publishersToAdd.add(publisher3);	
		book1.addPublishers(publishersToAdd);
		
		// returns the nested json object
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_publishers", book1.getId(), EventType.ADDTOCOLLECTION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds comparison json object
		JSONObject comparisonObject = new JSONObject();
		ArrayList<String> listOfPublisherReferencesToCompare = new ArrayList<String>();
		listOfPublisherReferencesToCompare.add(publisher1.getId());
		listOfPublisherReferencesToCompare.add(publisher2.getId());
		listOfPublisherReferencesToCompare.add(publisher3.getId());	
		comparisonObject.put("publishers", listOfPublisherReferencesToCompare);
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();	
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonObject.toString()));	
	}
	
	// ADDS A SINGLE REFERENCE TYPE TO A COLLECTION - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest4() throws NoSuchFieldException, SecurityException, Exception {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>(); //need a constructor so i don't need to pass in a list all the time
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		author1.writeWholeObjectToBlockchain();
		book1.writeWholeObjectToBlockchain();	
		Publisher publisher1 = new Publisher("Brie");
		publisher1.writeWholeObjectToBlockchain();
		book1.addPublisher(publisher1);
		
		// returns the nested json object
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_publishers", book1.getId(), EventType.ADDTOCOLLECTION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds comparison json object
		JSONObject comparisonObject = new JSONObject();
		comparisonObject.put("publishers", publisher1.getId());
		
		// drops any mongo data
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonObject.toString()));
	}
	
	// REMOVES A SINGLE REFERENCE TYPE FROM A COLLECTION - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest5() throws NoSuchFieldException, SecurityException, Exception {
		
		Publisher publisher1 = new Publisher("Fromage");
		Publisher publisher2 = new Publisher("Brie");
		Publisher publisher3 = new Publisher("Chedder");
		publisher1.writeWholeObjectToBlockchain();
		publisher2.writeWholeObjectToBlockchain();
		publisher3.writeWholeObjectToBlockchain();	
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		publishers.add(publisher1);
		publishers.add(publisher2);
		publishers.add(publisher3);	
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();
		book1.removePublisher(publisher1); 
		
		// returns the nested json object
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_publishers", book1.getId(), EventType.REMOVEFROMCOLLECTION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
				
		// builds comparison json object
		JSONObject comparisonObject = new JSONObject();
		comparisonObject.put("publishers", publisher1.getId());
		
		// drops any mongo data
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));	
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);	
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonObject.toString()));		
	}
	
	// REMOVES A COLLECTIONS OF REFERENCE TYPES FROM A COLLECTION - CHECKS EVENT HAS BEEN WRITTEN ACCORDINGLY
	@Test
	void setAttributeOnObjectTest6() throws NoSuchFieldException, SecurityException, Exception {
		
		Publisher publisher1 = new Publisher("Fromage");
		Publisher publisher2 = new Publisher("Brie");
		Publisher publisher3 = new Publisher("Chedder");
		publisher1.writeWholeObjectToBlockchain();
		publisher2.writeWholeObjectToBlockchain();
		publisher3.writeWholeObjectToBlockchain();
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		publishers.add(publisher1);
		publishers.add(publisher2);
		publishers.add(publisher3);	
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		Book book1 = new Book("Harry Potter", author1, 6, publishers);
		book1.writeWholeObjectToBlockchain();
		ArrayList<Publisher> publishersToRemove = new ArrayList<Publisher>(); 
		publishersToRemove.add(publisher1);
		publishersToRemove.add(publisher2);
		book1.removePublishers(publishersToRemove);
		
		// returns the nested json object
		JSONArray returnedBookString = (JSONArray) BlockchainApi.getItems(String.format("Book_%s_%s_publishers", book1.getId(), EventType.REMOVEFROMCOLLECTION), "Book").get("result");
		JSONObject resultObject = (JSONObject) returnedBookString.get(0);
		JSONObject resultObject2 = (JSONObject) resultObject.get("data");
		JSONObject dataObject = (JSONObject) resultObject2.get("json");
		JSONObject dataObjectLowerLevel = (JSONObject) dataObject.get("data");
		
		// builds comparison json object
		JSONObject comparisonObject = new JSONObject();
		ArrayList<String> listOfPublisherReferencesToCompare = new ArrayList<String>();
		listOfPublisherReferencesToCompare.add(publisher1.getId());
		listOfPublisherReferencesToCompare.add(publisher2.getId());
		comparisonObject.put("publishers", listOfPublisherReferencesToCompare);
		
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		mongoClient.close();
		
		assertTrue(dataObjectLowerLevel.toString().equals(comparisonObject.toString()));
	}
	
	@Test
	void metadataGenerationTest() throws Exception {

		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.writeWholeObjectToBlockchain();
		
		BlockchainWriteRepository testRepository = new BlockchainWriteRepository();
		
		JSONObject metadata = testRepository.generateMetadata("name", author1, EventType.SET);
		
		assertTrue(metadata.toJSONString().equals(String.format("{\"_id\":\"%s\",\"attribute\":\"name\",\"event\":\"SET\",\"class\":\"Author\"}", author1.getId())));
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		mongoClient.close();
	}
	
	// KEYS ARE USED TO QUERY THE BLOCKCHAIN. DUE TO THE FACT THAT MULTICHAIN DOES NOT SUPPORT FUNCTIONALITY TO QUERY BY VALUE, KEY STRINGS MUST BE USED. STRING OPERATIONS ARE NOT IDEAL BUT THESE CAN BE DEPRECATED IF QUERY BY VALUE FUNCTIONALITY EMERGES
	@Test
	void keyGenerationTest() throws Exception {
		

		Book book1 = new Book();
		book1.setCustomId("97d942a8-90e4-44c5-9589-3ccc455d289c");
		
		BlockchainWriteRepository testRepository = new BlockchainWriteRepository(true);
			
		String key = testRepository.generateKey("name", book1, EventType.SET);

		
		assertTrue(key.equals("Book_97d942a8-90e4-44c5-9589-3ccc455d289c_SET_name"));
	}
	
	@Test
	void keyGenerationTest2() throws Exception {
		
		Author author1 = new Author("JK Rowling", "1968/03/01");
		author1.setCustomId("ec5af423-e330-4c16-b274-b7abcd76a706");
				
		BlockchainWriteRepository testRepository = new BlockchainWriteRepository(true);
		
		String key = testRepository.generateKey("dateOfBirth", author1, EventType.SET);
	
		assertTrue(key.equals("Author_ec5af423-e330-4c16-b274-b7abcd76a706_SET_dateOfBirth"));
	}
	
	@Test
	void keyGenerationTest3() throws Exception {

		Book book1 = new Book();
		book1.setCustomId("97d942a8-90e4-44c5-9589-3ccc455d289c");

		BlockchainWriteRepository testRepository = new BlockchainWriteRepository(true);
		
		String key = testRepository.generateKey("copies", book1, EventType.SET);
	
		assertTrue(key.equals("Book_97d942a8-90e4-44c5-9589-3ccc455d289c_SET_copies"));
	}
	
}
