package foundustry.types.item;

public class ItemStack {
    public final Item item;
    public final int amount;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
}
