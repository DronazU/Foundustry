package foundustry.world.content;

import foundustry.world.Block;
import foundustry.world.Floor;
import foundustry.graphics.Atlas;

public class Blocks {
    public static Block

    /** environment */
    sandFloor, grass, stone, dirt

    ;

    public static void load() {
        sandFloor = new Floor("sand-floor");
        grass = new Floor("grass");
        stone = new Floor("stone");
        dirt = new Floor("dirt");

        for (Block block : Block.worldBlocks) {
            block.load();
        }
    }
}
