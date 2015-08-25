package mongodb.test;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

	public static void main(String[] args) {

		// we can use a list for clusters
		// MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(50).build();
		MongoClient client = new MongoClient("localhost", options);
		
		MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
		
		MongoCollection<Document> peopleCollection = db.getCollection("people");
		
		
		
	}

}
