package foundustry.world.content;

import foundustry.log.Log;
import foundustry.types.Item;

public class Items {
    public static Item
            unrefinedCopper, unrefinedIron;

    public static void load() {
        unrefinedCopper = new Item("unrefined-copper");
        unrefinedIron = new Item("unrefined-iron");

        Log.info("Blocks loaded.");
    }
}