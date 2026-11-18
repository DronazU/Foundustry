package foundustry.types.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.types.Block;
import foundustry.types.Category;
import foundustry.world.content.Blocks;

public class Item {
    public final String name;
    public float flammability = 0f;
    public float explosiveness = 0f;
    public float radioactivity = 0f;
    public boolean stackable = true;
    public Block placeableBlock = Blocks.nothing;
    public float craftTime = 0f;
    public boolean inventoryCraftable = true;

    public ItemStackList requirements = new ItemStackList();
    public int craftAmount = 1;

    public Category category = Category.OTHER;
    public TextureRegion region;

    public Item(String name) {
        this.name = name;
        Vars.items.add(this);
    }
}