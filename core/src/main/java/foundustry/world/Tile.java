package foundustry.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    public float x, y;
    public Block block;

    public Tile(float x, float y, Block block) {
        this.x = x;
        this.y = y;
        this.block = block;
    }

    public void draw(SpriteBatch batch) {
        if (block != null && block.region != null) {
            batch.draw(block.region, x, y, 32, 32);
        }
    }
}
