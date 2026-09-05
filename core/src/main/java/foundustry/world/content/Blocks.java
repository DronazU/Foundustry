package foundustry.world.content;

import foundustry.world.Block;
import foundustry.world.Floor;

public class Blocks {
    public static Block

    /** environment */
    sandFloor, grass, stone, dirt, nothing, water, deepWater, sandWater

    ;

    public static void load() {
        nothing = new NothingBlock("nothing");
        sandFloor = new Floor("sand-floor");
        grass = new Floor("grass");
        stone = new Floor("stone");
        dirt = new Floor("dirt");
        water = new Floor("water");
        deepWater = new Floor("deep-water");
        sandWater = new Floor("sand-water");

        for (Block block : Block.worldBlocks) {
            block.load();
        }
    }
}
