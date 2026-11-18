package foundustry.world.player.inventory;

import foundustry.types.item.Item;
import foundustry.types.item.ItemStack;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory {
    private final Map<Item, Integer> items = new LinkedHashMap<>();

    public int get(Item item) {
        return items.getOrDefault(item, 0);
    }

    public void add(Item item, int amount) {
        if (item == null || amount <= 0) return;

        items.put(item, get(item) + amount);
    }

    public boolean remove(Item item, int amount) {
        if (item == null || amount <= 0) return false;

        int count = get(item);

        if (count < amount) return false;

        if (count == amount) {
            items.remove(item);
        } else {
            items.put(item, count - amount);
        }

        return true;
    }

    public int getCraftableAmount(Item item) {
        if (item == null || !item.inventoryCraftable) {
            return 0;
        }

        if (item.requirements.getItems().size == 0) {
            return 0;
        }

        int maxAmount = Integer.MAX_VALUE;

        Map<Item, Integer> requirements = new LinkedHashMap<>();

        for (ItemStack stack : item.requirements.getItems()) {
            requirements.merge(
                    stack.item,
                    stack.amount,
                    Integer::sum
            );
        }

        for (Map.Entry<Item, Integer> entry : requirements.entrySet()) {
            int available = get(entry.getKey());
            int required = entry.getValue();

            maxAmount = Math.min(maxAmount, available / required);
        }

        return maxAmount == Integer.MAX_VALUE ? 0 : maxAmount;
    }

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void clear() {
        items.clear();
    }
}