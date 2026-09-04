package foundustry.world;

import foundustry.world.content.Blocks;

public class Generator {
    public int width;
    public int height;
    public int tileSize = 32;

    public Tile[][] generate() {
        Tile[][] map = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float x = i * tileSize;
                float y = j * tileSize;

                Block block;

                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    block = Blocks.stone;
                } else {
                    block = Blocks.grass;
                }

                map[i][j] = new Tile(x, y, block);
            }
        }

        return map;
    }
}
