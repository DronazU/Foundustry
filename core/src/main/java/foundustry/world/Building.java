package foundustry.world;

import foundustry.types.Block;

public class Building {
    public final Block block;

    public final int tileX;
    public final int tileY;

    public final int rotation;
    public final boolean flipX;

    public Building(Block block, int tileX, int tileY, int rotation, boolean flipX) {
        this.block = block;
        this.tileX = tileX;
        this.tileY = tileY;
        this.rotation = rotation;
        this.flipX = flipX;
    }

    public int getSize() {
        return block.size;
    }
}