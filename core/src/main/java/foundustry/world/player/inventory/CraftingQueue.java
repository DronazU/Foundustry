package foundustry.world.player.inventory;

import com.badlogic.gdx.utils.Array;
import foundustry.types.item.Item;
import foundustry.types.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class CraftingQueue {
    private final Array<CraftingEntry> entries = new Array<>();
    private float remainingTime;

    public boolean add(Item item, int amount, Inventory inventory) {
        if (item == null || inventory == null || amount <= 0) {
            return false;
        }

        if (!item.inventoryCraftable || item.craftTime <= 0f) {
            return false;
        }

        if (item.requirements.getItems().size == 0) {
            return false;
        }

        Map<Item, Integer> requirements = new LinkedHashMap<>();

        for (ItemStack stack : item.requirements.getItems()) {
            requirements.merge(stack.item, stack.amount, Integer::sum);
        }

        for (Map.Entry<Item, Integer> entry : requirements.entrySet()) {
            int required = entry.getValue() * amount;

            if (inventory.get(entry.getKey()) < required) {
                return false;
            }
        }

        for (Map.Entry<Item, Integer> entry : requirements.entrySet()) {
            inventory.remove(entry.getKey(), entry.getValue() * amount);
        }

        for (CraftingEntry entry : entries) {
            if (entry.item == item) {
                entry.amount += amount;
                return true;
            }
        }

        boolean wasEmpty = entries.size == 0;
        entries.add(new CraftingEntry(item, amount));

        if (wasEmpty) {
            remainingTime = item.craftTime;
        }

        return true;
    }

    public void update(float delta, Inventory inventory) {
        if (entries.size == 0 || inventory == null || delta <= 0f) {
            return;
        }

        float time = delta;

        while (entries.size > 0) {
            CraftingEntry current = entries.first();

            if (time < remainingTime) {
                remainingTime -= time;
                break;
            }

            time -= remainingTime;

            inventory.add(current.item, current.item.craftAmount);
            current.amount--;

            if (current.amount > 0) {
                remainingTime = current.item.craftTime;
            } else {
                entries.removeIndex(0);

                if (entries.size > 0) {
                    remainingTime = entries.first().item.craftTime;
                } else {
                    remainingTime = 0f;
                    break;
                }
            }
        }
    }

    public Array<CraftingEntry> getEntries() {
        return entries;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public boolean isEmpty() {
        return entries.size == 0;
    }
}