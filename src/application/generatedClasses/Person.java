
package application.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


public class Person extends DatabaseItem {

	private String name;
	private double heightInCm;
	private House home;
 

	public Person() {}	
	
	public Person(String name,double heightInCm,House home) {
		super();
		
 
		this.name = name; 
		this.heightInCm = heightInCm; 
		this.home = home;	
	}
	
 

	public String getName() {
		return this.name;
	}
	
	public double getHeightInCm() {
		return this.heightInCm;
	}
	
	public House getHome() {
		return this.home;
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		
		blockchainWriteRepository.addItem(this, "name", EventType.SET, name);
	} 
	
	public void setHeightInCm(double heightInCm) throws Exception {
		this.heightInCm = heightInCm;
		
		blockchainWriteRepository.addItem(this, "heightInCm", EventType.SET, heightInCm);
	} 
	
	public void setHome(House home) throws Exception {
		this.home = home;
		
		blockchainWriteRepository.addItem(this, "home", EventType.SET, home);
	} 
	
}