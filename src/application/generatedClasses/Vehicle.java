
package application.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


public class Vehicle extends DatabaseItem {

	private String colour;
 

	public Vehicle() {}	
	
	public Vehicle(String colour) {
		super();
		
 
		this.colour = colour;	
	}
	
 

	public String getColour() {
		return this.colour;
	}
	
	public void setColour(String colour) throws Exception {
		this.colour = colour;
		
		blockchainWriteRepository.addItem(this, "colour", EventType.SET, colour);
	} 
	
}