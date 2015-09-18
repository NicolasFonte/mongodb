package mongodb.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

// return all the states with population less than 10 million

public class AggregationFrameworkTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase zipsDb = client.getDatabase("zipsdb");
		MongoCollection<Document> zipsCollection = zipsDb.getCollection("zips");

		List<Document> pipeline;

		pipeline = Arrays.asList(
				new Document("$group", new Document("_id", "$state")
					.append("totalPop", new Document("$sum", "$pop"))),
				new Document("$match", new Document("totalPop", 
						new Document("$gte", 10000))) );

		/**
		 * pipeline = Arrays.asList(Document.parse("json stage pipeline1", pipeline 2..));
		 */
		
		List<Document> zips = zipsCollection.aggregate(pipeline).into(
				new ArrayList<Document>());

		for (Document eachZip : zips) {
			System.out.println(eachZip);
		}

	}

}
