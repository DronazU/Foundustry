package foundustry.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.core.Vars;
import foundustry.world.content.Block;

public class Tile {
    public float x, y;

    public Block floor;
    public Block block;
    public int rotation;

    public Tile(float x, float y, Block floor) {
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.block = null;
    }

    public void draw(SpriteBatch batch) {
        float size = Vars.tileSize;
        if (floor != null && floor.region != null) {
            batch.draw(floor.region, x, y, size, size);
        }
        if (block != null && block.region != null) {
            batch.draw(
                    block.region,
                    x, y,
                    size / 2f, size / 2f,
                    size, size,
                    1f, 1f,
                    rotation
            );
        }
    }
}