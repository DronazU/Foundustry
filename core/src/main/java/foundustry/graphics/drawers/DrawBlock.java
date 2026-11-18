
package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.types.Block;
import foundustry.world.TileAction;
import foundustry.world.World;

public class DrawBlock extends Drawer {

    private static final Color previousColor = new Color();

    private final TileAction drawFloors = tile -> tile.drawFloor(batch);
    private final TileAction drawShadows = tile -> tile.drawShadow(batch);
    private final TileAction drawBlocks = tile -> tile.drawBlock(batch);
    private final TileAction drawConveyorItems = tile -> tile.drawConveyorItems(batch);

    public void render(World world) {
        if (world == null) return;

        begin();

        try {
            world.forEachTile(drawFloors);
            world.forEachTile(drawShadows);
            world.forEachTile(drawBlocks);
            world.forEachTile(drawConveyorItems);
        } finally {
            end();
        }
    }

    public void renderPreview(Block block, int tileX, int tileY, int rotation, boolean flipX) {
        if (block == null) return;

        begin();

        try {
            drawPreview(batch, block, tileX, tileY, rotation, flipX);
        } finally {
            end();
        }
    }

    public static void drawBlock(
            SpriteBatch batch,
            Block block,
            float x,
            float y,
            int rotation,
            boolean flipX,
            float alpha,
            float size
    ) {
        if (block == null || block.getRegion() == null) return;

        previousColor.set(batch.getColor());

        try {
            batch.setColor(
                    previousColor.r,
                    previousColor.g,
                    previousColor.b,
                    previousColor.a * alpha
            );

            batch.draw(
                    block.getRegion(),
                    x,
                    y,
                    size / 2f,
                    size / 2f,
                    size,
                    size,
                    flipX ? -1f : 1f,
                    1f,
                    rotation
            );
        } finally {
            batch.setColor(previousColor);
        }
    }

    private void drawPreview(SpriteBatch batch, Block block, int x, int y, int rotation, boolean flipX) {
        TextureRegion region = block.region;

        if (region == null) return;

        previousColor.set(batch.getColor());

        try {
            batch.setColor(
                    previousColor.r,
                    previousColor.g,
                    previousColor.b,
                    previousColor.a * 0.5f
            );

            float size = Vars.tileSize;

            batch.draw(
                    region,
                    x * Vars.tileSize,
                    y * Vars.tileSize,
                    size / 2f,
                    size / 2f,
                    size,
                    size,
                    flipX ? -1f : 1f,
                    1f,
                    rotation
            );
        } finally {
            batch.setColor(previousColor);
        }
    }
}