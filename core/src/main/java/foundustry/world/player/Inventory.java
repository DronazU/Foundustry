
package foundustry.world.player;

import foundustry.types.Item;

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

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }
}