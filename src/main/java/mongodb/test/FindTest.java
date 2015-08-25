package mongodb.test;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class FindTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("course");

		MongoCollection<Document> coll = db.getCollection("findTest");

		coll.drop();
		
		// insert 10 documents
		for (int i = 0 ; i < 10; i ++){
			coll.insertOne(new Document().append("x", i));
		}
		
		System.out.println("Find one");
		Document findOneDoc = coll.find().first();
		System.out.println(findOneDoc); 
		
		System.out.println("Find all with into");
		ArrayList<Document> findIntoDocs = coll.find().into(new ArrayList<Document>());
		System.out.println(findIntoDocs);
		
		System.out.println("Find all with interation");
		MongoCursor<Document> cur = coll.find().iterator();
		try {
			while (cur.hasNext()) {
				System.out.println(cur.next());
			}
		} finally {
			cur.close();
		}
		
		System.out.println("Count:");
		long count = coll.count();
		System.out.println(count);
	}

}
