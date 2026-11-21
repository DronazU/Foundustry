package foundustry.world.contentType.conveyor;

import foundustry.types.item.Item;

public class ConveyorItem {
    public final Item item;
    public float position;

    public ConveyorItem(Item item, float position) {
        this.item = item;
        this.position = position;
    }
}