
package tests.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;

// TRUE IS PASSED TO THE DATABASEITEM'S CONSTRUCTOR ONLY FOR THESE TEST CLASSES.

public class Person extends DatabaseItem {

	private String name;
	private String dateOfBirth;
	private House home;
	private String favouriteFood;

	
	public Person() {}	
	
	public Person(String name, String dateOfBirth) {
		super(true); 
		this.name = name;
	}
	
	public Person(String dateOfBirth) {
		super(true);
		this.name = name;
	}
	
	
	public Person( String name, String favouriteFood, House home) {
		super(true);
		
		this.favouriteFood = favouriteFood;
		this.name = name; 
		this.home = home;	
	}
	
	
	
	public Person( String name, String dateOfBirth, String favouriteFood, House home) {
		super(true);
		
		this.favouriteFood = favouriteFood;
		this.name = name; 
		this.dateOfBirth = dateOfBirth; 
		this.home = home;	
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDateOfBirth() {
		return this.dateOfBirth;
	}
	
	public House getHome() {
		return this.home;
	}
	
	public String getFavouriteFood() {
		return this.favouriteFood;
	}
	
	
	
	//SETTERS
	
	
	//attribute single
	public void setName(String name) throws Exception {
		this.name = name;
		
		blockchainWriteRepository.addItem(this, "name", EventType.SET, name);
	} 
	
	public void setDateOfBirth(String dateOfBirth) throws Exception {
		this.dateOfBirth = dateOfBirth;
		
		blockchainWriteRepository.addItem(this, "dateOfBirth", EventType.SET, dateOfBirth);
	} 
	
	
	//reference single
	public void setHome(House home) throws Exception {
		this.home = home;
		
		blockchainWriteRepository.addItem(this, "home", EventType.SET, home);
	} 
	
	public void setFavouriteFood(String favouriteFood) throws Exception {
		this.favouriteFood = favouriteFood;
		
		blockchainWriteRepository.addItem(this, "favoruiteFood", EventType.SET, favouriteFood);
		
	}
	
	//reference collection add single
	
	//reference collection add collection
	
	//reference collection remove single
	
	//reference collection remove multiple
	
	//attributes collection add single
	
	//attributes collection add collection
	
	//attributes collection remove single
	
	//attributes collection remove multiple
	
	
	
	
	
	
	
	
	
	

}