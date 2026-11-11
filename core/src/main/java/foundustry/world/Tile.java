package foundustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.types.Block;

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

    public void drawFloor(SpriteBatch batch) {
        float size = Vars.tileSize;

        if (floor != null && floor.region != null) {
            batch.draw(floor.region, x, y, size, size);
        }
    }

    public void drawShadow(SpriteBatch batch) {
        if (block == null || !block.hasShadow) return;
        if (Vars.Shadow.squareShadow == null) return;

        float size = Vars.tileSize * 1.25f;
        float offset = (size - Vars.tileSize) / 2f;

        Color previousColor = new Color(batch.getColor());

        batch.setColor(0f, 0f, 0f, 1);

        batch.draw(
                Vars.Shadow.squareShadow,
                x - offset,
                y - offset,
                size,
                size
        );

        batch.setColor(previousColor);
    }

    public void drawBlock(SpriteBatch batch) {
        if (block == null) return;

        TextureRegion region = block.getRegion();

        if (region == null) return;

        float size = Vars.tileSize;

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