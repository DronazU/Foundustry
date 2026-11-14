package foundustry.world;

import foundustry.core.Vars;
import foundustry.types.Block;
import foundustry.world.content.Blocks;

import static foundustry.world.SimplexNoise.noise;

public class Generator {
    public float scale = 0.01f;
    public float seed;

    public Chunk generateChunk(int chunkX, int chunkY) {
        Chunk chunk = new Chunk(chunkX, chunkY);

        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                int worldX = chunkX * Chunk.SIZE + x;
                int worldY = chunkY * Chunk.SIZE + y;

                float value = noise(
                        worldX * scale + seed,
                        worldY * scale + seed
                );

                Block floor;

                if (value < -0.5f) {
                    floor = Blocks.deepWater;
                } else if (value < -0.3f) {
                    floor = Blocks.water;
                } else if (value < -0.1f) {
                    floor = Blocks.sandWater;
                } else if (value < 0.1f) {
                    floor = Blocks.sandFloor;
                } else if (value < 0.3f) {
                    floor = Blocks.dirt;
                } else if (value < 0.6f) {
                    floor = Blocks.grass;
                } else {
                    floor = Blocks.stone;
                }

                chunk.tiles[x][y] = new Tile(
                        worldX * Vars.tileSize,
                        worldY * Vars.tileSize,
                        floor
                );
            }
        }
        return chunk;
    }
}