package runtime.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DatabaseItem {

	@JsonProperty("_id")
	protected String id;	
	protected boolean isTest = false;

	@JsonIgnore
	protected BlockchainWriteRepository blockchainWriteRepository;

	public DatabaseItem() {
		this.setId();
		
		 blockchainWriteRepository = new BlockchainWriteRepository();
	}
	
	// this constructor should only be used in the test classes to instantiate a new instance of blockchain write repository that uses test configuration settings
	public DatabaseItem(boolean isTest) {
		this.setId();
		this.isTest = isTest;
		
		 blockchainWriteRepository = new BlockchainWriteRepository(isTest);
	}

	public void writeWholeObjectToBlockchain() throws Exception {		
		blockchainWriteRepository.writeToDatabaseInitial(this);
	}

	public void setId() {
		this.id = UUID.randomUUID().toString();
	}

	public void setCustomId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void deleteItem() throws Exception {
		blockchainWriteRepository.deleteItem(this);
	}
}
