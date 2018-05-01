package tests.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;


public class Author extends Person {

	private String authorName;

	public Author() {}	
	
	public Author( String authorName) {
		super();
		this.authorName = authorName; 
	}
	
	public Author(String authorName, String dateOfBirth) {
		super(dateOfBirth);
		this.authorName = authorName; 
	}
	
	public Author( String authorName, String dateOfBirth, String name, House home, String favouriteFood) {
		super(name, dateOfBirth, favouriteFood, home);
		this.authorName = authorName; 
	}
	
	public String getAuthorName() {
		return this.authorName;
	}
	
	public void setAuthorName(String authorName) throws Exception {
		this.authorName = authorName;
		
		blockchainWriteRepository.addItem(this, "authorName", EventType.SET, authorName);
	} 
}