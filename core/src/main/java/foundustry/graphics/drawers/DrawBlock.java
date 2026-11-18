package foundustry.graphics.drawers;

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
}
