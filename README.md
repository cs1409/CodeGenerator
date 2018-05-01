# CodeGenerator
How To Use
To modify the MongoDB database that move_to_mong.jar writes to, edit the argument given to move_to_mongo.jar in script_move_to_mongo.jar. By default, it is ‘test_database’, but you can use any string you would like. (the database will be automatically created in MongoDB)
Setting up the server Locally:

1. Create a new Amazon Web Service instance running Ubuntu with a volume size of 20GB.

2. Once launched, use SSH File Transfer Protocol (I recommend the FileZilla SFTP Client for
ease of use) to move the following three files into the home directory of the AWS instance:
- script_set_up_server.sh
- script_move_to_mongo.sh - move_to_mongo.jar

On the server:

3. Run the script

This will download and install the required version of JDK & JRE, MultiChain and MongoDB. It will initialise MongoDB process.

If you are setting up a second server that will connect to an existing chain, follow step 4b. If you are starting your first chain, follow step 4a.

4a. Run these two commands :

     multichain-util create chain1
     
     multichaind chain1 -daemon
     
4b. Execute the following command:

     multichaind chain1@[ip-address]:[port]
     
You should be told that the blockchain was successfully initialized, but you do not have permission to connect. You should also be shown a message containing an address in this node’s wallet. Note the address of the wallet, you will need it for the next step.

Back on the first server

multichain-cli chain1 grant [walletaddress] connect

On the second server

   multichaind chain1 -daemon
   
5. Navigate to ~/.multichain/chain1/params.dat and note the default- rpc-port and default- network-port. Edit these parameters to suite your needs (use default for testing)
Run these five commands:

echo “allowrpc = [default-rpc-port from
params.dat] >>

~/.multichain/chain1/multichain.conf
echo “allowrpc = [default-connection-port from
params.dat] >>

~/.multichain/chain1/multichain.conf

echo “allowip = [public IP address of the application’s computer] >> ~/.multichain/chain1/multichain.conf

echo "walletnotify=/home/ubuntu/script_move_to_mongo.sh %j %c" >> ~/.multichain/chain1/multichain.conf
6. 7. 

8.

Open these ports on the Amazon Web Services instance located under Security Groups. Use this command to edit the mongo config file using vim

   sudo vim /etc/mongo.conf
   
Change bindIp (located under #networkinterfaces) to either 0.0.0.0 (WARNING: this opens up the MongoDB server to all IP connections) or the public IP address of the application’s computer.

8. Set up the desired streams according to the generated classes and subscribe to them. You only need to set up the streams on one of the nodes, but you have to subscribe to each stream on every node.

multichain-cli chain1 create stream [stream name] multiChain-cli chain1 subscribe [stream name]

9. Restart your MultiChain process to ensure all these changes take effect. Stop:

        multichain-cli chain1 stop
Start:
        multichaind chain1 -daemon
        
10. To change the database that move_to_mongo.jar writes to, edit the argument given to move_to_mongo.jar in the bash script script_move_to_mongo.jar. The default is ‘test_database’. When using the test classes, use ‘test_database’.

11. To enable mining (optional): On the first server run

        multichain-cli chain1 grant [walletaddress] mine
        
On the second server, check that two permitted miners are listed: 
listpermissions mine

Run this on both servers to maximize the degree of miner randomness: 

setruntimeparam miningturnover 1

Now wait for a couple of minutes, so that a few blocks are mined. (This assumes you left the block time on the default of 15 seconds.) On either server, check the miners of the last few blocks:

listblocks -10

Contact: cs1409@york.ac.uk if further assistance is required


Using the application:

To generate the desired classes, pass in the location of a .ecore file as an argument to the CodeGenerator.java class. This will generate a set of classes in the same directory as the .ecore file in a folder titled according to the package name on the Ecore model.

Move these Java classes into a package called application.generatorClasses in the same project as the application.runtime. If the classes are placed elsewhere, be sure to change three references to this package in the runtime classes:
Change application.generatedClasses NOT tests.generatedClasses

1. BlockchainWriteRepository, line 85 and line 107

2. MongoDbReadRepository, line 69

Once this is done, you are ready to start creating your objects and writing them to the blockchain.
Create a new class with a main method.

Instantiate new objects and set their properties like below:

Once writeWholeObjectToBlockchain() has been called, all further modifications on that set object will be written to the blockchain immediately. Before writeWholeObjectToBlockchain() is called, the set methods do not write to the blockchain. This is because the object does not yet exist in the blockchain or the state database.
 
To query the blockchain, call

BlockchainApi.getItems(...)
with the name of the stream (the object type) you are querying, and with the key you are looking for. Keys are formatted as so:
[ObjectType]_[GUID]_[EventType]_[Attribute]

For example:
Book_3929fds38482daad_SET_name

Will return the blockchain event where the name has been set.

To query the blockchain for initial creations of objects (written using writeWholeObjectToBlockchain()) as opposed to set events, attribute is unnessary.

[ObjectType]_[GUID]_INITIALCREATION
This will return the event for the initial creation of that object.
The blockchain event types are:
SET
INITIAL CREATION
ADDTOCOLLECTION
REMOVEFROMCOLLECTION
DELETE

When Multichain adds improved querying ability, this functionality could be greatly expanded complete with a BlockchainReadRepository.

To read from the state database, create an instance of MongoClient and MongoDatabase, like so:

 The MONGOCLIENTURI should be the IP of the state database (the port default port is 27017) and the name of the database should be the same as the name in step 10 of setting up the server. Create a new ReadRepository instance and call the available methods.
