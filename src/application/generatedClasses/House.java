
package application.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


public class House extends DatabaseItem {

	private String address;
	private int numberOfRooms;
 

	public House() {}	
	
	public House(String address,int numberOfRooms) {
		super();
		
 
		this.address = address; 
		this.numberOfRooms = numberOfRooms;	
	}
	
 

	public String getAddress() {
		return this.address;
	}
	
	public int getNumberOfRooms() {
		return this.numberOfRooms;
	}
	
	public void setAddress(String address) throws Exception {
		this.address = address;
		
		blockchainWriteRepository.addItem(this, "address", EventType.SET, address);
	} 
	
	public void setNumberOfRooms(int numberOfRooms) throws Exception {
		this.numberOfRooms = numberOfRooms;
		
		blockchainWriteRepository.addItem(this, "numberOfRooms", EventType.SET, numberOfRooms);
	} 
	
}