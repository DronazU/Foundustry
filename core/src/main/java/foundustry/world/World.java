package foundustry.world;

import java.util.HashMap;
import java.util.Map;

public class World {
    public static final int LOAD_RADIUS = 2;

    private final Generator generator;
    private final Map<Long, Chunk> chunks = new HashMap<>();

    public World(float seed) {
        generator = new Generator();
        generator.seed = seed;
    }

    public void update(float worldX, float worldY) {
        int tileX = (int)Math.floor(worldX / foundustry.core.Vars.tileSize);
        int tileY = (int)Math.floor(worldY / foundustry.core.Vars.tileSize);

        int chunkX = Math.floorDiv(tileX, Chunk.SIZE);
        int chunkY = Math.floorDiv(tileY, Chunk.SIZE);

        for (int x = chunkX - LOAD_RADIUS; x <= chunkX + LOAD_RADIUS; x++) {
            for (int y = chunkY - LOAD_RADIUS; y <= chunkY + LOAD_RADIUS; y++) {
                loadChunk(x, y);
            }
        }
    }

    private void loadChunk(int x, int y) {
        long key = getKey(x, y);

        if (!chunks.containsKey(key)) {
            chunks.put(key, generator.generateChunk(x, y));
        }
    }

    public Tile getTile(int worldTileX, int worldTileY) {
        int chunkX = Math.floorDiv(worldTileX, Chunk.SIZE);
        int chunkY = Math.floorDiv(worldTileY, Chunk.SIZE);

        int localX = Math.floorMod(worldTileX, Chunk.SIZE);
        int localY = Math.floorMod(worldTileY, Chunk.SIZE);

        Chunk chunk = chunks.get(getKey(chunkX, chunkY));

        if (chunk == null) {
            return null;
        }

        return chunk.getTile(localX, localY);
    }

    public Iterable<Chunk> getLoadedChunks() {
        return chunks.values();
    }

    private long getKey(int x, int y) {
        return ((long)x << 32) | (y & 0xffffffffL);
    }

    public float getSeed() {
        return generator.seed;
    }
}