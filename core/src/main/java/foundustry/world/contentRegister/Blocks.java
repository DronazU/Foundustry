package foundustry.world.contentRegister;

import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.types.Category;
import foundustry.types.recipe.ProductionType;
import foundustry.world.contentType.block.environment.Floor;
import foundustry.world.contentType.NothingBlock;
import foundustry.world.contentType.block.logistics.Conveyor;
import foundustry.world.contentType.block.logistics.Unloader;
import foundustry.world.contentType.block.production.GenericCrafter;

public class Blocks {
    public static Block

    /** environment */
    sandFloor, grass, stone, dirt, nothing, water, deepWater, sandWater,

    /** logistic */
    conveyor, cornerConveyor, unloader,

    /** production */
    stoneFurnace
    ;

    public static void load() {
        // Environment region
        nothing = new NothingBlock("nothing");
        sandFloor = new Floor("sand-floor");
        grass = new Floor("grass");
        stone = new Floor("stone");
        dirt = new Floor("dirt");
        water = new Floor("water");
        deepWater = new Floor("deep-water");
        sandWater = new Floor("sand-water");
        // Environment endregion

        // Logistic region
        conveyor = new Conveyor("conveyor") {{
            canRotate = true;
            speed = 1;
        }};
        cornerConveyor = new Conveyor("conveyor_corner") {{
            speed = 1f;
            canFlip = true;
            canRotate = true;
        }};
        unloader = new Unloader("unloader");
        // Logistic endregion

        // Production region
        stoneFurnace = new GenericCrafter("stone-furnace", ProductionType.FURNACE) {{
           size = 2;
        }};
        // Production endregion

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