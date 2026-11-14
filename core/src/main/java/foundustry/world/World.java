package foundustry.world;

import foundustry.core.game.data.database.SaveManager;

import java.util.HashMap;
import java.util.Map;

public class World {
    public static final int LOAD_RADIUS = 2;

    private final Generator generator;
    private final SaveManager saveManager;
    private final String worldId;

    private final Map<Long, Chunk> chunks = new HashMap<>();

    public World(float seed, String worldId, SaveManager saveManager) {
        generator = new Generator();
        generator.seed = seed;

        this.worldId = worldId;
        this.saveManager = saveManager;
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
            Chunk chunk = generator.generateChunk(x, y);

            saveManager.loadChunk(chunk, worldId);

            chunks.put(key, chunk);
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

    public boolean setBlock(int worldTileX, int worldTileY, foundustry.types.Block block, int rotation) {
        Tile tile = getTile(worldTileX, worldTileY);

        if (tile == null) {
            return false;
        }

        int chunkX = Math.floorDiv(worldTileX, Chunk.SIZE);
        int chunkY = Math.floorDiv(worldTileY, Chunk.SIZE);

        Chunk chunk = chunks.get(getKey(chunkX, chunkY));

        if (chunk == null) {
            return false;
        }

        tile.block = block;
        tile.rotation = rotation;

        chunk.dirty = true;

        return true;
    }

    public void save() {
        for (Chunk chunk : chunks.values()) {
            if (!chunk.dirty) {
                continue;
            }

            saveManager.saveChunk(chunk, worldId);
            chunk.dirty = false;
        }
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