package foundustry.core.game.data.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import foundustry.log.Log;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class SaveManager implements AutoCloseable {

    private static final String CONNECTION_URI = "mongodb://localhost:27017";

    private static final String DATABASE_NAME = "Foundustry";
    private static final String COLLECTION_NAME = "saves";
    private static final String PLAYER_ID = "player";

    private final MongoClient client;
    private final MongoCollection<Document> saves;

    public SaveManager() {
        client = MongoClients.create(CONNECTION_URI);
        saves = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME);
        Log.info("SaveManager initialized");
    }

    public void savePosition(float x, float y) {
        try {
            Document document = new Document("playerId", PLAYER_ID)
                    .append("x", x)
                    .append("y", y);

            saves.replaceOne(
                    eq("playerId", PLAYER_ID),
                    document,
                    new ReplaceOptions().upsert(true)
            );

            Log.info("Player position saved: x=" + x + ", y=" + y);
        } catch (Exception e) {
            Log.errExc("Failed to save player position: " + e.getMessage());
        }
    }

    public float[] loadPosition() {
        try {
            Document document = saves.find(
                    eq("playerId", PLAYER_ID)
            ).first();

            if (document == null) {
                Log.info("No saved player position found");
                return null;
            }

            Number x = document.get("x", Number.class);
            Number y = document.get("y", Number.class);

            if (x == null || y == null) {
                Log.warn("Saved player position is invalid");
                return null;
            }

            Log.info("Player position loaded: x=" + x + ", y=" + y);

            return new float[]{
                    x.floatValue(),
                    y.floatValue()
            };
        } catch (Exception e) {
            Log.errExc("Failed to load player position: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {
        client.close();
        Log.info("SaveManager closed");
    }
}
