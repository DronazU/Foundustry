package foundustry.world.content;

import foundustry.core.Vars;
import foundustry.graphics.Atlas;
import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.types.item.Item;

public class Items {
    public static Item
            /** unrefined ore */
            unrefinedCopper, unrefinedIron,
            /** natural resources */
            stone,
            /** logistic */
            conveyor, cornerConveyor
            ;

    public static void load() {
        unrefinedCopper = new Item("unrefined-copper") {{
            inventoryCraftable = false;
        }};

        unrefinedIron = new Item("unrefined-iron") {{
            inventoryCraftable = false;
        }};

        stone = new Item("stone") {{
            inventoryCraftable = false;
        }};

        conveyor = new Item("conveyor") {{
           placeableBlock = Blocks.conveyor;
           requirements
                   .with(unrefinedCopper, 1)
                   .with(unrefinedIron, 1);
           craftTime = 0.5f;
           craftAmount = 2;
        }};

        cornerConveyor = new Item("conveyor_corner") {{
            placeableBlock = Blocks.cornerConveyor;
            requirements
                    .with(unrefinedCopper, 1)
                    .with(unrefinedIron, 1);
            craftTime = 0.5f;
            craftAmount = 2;
        }};

        for (Item item : Vars.items) {
            item.region = Atlas.find(item.name);
        }

        Log.info("Items loaded.");
    }

    public static Item getByBlock(Block block) {
        for (Item item : Vars.items) {
            if (item.placeableBlock == block) {
                return item;
            }
        }

        return null;
    }
}