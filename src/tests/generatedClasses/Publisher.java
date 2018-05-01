package tests.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


//TRUE IS PASSED TO THE DATABASEITEM'S CONSTRUCTOR ONLY FOR THESE TEST CLASSES.

public class Publisher extends DatabaseItem {

	private String name;
	
	public Publisher() {}	
	
	public Publisher( String name) {
		super(true);
		this.name = name;	
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		
		blockchainWriteRepository.addItem(this, "name", EventType.SET, name);
	} 
}