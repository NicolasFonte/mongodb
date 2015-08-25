package mongodb.test;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Handout3DeleteTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("school");

		MongoCollection<Document> coll = db.getCollection("students");

		// Bson sort = Sorts.orderBy(Sorts.ascending("student_id", "score"));
		Bson homeworkFilter = Filters.eq("type", "homework");

		ArrayList<Document> docs = coll.find().into(new ArrayList<Document>());

		for (Document eachStudent : docs) {

			System.out.println("old student: " + eachStudent);
			
			int id = eachStudent.getInteger("_id");
			List<Document> scores = (List) eachStudent.get("scores");
			List<Document> newScores = removeSmallestHomeworkScore(scores);
			
			eachStudent.append("scores", newScores);

			coll.replaceOne(Filters.eq("_id", id), eachStudent);
			
			System.out.println("Updated student: " + eachStudent);
		}

	}


	private static List<Document> removeSmallestHomeworkScore(
			List<Document> scores) {

		List<Document> newScores = new ArrayList<Document>();
		List<Document> homeworkScores = new ArrayList<Document>();
		
		for (Document score : scores) {
			if ( score.getString("type").equals("homework") ) {
				homeworkScores.add(score);
			} else {
				newScores.add(score);
			}
		}
		
		// get higher score
		double value = Double.MIN_VALUE; 
		Document minHomeworkScore = null;
		
		for ( Document hscore : homeworkScores ) {
			if ( hscore.getDouble("score") > value) {
				value = hscore.getDouble("score");
				minHomeworkScore = hscore;
			}
		}
		newScores.add(minHomeworkScore);
		return newScores;
	}

}
