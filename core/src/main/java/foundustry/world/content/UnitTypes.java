package foundustry.world.content;

import foundustry.game.Vars;
import foundustry.log.Log;
import foundustry.types.UnitTypes.GroundUnit;

public class UnitTypes {
    public static UnitType
            /** player */
            constructionDrone
            ;

    public static void load() {
        constructionDrone = new GroundUnit("construction-drone") {{
            speed = 10 * 60f;
            size = 2;
        }};

        for (UnitType unit : Vars.units) {
            unit.load();
        }
        Log.info("UnitTypes loaded.");
    }
}