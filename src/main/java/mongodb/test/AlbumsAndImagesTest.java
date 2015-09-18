package mongodb.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * 
 * @author nicolas
 *
 *         This program deletes orphan images from a album db. There are two
 *         collections in the db. The album has a list os images but some of
 *         these images inside the Album collection are not in the image
 *         collection so this program is meant to delete them for consistency
 *
 */
public class AlbumsAndImagesTest {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase albumDb = client.getDatabase("albums");

		MongoCollection<Document> imageCollection = albumDb
				.getCollection("images");
		MongoCollection<Document> albumCollection = albumDb
				.getCollection("albums");

		/**
		 * Put in a Set all images Ids the belongs for any album. After we query
		 * all images and check if is inside the Set [O(1) operation]. Otherwise
		 * its orphan and we can delete it.
		 * 
		 * 
		 */

		Set<Integer> setAlbumImages = new HashSet<Integer>();

		List<Document> allAlbums = albumCollection.find().into(
				new ArrayList<Document>());

		// fetch all albums in the collections
		for (Document album : allAlbums) {

			List<Integer> images = album.get("images", ArrayList.class);

			for (Integer imageId : images) {

				setAlbumImages.add(imageId);
			}

		}

		List<Document> allImages = imageCollection.find().into(
				new ArrayList<Document>());

		for (Document eachImage : allImages) {

			int imageId = eachImage.getInteger("_id");

			
			if (!setAlbumImages.contains(imageId)) {
				// non-orphan since the image id is not in any album previously queried
				imageCollection.deleteOne(Filters.eq("_id", imageId));
			}

		}

	}
}
