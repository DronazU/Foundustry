package foundustry.core.game.data.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import foundustry.core.Vars;
import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.world.Chunk;
import foundustry.world.Tile;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class SaveManager implements AutoCloseable {

    private static final String CONNECTION_URI = "mongodb://localhost:27017";

    private static final String DATABASE_NAME = "Foundustry";
    private static final String COLLECTION_NAME = "saves";
    private static final String PLAYER_ID = "player";

    private final MongoClient client;
    private final MongoCollection<Document> saves;
    private final MongoCollection<Document> worlds;
    private final MongoCollection<Document> chunks;

    public SaveManager() {
        client = MongoClients.create(CONNECTION_URI);

        var database = client.getDatabase(DATABASE_NAME);

        saves = database.getCollection(COLLECTION_NAME);
        worlds = database.getCollection("worlds");
        chunks = database.getCollection("chunks");

        Log.info("SaveManager initialized");
    }

    public static class WorldData {
        public final String id;
        public final float seed;

        public WorldData(String id, float seed) {
            this.id = id;
            this.seed = seed;
        }
    }

    public WorldData loadWorld() {
        try {
            Document document = worlds.find(eq("_id", "active")).first();

            if (document == null) {
                return null;
            }

            String id = document.getString("worldId");
            Number seed = document.get("seed", Number.class);

            if (id == null || seed == null) {
                return null;
            }

            Log.info("World loaded: " + id);

            return new WorldData(id, seed.floatValue());

        } catch (Exception e) {
            Log.errExc("Failed to load world: " + e.getMessage());
            return null;
        }
    }

    public WorldData createWorld(float seed) {
        WorldData world = new WorldData(
                UUID.randomUUID().toString(),
                seed
        );

        try {
            Document document = new Document("_id", "active")
                    .append("worldId", world.id)
                    .append("seed", world.seed);

            worlds.replaceOne(
                    eq("_id", "active"),
                    document,
                    new ReplaceOptions().upsert(true)
            );

            Log.info("World created: " + world.id);

        } catch (Exception e) {
            Log.errExc("Failed to create world: " + e.getMessage());
        }

        return world;
    }

    public void saveChunk(Chunk chunk, String worldId) {
        try {
            List<Document> blockData = new ArrayList<>();

            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < Chunk.SIZE; y++) {
                    Tile tile = chunk.tiles[x][y];

                    if (tile == null || tile.block == null) {
                        continue;
                    }

                    blockData.add(
                            new Document("x", x)
                                    .append("y", y)
                                    .append("block", tile.block.name)
                                    .append("rotation", tile.rotation)
                    );
                }
            }

            String chunkId = worldId + ":" + chunk.x + ":" + chunk.y;

            Document document = new Document("chunkId", chunkId)
                    .append("worldId", worldId)
                    .append("chunkX", chunk.x)
                    .append("chunkY", chunk.y)
                    .append("blocks", blockData);

            chunks.replaceOne(
                    eq("chunkId", chunkId),
                    document,
                    new ReplaceOptions().upsert(true)
            );

        } catch (Exception e) {
            Log.errExc("Failed to save chunk: " + e.getMessage());
        }
    }

    public void loadChunk(Chunk chunk, String worldId) {
        try {
            String chunkId = worldId + ":" + chunk.x + ":" + chunk.y;

            Document document = chunks.find(
                    eq("chunkId", chunkId)
            ).first();

            if (document == null) {
                return;
            }

            List<Document> blockData = document.getList(
                    "blocks",
                    Document.class
            );

            if (blockData == null) {
                return;
            }

            for (Document data : blockData) {
                int x = data.getInteger("x", -1);
                int y = data.getInteger("y", -1);
                int rotation = data.getInteger("rotation", 0);

                String blockName = data.getString("block");

                if (x < 0 || x >= Chunk.SIZE ||
                        y < 0 || y >= Chunk.SIZE ||
                        blockName == null) {
                    continue;
                }

                Block block = findBlock(blockName);

                if (block == null) {
                    continue;
                }

                Tile tile = chunk.tiles[x][y];

                if (tile != null) {
                    tile.block = block;
                    tile.rotation = rotation;
                }
            }

        } catch (Exception e) {
            Log.errExc("Failed to load chunk: " + e.getMessage());
        }
    }

    private Block findBlock(String name) {
        for (Block block : Vars.blocks) {
            if (block.name.equals(name)) {
                return block;
            }
        }

        return null;
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