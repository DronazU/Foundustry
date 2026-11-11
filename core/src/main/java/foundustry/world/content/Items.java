package foundustry.world.content;

import foundustry.core.Vars;
import foundustry.graphics.Atlas;
import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.types.Item;

public class Items {
    public static Item
            /** unrefined ore */
            unrefinedCopper, unrefinedIron,
            /** logistic */
            conveyor
            ;

    public static void load() {
        unrefinedCopper = new Item("unrefined-copper");
        unrefinedIron = new Item("unrefined-iron");

        conveyor = new Item("conveyor") {{
           placeableBlock = Blocks.conveyor;
        }};

        for (Item item : Vars.items) {
            item.region = Atlas.find(item.name);
        }

        Log.info("Items loaded.");
    }
}