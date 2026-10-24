package foundustry.world;

import foundustry.core.Vars;
import foundustry.log.Log;
import foundustry.world.content.Block;
import foundustry.world.content.Blocks;

import static foundustry.world.SimplexNoise.noise;

public class Generator {
    public int width;
    public int height;
    public float scale;
    public float seed;

    public static Tile[][] map;

    public Tile[][] generate() {
        Tile[][] map = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float x = i * Vars.tileSize;
                float y = j * Vars.tileSize;

                float value = noise(
                        i * scale + seed,
                        j * scale + seed
                );

                Block floor;

                if (value < -0.3f) {
                    floor = Blocks.deepWater;
                } else if (value < -0.1f) {
                    floor = Blocks.water;
                } else if (value < 0.1f) {
                    floor = Blocks.sandWater;
                } else if (value < 0.3f) {
                    floor = Blocks.sandFloor;
                } else if (value < 0.5f) {
                    floor = Blocks.dirt;
                } else if (value < 0.7f) {
                    floor = Blocks.grass;
                } else {
                    floor = Blocks.stone;
                }

                map[i][j] = new Tile(x, y, floor);
            }
        }

        return map;
    }

    public Tile[][] createMap(int width, int height) {
        this.width = width;
        this.height = height;

        map = generate();

        Log.info("map created.");
        return map;
    }
}