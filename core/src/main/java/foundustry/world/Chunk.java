package foundustry.world;

public class Chunk {
    public static final int SIZE = 32;

    public final int x;
    public final int y;

    public final Tile[][] tiles = new Tile[SIZE][SIZE];

    public boolean dirty = false;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return null;
        }

        return tiles[x][y];
    }
}