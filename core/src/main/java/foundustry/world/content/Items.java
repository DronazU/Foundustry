package foundustry.world.content;

import foundustry.core.Vars;
import foundustry.graphics.Atlas;
import foundustry.log.Log;
import foundustry.types.Item;

public class Items {
    public static Item
            /** unrefined ore */
            unrefinedCopper, unrefinedIron,
            /** logistic */
            conveyor, cornerConveyor
            ;

    public static void load() {
        unrefinedCopper = new Item("unrefined-copper");
        unrefinedIron = new Item("unrefined-iron");

        conveyor = new Item("conveyor") {{
           placeableBlock = Blocks.conveyor;
        }};
        cornerConveyor = new Item("conveyor_corner") {{
            placeableBlock = Blocks.cornerConveyor;
        }};

        for (Item item : Vars.items) {
            item.region = Atlas.find(item.name);
        }

        Log.info("Items loaded.");
    }
}