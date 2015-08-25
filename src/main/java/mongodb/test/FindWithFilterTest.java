package mongodb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Filters.*;

public class FindWithFilterTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("course");

		MongoCollection<Document> coll = db.getCollection("findWithFilterTest");

		coll.drop();

		// insert 10 documents
		for (int i = 0; i < 10; i++) {
			coll.insertOne(new Document().append("x", new Random().nextInt(2))
					.append("y", new Random().nextInt(100)).append("i", i));
		}

//		Bson filter = new Document("x", 0).append("y",
//				new Document("$gt", 10).append("$lt", 90));

		Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));
		
		// exclude this attribute in the projection
		//Bson projection = new Document("x", 0);
		Bson projection = Projections.fields( Projections.include("y", "i"), Projections.exclude("_id") );
		
		List<Document> all = coll.find(filter).projection(projection).into(new ArrayList<Document>());

		for (Document doc : all) {
			System.out.println(doc);
		}

		long count = coll.count(filter);
		System.out.println("count: " + count);
	}

}
