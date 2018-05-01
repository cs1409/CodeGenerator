
package application.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


public class Car extends Vehicle {

	private int numberOfWheels;
	private Person owner;
 

	public Car() {}	
	
	public Car(int numberOfWheels,Person owner) {
		super();
		
 
		this.numberOfWheels = numberOfWheels; 
		this.owner = owner;	
	}
	
 	public Car(int numberOfWheels,Person owner, String colour){
		super( colour);
		
 
		this.numberOfWheels = numberOfWheels; 
		this.owner = owner;	
	} 

	public int getNumberOfWheels() {
		return this.numberOfWheels;
	}
	
	public Person getOwner() {
		return this.owner;
	}
	
	public void setNumberOfWheels(int numberOfWheels) throws Exception {
		this.numberOfWheels = numberOfWheels;
		
		blockchainWriteRepository.addItem(this, "numberOfWheels", EventType.SET, numberOfWheels);
	} 
	
	public void setOwner(Person owner) throws Exception {
		this.owner = owner;
		
		blockchainWriteRepository.addItem(this, "owner", EventType.SET, owner);
	} 
	
}