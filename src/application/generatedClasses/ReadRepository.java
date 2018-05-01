package application.generatedClasses;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import runtime.application.*;

public class ReadRepository extends MongoDbReadRepository {

public ReadRepository(MongoDatabase db) {
		super(db);
	}

	public Vehicle getVehicleByColour(String colour) throws IOException {
	
		Document document = MongoDbApi.getDocument("Vehicle", "colour", colour, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Vehicle");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Vehicle returnedVehicle = mapper.readValue(documentJsonString, Vehicle.class);		
			return returnedVehicle;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	
	
	public Vehicle getVehicleById(String id) throws IOException {
	
		Document document = MongoDbApi.getDocument("Vehicle", "id", id, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Vehicle");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Vehicle returnedVehicle = mapper.readValue(documentJsonString, Vehicle.class);		
			return returnedVehicle;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	
	public Car getCarByNumberOfWheels(int numberOfWheels) throws IOException {
	
		Document document = MongoDbApi.getDocument("Car", "numberOfWheels", numberOfWheels, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Car");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Car returnedCar = mapper.readValue(documentJsonString, Car.class);		
			return returnedCar;
		} catch(IOException e ) {
			throw e;		
		} finally {
			BlockchainWriteRepository.unlockWrite();
		}	
	}
	
	
	public Car getCarById(String id) throws IOException {
	
		Document document = MongoDbApi.getDocument("Car", "id", id, db);
		
		if (document == null) {
			return null;
		}
		
		resolveReferences(document, db, "Car");
		
		@SuppressWarnings("deprecation") // TODO: this is a deprecated method, but should be okay for the project
		String documentJsonString = com.mongodb.util.JSON.serialize(document);

		ObjectMapper mapper = new ObjectMapper();

		BlockchainWriteRepository.lockWrite();

		try {
			Car returnedCar = mapper.readValue(documentJsonString, Car.class);		
			return returnedCar;
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
	public Person getPersonByHeightInCm(double heightInCm) throws IOException {
	
		Document document = MongoDbApi.getDocument("Person", "heightInCm", heightInCm, db);
		
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
	
	
	public Person getPersonById(String id) throws IOException {
	
		Document document = MongoDbApi.getDocument("Person", "id", id, db);
		
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
	
	
	public House getHouseById(String id) throws IOException {
	
		Document document = MongoDbApi.getDocument("House", "id", id, db);
		
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