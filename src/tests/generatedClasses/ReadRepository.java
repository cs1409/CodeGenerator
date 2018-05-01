package tests.generatedClasses;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import runtime.application.BlockchainApi;
import runtime.application.EventType;
import tests.generatedClasses.Author;
import tests.generatedClasses.Book;
import tests.generatedClasses.House;
import tests.generatedClasses.Person;
import tests.generatedClasses.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import runtime.application.*;

// PLEASE NOTE: THE METHODS BELOW ONLY REPRESENT A SUBSET OF THE METHODS THAT WOULD BE GENERATED FROM AN EQUIVALENT DOMAIN MODEL

public class ReadRepository extends MongoDbReadRepository {

public ReadRepository(MongoDatabase db) {
		super(db);
	}

public ReadRepository(MongoDatabase db, boolean isTest) {
	super(db, isTest);
}

	public Book getBookByName(String name) throws IOException {
	
		Document document = MongoDbApi.getDocument("Book", "name", name, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Book");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Book returnedBook = mapper.readValue(documentJsonString, Book.class);		
			return returnedBook;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Book getBookByAuthor(Author author) throws IOException {
	
		Document document = MongoDbApi.getDocument("Book", "author", author, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Book");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Book returnedBook = mapper.readValue(documentJsonString, Book.class);		
			return returnedBook;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Book getBookByOwner(Person owner) throws IOException {
	
		Document document = MongoDbApi.getDocument("Book", "owner", owner, db);
		
		resolveReferences(document, db, "Book");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Book returnedBook = mapper.readValue(documentJsonString, Book.class);		
			return returnedBook;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Publisher getPublisherByName(String name) throws IOException {
	
		Document document = MongoDbApi.getDocument("Publisher", "name", name, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Publisher");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Publisher returnedPublisher = mapper.readValue(documentJsonString, Publisher.class);		
			return returnedPublisher;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Author getAuthorByName(String name) throws IOException {
	
		Document document = MongoDbApi.getDocument("Author", "name", name, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Author");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Author returnedAuthor = mapper.readValue(documentJsonString, Author.class);		
			return returnedAuthor;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	
	public Author getAuthorByAuthorName(String authorName) throws IOException {
		
		Document document = MongoDbApi.getDocument("Author", "authorName", authorName, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Author");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Author returnedAuthor = mapper.readValue(documentJsonString, Author.class);		
			return returnedAuthor;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	
	public Author getAuthorByDateOfBirth(String dateOfBirth) throws IOException {
	
		Document document = MongoDbApi.getDocument("Author", "dateOfBirth", dateOfBirth, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Author");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Author returnedAuthor = mapper.readValue(documentJsonString, Author.class);		
			return returnedAuthor;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Person getPersonByName(String name) throws IOException {
	
		Document document = MongoDbApi.getDocument("Person", "name", name, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Person");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Person returnedPerson = mapper.readValue(documentJsonString, Person.class);		
			return returnedPerson;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Person getPersonByDateOfBirth(String dateOfBirth) throws IOException {
	
		Document document = MongoDbApi.getDocument("Person", "dateOfBirth", dateOfBirth, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Person");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Person returnedPerson = mapper.readValue(documentJsonString, Person.class);		
			return returnedPerson;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public Person getPersonByHome(House home) throws IOException {
	
		Document document = MongoDbApi.getDocument("Person", "home", home, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Person");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Person returnedPerson = mapper.readValue(documentJsonString, Person.class);		
			return returnedPerson;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public House getHouseByAddress(String address) throws IOException {
	
		Document document = MongoDbApi.getDocument("House", "address", address, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "House");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			House returnedHouse = mapper.readValue(documentJsonString, House.class);		
			return returnedHouse;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	public House getHouseByNumberOfRooms(int numberOfRooms) throws IOException {
	
		Document document = MongoDbApi.getDocument("House", "numberOfRooms", numberOfRooms, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "House");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			House returnedHouse = mapper.readValue(documentJsonString, House.class);		
			return returnedHouse;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
}