package foundustry.world.contentRegister;

import foundustry.core.Vars;
import foundustry.graphics.Atlas;
import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.types.item.Item;

public class Items {
    public static Item
            /** environment */
            unrefinedCopper, unrefinedIron, stone,
            /** materials region */
            copperIngot, ironIngot,
            /** items */
            conveyor, cornerConveyor, unloader, stoneFurnace
            ;

    public static void load() {
        /** environment region */
        unrefinedCopper = new Item("unrefined-copper") {{
            inventoryCraftable = false;
        }};

        unrefinedIron = new Item("unrefined-iron") {{
            inventoryCraftable = false;
        }};

        stone = new Item("item-stone") {{
            inventoryCraftable = false;
        }};

        /** materials region */
        copperIngot = new Item("copper-ingot") {{
           inventoryCraftable = false;
        }};

        ironIngot = new Item("iron-ingot") {{
            inventoryCraftable = false;
        }};

        /** items region */
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

        unloader = new Item("unloader") {{
            placeableBlock = Blocks.unloader;
            requirements
                    .with(unrefinedCopper, 5)
                    .with(unrefinedIron, 4);
            craftTime = 3f;
            craftAmount = 1;
        }};

        stoneFurnace = new  Item("stone-furnace") {{
            placeableBlock = Blocks.stoneFurnace;
            requirements.with(stone, 5);
            craftTime = 1f;
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