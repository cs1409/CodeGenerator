package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import runtime.application.MongoDbReadRepository;
import tests.generatedClasses.Author;
import tests.generatedClasses.Book;
import tests.generatedClasses.House;
import tests.generatedClasses.Person;
import tests.generatedClasses.Publisher;
import tests.generatedClasses.ReadRepository;

class MongoDbReadRepositoryTests {
	
	private static final String DATABASENAME = "unit_test_database"; // DO NOT CHANGE
	private static final String MONGOCLIENTURI = "mongodb://18.217.200.73:27017";

	@Test
	void getAuthorTest() throws Exception {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
		
		Author returnedAuthor= readRepository.getAuthorByName("Test Author");
		
		Author testAuthor = new Author("Test Author", "20/03/1996");
		testAuthor.setCustomId("testAuthor1"); 
		
		assertTrue(returnedAuthor.getName().equals(testAuthor.getName()));
		assertTrue(returnedAuthor.getId().equals(testAuthor.getId()));
		assertTrue(returnedAuthor.getDateOfBirth().equals(testAuthor.getDateOfBirth()));
		
		mongoClient.close();
	}
	
	@Test
	void getPublisherTest() throws IOException {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
		
		Publisher returnedPublisher = readRepository.getPublisherByName("Test Publisher 1");
		
		Publisher testPublisher = new Publisher("Test Publisher 1");
		testPublisher.setCustomId("testPublisher1");
		
		assertTrue(returnedPublisher.getName().equals(testPublisher.getName()));
		assertTrue(returnedPublisher.getId().equals(testPublisher.getId()));
		
		mongoClient.close();
	}
	
	@Test
	void getPersonTest() throws IOException {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);
		
		Person returnedPerson = readRepository.getPersonByName("Test Person");
		
		House testHouse = new House("Test Address", 10);
		testHouse.setCustomId("testHouse1");
		
		Person testPerson = new Person("Test Person", "Chocolate", testHouse);
		testPerson.setCustomId("testPerson1");
		
		assertTrue(returnedPerson.getHome().getAddress().equals(testPerson.getHome().getAddress()));
		assertTrue(returnedPerson.getHome().getNumberOfRooms() == testPerson.getHome().getNumberOfRooms());
		assertTrue(returnedPerson.getHome().getId().equals(testPerson.getHome().getId()));		
		assertTrue(returnedPerson.getFavouriteFood().equals(testPerson.getFavouriteFood()));
		assertTrue(returnedPerson.getId().equals(testPerson.getId()));
		assertTrue(returnedPerson.getName().equals(testPerson.getName()));
		
		mongoClient.close();
	}
	
	@Test
	void getHomeTest() throws IOException {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
			
		ReadRepository readRepository = new ReadRepository(database, true);

		House returnedHouse = readRepository.getHouseByAddress("Test Address");
		
		House testHouse = new House("Test Address", 10);
		testHouse.setCustomId("testHouse1");
		
		assertTrue(returnedHouse.getAddress().equals(testHouse.getAddress())); 
		assertTrue(returnedHouse.getId().equals(testHouse.getId()));
		assertTrue(returnedHouse.getNumberOfRooms() == testHouse.getNumberOfRooms());
		
		mongoClient.close();
	}
	
	@Test
	void getBookTest() throws Exception {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database, true);

		
		Book returnedBook = readRepository.getBookByName("Test Book");
		
		Author testAuthor = new Author("Test Author", "20/03/1971");
		testAuthor.setCustomId("test2");	
		Publisher testPublisher1 = new Publisher("Test Publisher 1");
		testPublisher1.setCustomId("testPublisher1");	
		Publisher testPublisher2 = new Publisher("Test Publisher 2");
		testPublisher2.setCustomId("testPublisher2");	
		House testHouse = new House("Test Address", 10);
		testHouse.setCustomId("testHouse1");	
		Person testPerson = new Person("Test Person", "Chocolate", testHouse);
		testPerson.setCustomId("testPerson1");
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		publishers.add(testPublisher1);
		publishers.add(testPublisher2);
		
		Book testBook = new Book("Test Book", testAuthor, 10, publishers, testPerson);
		
		ArrayList<Publisher> returnedBookPubs = returnedBook.getPublishers();
		Publisher returnedBookPub0 = returnedBook.getPublishers().get(0);
		String returnedBookPub0Name = returnedBook.getPublishers().get(0).getName();

		ArrayList<Publisher> testBookPubs = testBook.getPublishers();
		Publisher testBookPub0 = testBook.getPublishers().get(0);
		String tetsBookPub0Name = testBook.getPublishers().get(0).getName();
		
		assertTrue(returnedBook.getOwner().getFavouriteFood().equals(testBook.getOwner().getFavouriteFood()));
		assertTrue(returnedBook.getOwner().getHome().getAddress().equals(testBook.getOwner().getHome().getAddress()));
		assertTrue(returnedBook.getName().equals(testBook.getName()));
		assertTrue(returnedBook.getCopies() == testBook.getCopies());
		assertTrue(returnedBook.getPublishers().get(0).getName().equals(testBook.getPublishers().get(0).getName()) && returnedBook.getPublishers().get(1).getName().equals(testBook.getPublishers().get(1).getName()));
		assertTrue(returnedBook.getAuthor().getName().equals(testBook.getAuthor().getName()));
		
		mongoClient.close();
	}


}

//DATABASE INSERT SCRIPTS
//db.Author.insert({"_id":"testAuthor1", "name":"Test Author", "dateOfBirth":"20/03/1996"})
//db.Person.insert({"_id":"testPerson1","name":"Test Person","favouriteFood":"Chocolate", "home":"testHouse1"})
//db.House.insert({"_id": "testHouse1",  "address":"Test Address", "numberOfRooms":10})
//db.Publisher.insert({"_id":"testPublisher1", "name":"Test Publisher 1"})
//db.Publisher.insert({"_id":"testPublisher2", "name":"Test Publisher 2"})
//db.Book.insert({"_id":"testBook1", "name":"Test Book", "author":"testAuthor1", "copies":10, "publishers": ["testPublisher1", "testPublisher2"], "owner":"testPerson1"})