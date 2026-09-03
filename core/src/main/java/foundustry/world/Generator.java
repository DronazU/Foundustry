package foundustry.world;

public class Generator {
    public int width;
    public int height;
    public int tileSize = 32;

    public void generate(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float x = i * tileSize;
                float y = j * tileSize;


            }
        }
    }
}
