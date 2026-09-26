package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.game.Vars;
import foundustry.log.Log;
import foundustry.types.BlockTypes.NothingBlock;
import foundustry.types.BlockTypes.Floor;
import foundustry.types.UnitTypes.GroundUnit;

public class UnitTypes {
    public static UnitType
            /** player */
            constructionDrone
            ;

    public static void load() {
        constructionDrone = new GroundUnit() {{
            speed = 10f;
        }};

        for (UnitType unit : Vars.units) {
            unit.load();
        }
        Log.info("Blocks loaded.");
    }
}