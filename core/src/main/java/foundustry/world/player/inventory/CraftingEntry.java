package foundustry.world.player.inventory;

import foundustry.types.item.Item;

public class CraftingEntry {
    public final Item item;
    public int amount;

    public CraftingEntry(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
}