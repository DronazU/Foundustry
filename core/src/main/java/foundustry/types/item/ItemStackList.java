package foundustry.types.item;

import com.badlogic.gdx.utils.Array;

public class ItemStackList {
    private final Array<ItemStack> items = new Array<>();

    public ItemStackList with(Item item, int amount) {
        if (item == null || amount <= 0) {
            return this;
        }

        items.add(new ItemStack(item, amount));
        return this;
    }

    public Array<ItemStack> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}