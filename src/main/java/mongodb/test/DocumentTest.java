package mongodb.test;

import org.bson.Document;

public class DocumentTest {

	public static void main(String[] args) {
		
		Document doc = new Document();
		
		doc.append("str", "MongoDB");
		
		System.out.println(doc.getString("str"));
		
	}

}
