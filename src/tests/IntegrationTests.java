package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import runtime.application.MongoDbReadRepository;
import tests.generatedClasses.Author;
import tests.generatedClasses.Book;
import tests.generatedClasses.House;
import tests.generatedClasses.Person;
import tests.generatedClasses.Publisher;
import tests.generatedClasses.ReadRepository;

class IntegrationTests {
	
	private static final String DATABASENAME = "test_database"; // DO NOT CHANGE
	private static final String MONGOCLIENTURI = "mongodb://18.217.200.73:27017";

	@Test
	void writeBookChangeBookGetBookTest() throws Exception {		
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
						
		// BUILDS OBJECTS
		Author testAuthor = new Author("Test Author 2");
		Publisher testPublisher3 = new Publisher("Test Publisher 3");
		Publisher testPublisher4 = new Publisher("Test Publisher 4");
		House testHouse = new House("Test Address 2", 10);
		Person testPerson = new Person("Test Person 3", "Chocolate", testHouse);
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		publishers.add(testPublisher3);
		publishers.add(testPublisher4);
			
		Book testBook = new Book("Test Book 2", testAuthor, 10, publishers, testPerson);
		
		// FIRST WRITE TO DATABASE
		testAuthor.writeWholeObjectToBlockchain();
		testPublisher3.writeWholeObjectToBlockchain();
		testPublisher4.writeWholeObjectToBlockchain();
		testHouse.writeWholeObjectToBlockchain();
		testPerson.writeWholeObjectToBlockchain();
		testBook.writeWholeObjectToBlockchain();
		
		// 
		Thread.sleep(1000); 		
		
		// READS MONGODB
		House returnedHouse = readRepository.getHouseByAddress("Test Address 2");
		Person returnedPerson = readRepository.getPersonByName("Test Person 3");
		Publisher returnedPublisher = readRepository.getPublisherByName("Test Publisher 3");
		
		Book returnedBook = readRepository.getBookByName("Test Book 2");
		
		// MODIFIES DATABASE/OBJECTS
		testBook.setCopies(2);
		testAuthor.setDateOfBirth("20/05/1981");
		testAuthor.setAuthorName("Test Author Name Modified");
		
		House testHouse2 = new House("Test Address 3", 4);
		testHouse2.writeWholeObjectToBlockchain();
		
		testPerson.setHome(testHouse2);
		
		Publisher testPublisher5 = new Publisher("Test Publisher 5");
		Publisher testPublisher6 = new Publisher("Test Publisher 6");
		Publisher testPublisher7 = new Publisher("Test Publisher 7");
		testPublisher5.writeWholeObjectToBlockchain();
		testPublisher6.writeWholeObjectToBlockchain();
		testPublisher7.writeWholeObjectToBlockchain();
		ArrayList<Publisher> publishersToAdd = new ArrayList<Publisher>();
		publishersToAdd.add(testPublisher5);
		publishersToAdd.add(testPublisher6);
		
		testBook.addPublishers(publishersToAdd);
		testBook.removePublisher(testPublisher3);
		testBook.addPublisher(testPublisher7);
			
		Thread.sleep(2000);
		
		// READS MONGODB AGAIN
		Book returnedBookModified = readRepository.getBookByName("Test Book 2");
		Person returnedPersonModified = readRepository.getPersonByName("Test Person 3");
		
		// BUILDS LIST FOR ASSERTIONS
		ArrayList<Publisher> publishersThatShouldBeContained = new ArrayList<Publisher>();
		publishersThatShouldBeContained.add(testPublisher4);
		publishersThatShouldBeContained.add(testPublisher5);
		publishersThatShouldBeContained.add(testPublisher6);
		publishersThatShouldBeContained.add(testPublisher7);

		// DROPS TEST COLLECTIONS
		MongoCollection<Document> bookColl = database.getCollection("Book");
		bookColl.drop();
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> publisherColl = database.getCollection("Publisher");
		publisherColl.drop();
		MongoCollection<Document> houseColl = database.getCollection("House");
		houseColl.drop();
		MongoCollection<Document> personColl = database.getCollection("Person");
		personColl.drop();
		
						
		// ASSERTIONS
		//assertTrue(returnedBookModified.getPublishers().containsAll(publishersThatShouldBeContained));
		assertTrue(returnedBook.getCopies() == 10 && returnedBookModified.getCopies() == 2);
		assertTrue(returnedBook.getOwner().getHome().getAddress().equals("Test Address 2") && returnedBookModified.getOwner().getHome().getAddress().equals("Test Address 3"));
		assertTrue(returnedBookModified.getAuthor().getDateOfBirth().equals("20/05/1981"));
		assertTrue(returnedBook.getName().equals("Test Book 2") && returnedBookModified.getName().equals("Test Book 2"));	
		assertTrue(returnedBook.getAuthor().getAuthorName().equals("Test Author 2") && returnedBookModified.getAuthor().getAuthorName().equals("Test Author Name Modified"));
		
		mongoClient.close();	
	}
	
	@Test
	void writeObjectModifyObjectGetObjectTest2() throws Exception {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
			
		Person testPerson = new Person("Test Person", "20/03/1996");
		testPerson.writeWholeObjectToBlockchain();
		
		Thread.sleep(2000);
	
		Person returnedPerson = readRepository.getPersonByName("Test Person");
		
		assertTrue(returnedPerson.getName().equals("Test Person") && returnedPerson.getFavouriteFood() == null && returnedPerson.getHome() == null);
		
		MongoCollection<Document> personColl = database.getCollection("Person");
		personColl.drop();	
		
		mongoClient.close();
	}
	
	@Test
	void writeObjectDeleteObjectTest() throws Exception {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
				
		Person testPerson = new Person("Test Person", "20/01/1995");
		testPerson.writeWholeObjectToBlockchain();
		
		ReadRepository readRepository = new ReadRepository(database, true);

		Thread.sleep(2000);

		
		Person returnedPerson = readRepository.getPersonByName("Test Person");
		
		returnedPerson.deleteItem();
		
		Thread.sleep(2000);
		
		Person returnedPerson2 = readRepository.getPersonByName("Test Person");
		
		MongoCollection<Document> personColl = database.getCollection("Person");
		personColl.drop();
		
		assertTrue(returnedPerson.getName().equals("Test Person") && returnedPerson2 == null);
		
		mongoClient.close();
	}

	@Test
	void setObjectAttributesBeforeWriteTest() throws Exception {
		
		Author testAuthor = new Author("Test Author 1");
		
		testAuthor.setDateOfBirth("20/09/1981");
		
		testAuthor.writeWholeObjectToBlockchain();
		
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
		
		Author returnedAuthor = readRepository.getAuthorByAuthorName("Test Author 1");
		
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		
		assertTrue(returnedAuthor.getDateOfBirth().equals("20/09/1981"));
	}

	@Test
	void inheritanceTest() throws Exception {
		
		House home = new House("Test Address", 8);
		home.writeWholeObjectToBlockchain();
		
		Author testAuthor = new Author("Test Author 1", "20/03/1996", "Test Name 2", home , "Cheese");
		testAuthor.writeWholeObjectToBlockchain();
		
		Thread.sleep(3000);
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
		
		Author returnedAuthor = readRepository.getAuthorByName("Test Name 2");
		
		MongoCollection<Document> authorColl = database.getCollection("Author");
		authorColl.drop();
		MongoCollection<Document> personColl = database.getCollection("Person");
		personColl.drop();
		MongoCollection<Document> houseColl = database.getCollection("House");
		houseColl.drop();
		
		mongoClient.close();
		
		assertTrue(returnedAuthor.getFavouriteFood().equals("Cheese"));
		assertTrue(returnedAuthor.getHome().getAddress().equals("Test Address"));
	}
}