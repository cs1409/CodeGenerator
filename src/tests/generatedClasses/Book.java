
package tests.generatedClasses;

import runtime.application.*;
import java.util.ArrayList;

//TRUE IS PASSED TO THE DATABASEITEM'S CONSTRUCTOR ONLY FOR THESE TEST CLASSES.

public class Book extends DatabaseItem {
 
	private ArrayList<Publisher> publishers;	// non collections
	private String name;
	private int copies;
	private Author author;
	private Person owner;

	
	public Book() {}	
	
	public Book(String name, ArrayList<Publisher> publishers) {
		super(true);
		this.publishers = publishers;		
		this.name = name; 
	}
	
	public Book(String name, Author author, int copies, ArrayList<Publisher> publishers) {
		super(true);
		this.publishers = publishers;		
		this.name = name; 
		this.copies = copies; 
		this.author = author; 
	}
	
	public Book(String name, Author author, int copies, ArrayList<Publisher> publishers, Person owner) {
		super(true);
		this.publishers = publishers;		
		this.name = name; 
		this.copies = copies; 
		this.author = author; 
		this.owner = owner;	
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCopies() {
		return this.copies;
	}
	
	public Author getAuthor() {
		return this.author;
	}
	
	public Person getOwner() {
		return this.owner;
	}
	
	
	public ArrayList<Publisher> getPublishers() {
		return this.publishers;
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		
		blockchainWriteRepository.addItem(this, "name", EventType.SET, name);
	} 
	
	public void setCopies(int copies) throws Exception {
		this.copies = copies;
		
		blockchainWriteRepository.addItem(this, "copies", EventType.SET, copies);
	} 
	
	
	public void setAuthor(Author author) throws Exception {
		this.author = author;
		
		blockchainWriteRepository.addItem(this, "author", EventType.SET, author);
	} 
	
	public void setOwner(Person owner) throws Exception {
		this.owner = owner;
		
		blockchainWriteRepository.addItem(this, "owner", EventType.SET, owner);
	} 
	
	

	public void addPublisher(Publisher publisher) throws Exception {
		this.publishers.add(publisher);
		
		blockchainWriteRepository.addItem(this, "publishers", EventType.ADDTOCOLLECTION, publisher);
	} 
	
	
	//reference collection add collection
	public void addPublishers(ArrayList<Publisher> publishers) throws Exception {
		this.publishers.addAll(publishers);
		
		blockchainWriteRepository.addItem(this, "publishers", EventType.ADDTOCOLLECTION, publishers);
	} 
	
	
	//reference collection remove single
	public void removePublisher(Publisher publisher) throws Exception {
		this.publishers.remove(publisher);
		
		blockchainWriteRepository.addItem(this, "publishers", EventType.REMOVEFROMCOLLECTION, publisher);
	} 
	
	
	//reference collection remove multiple
	public void removePublishers(ArrayList<Publisher> publishers) throws Exception {
		this.publishers.removeAll(publishers);
		
		blockchainWriteRepository.addItem(this, "publishers", EventType.REMOVEFROMCOLLECTION, publishers);
	} 
	
	
	//attributes collection add single
	
	//attributes collection add collection
	
	//attributes collection remove single
	
	//attributes collection remove multiple
	
	
	
	
	
	
	
	
	
	

}