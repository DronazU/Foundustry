package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.Color;
import foundustry.core.Vars;
import foundustry.types.Block;
import foundustry.world.TileAction;
import foundustry.world.World;

public class DrawBlock extends Drawer {

    private final TileAction drawFloors = tile -> tile.drawFloor(batch);
    private final TileAction drawShadows = tile -> tile.drawShadow(batch);
    private final TileAction drawBlocks = tile -> tile.drawBlock(batch);
    private final TileAction drawConveyorItems = tile -> tile.drawConveyorItems(batch);

    public void render(World world) {
        if (world == null) {
            return;
        }

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
        if (block == null || block.region == null) {
            return;
        }

        float size = Vars.tileSize * block.size;
        float x = tileX * Vars.tileSize;
        float y = tileY * Vars.tileSize;

        begin();

        try {
            batch.setColor(1f, 1f, 1f, 0.5f);

            batch.draw(
                    block.region,
                    x, y,
                    size / 2f, size / 2f,
                    size, size,
                    flipX ? -1f : 1f,
                    1f,
                    rotation
            );
        } finally {
            batch.setColor(Color.WHITE);
            end();
        }
    }
}
