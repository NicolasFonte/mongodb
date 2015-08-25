package mongodb.test;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Projections.*;

public class FindWithSortSkipLimitTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("course");

		MongoCollection<Document> coll = db.getCollection("test");

		coll.drop();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				coll.insertOne(new Document().append("i", i).append("j", j));
			}
		}

		Bson projection = fields(include("i", "j"), excludeId());
		// Bson sort = new Document("i", 1).append("j", -1);
		Bson sort = Sorts.orderBy(Sorts.ascending("i"), Sorts.descending("j"));

		ArrayList<Document> all = coll.find().projection(projection).sort(sort)
				.skip(10).limit(90).into(new ArrayList<Document>());

		for (Document doc : all) {
			System.out.println(doc);
		}

	}

}
