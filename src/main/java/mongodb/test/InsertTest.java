package mongodb.test;

import java.util.Arrays;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("course");

		MongoCollection<Document> coll = db.getCollection("insertTest");

		coll.drop();

		Document smith = new Document().append("name", "Smith")
				.append("age", 30).append("profession", "programmer");
		
		Document jones = new Document().append("name", "Jones")
				.append("age", 25).append("profession", "hacker");

		
		coll.insertMany(Arrays.asList(smith, jones));
		
		
	}

}
