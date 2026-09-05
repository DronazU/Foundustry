package foundustry.world;

import foundustry.world.content.Blocks;

import static foundustry.world.SimplexNoise.noise;

public class Generator {
    public int width;
    public int height;
    public int tileSize = 32;
    public float scale = 0.05f;
    public float seed = (float)Math.random() * 1000;

    public Tile[][] generate() {
        Tile[][] map = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float x = i * tileSize;
                float y = j * tileSize;

                float value = noise(i * scale + seed, j * scale + seed);
                Block block = Blocks.nothing;

                if (value < -0.3f) {
                    block = Blocks.deepWater;
                } else if (value < -0.1f) {
                    block = Blocks.water;
                } else if (value < 0.1f) {
                    block = Blocks.sandWater;
                } else if (value < 0.3f) {
                    block = Blocks.sandFloor;
                } else if (value < 0.5f) {
                    block = Blocks.dirt;
                } else if (value < 0.7f) {
                    block = Blocks.grass;
                } else {
                    block = Blocks.stone;
                }
                map[i][j] = new Tile(x, y, block);
            }
        }

        return map;
    }
}
