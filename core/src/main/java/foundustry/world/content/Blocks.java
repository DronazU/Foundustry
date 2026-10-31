package foundustry.world.content;

import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.log.Log;
import foundustry.types.blockTypes.Conveyor;
import foundustry.types.blockTypes.NothingBlock;
import foundustry.types.blockTypes.Floor;
import foundustry.types.Category;

public class Blocks {
    public static Block

    /** environment */
    sandFloor, grass, stone, dirt, nothing, water, deepWater, sandWater,

    /** logistic */
    conveyor, cornerConveyor
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
        conveyor = new Conveyor("conveyor") {{
            canRotate = true;
            speed = 1f;
        }};

        for (Block block : Vars.blocks) {
            block.load();
        }
        Log.info("Blocks loaded.");
    }

    public static Array<Block> getByCategory(Category category) {
        Array<Block> output = new Array<>();

        for (Block block : Vars.blocks) {
            if (block.category == category) {
                output.add(block);
            }
        }
        return output;
    }
}
