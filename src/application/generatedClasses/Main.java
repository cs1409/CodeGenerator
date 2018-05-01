package application.generatedClasses;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class Main {
	
	private static final String DATABASENAME = "test_database";
	private static final String MONGOCLIENTURI = "mongodb://18.217.200.73:27017";

	public static void main(String[] args) throws Exception {
		

		Car car1 = new Car();
		car1.writeWholeObjectToBlockchain();
		car1.setNumberOfWheels(4);
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGOCLIENTURI));
		
		MongoDatabase database = mongoClient.getDatabase(DATABASENAME);
		
		ReadRepository readRepository = new ReadRepository(database);
		
		Car returnedCar = readRepository.getCarByNumberOfWheels(4);
		
		System.out.println(returnedCar.getNumberOfWheels());

	}

}
