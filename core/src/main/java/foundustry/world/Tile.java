package foundustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

        if (block == null) return;

//        if (Vars.Shadow.squareShadow != null) {
//            float shadowSize = size * 1.5f;
//            float offset = (shadowSize - size) / 2f;
//            batch.setColor(Color.BLACK);
//            batch.draw(
//                    Vars.Shadow.squareShadow,
//                    x - offset,
//                    y - offset,
//                    shadowSize,
//                    shadowSize
//            );
//            batch.setColor(Color.WHITE);
//        }

        TextureRegion region = block.getRegion();

        if (region != null) {
            batch.draw(
                    region,
                    x, y,
                    size / 2f, size / 2f,
                    size, size,
                    1f, 1f,
                    rotation
            );
        }
    }
}