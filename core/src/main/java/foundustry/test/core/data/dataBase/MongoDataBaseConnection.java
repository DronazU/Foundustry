
package foundustry.test.core.data.dataBase;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import foundustry.log.Log;
import org.bson.Document;

public class MongoDataBaseConnection {

    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            Document result = client.getDatabase("Foundustry")
                    .runCommand(new Document("ping", 1));

            Log.info("MongoDB connection successful: " + result);
        } catch (Exception e) {
            Log.errExc("MongoDB connection failed: " + e.getMessage());
        }
    }
}