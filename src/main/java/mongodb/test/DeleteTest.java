package mongodb.test;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DeleteTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("course");

		MongoCollection<Document> coll = db.getCollection("findWithSortTest");

		coll.drop();

		for (int i = 0; i < 8; i++) {
			coll.insertOne(new Document().append("_id", i));
		}
		
		//coll.deleteMany(Filters.gt("_id", 4));
		coll.deleteOne(Filters.eq("_id", 4));
		
		for (Document cur : coll.find().into(new ArrayList<Document>())) {
			System.out.println(cur);
		}

	}

}
