package mongodb.test;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Handout2DeleteTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("students");

		MongoCollection<Document> coll = db.getCollection("grades");

		// coll.drop();

		Bson sort = Sorts.orderBy(Sorts.ascending("student_id", "score"));
		Bson homeworkFilter = Filters.eq("type", "homework");

		ArrayList<Document> docs = coll.find(homeworkFilter).sort(sort)
				.into(new ArrayList<Document>());

		int currentId = 0;
		for (Document cur : docs) {

//			int studentId = cur.getInteger("student_id");
//			ObjectId docId = cur.getObjectId("_id");
//			if (currentId == studentId) {
//				System.out.println(docId);
//				coll.deleteOne(Filters.eq("_id", docId));
//				currentId++;
//			}

			System.out.println(cur);
		}

	}
}
