package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.graphics.Atlas;
import foundustry.types.Category;

import static foundustry.core.Vars.blocks;

public class Block {
    public String name = "parrot";
    public int size = 1;
    public int rotation = 0;
    public boolean canRotate = false;
    public Category category = null;

    public TextureRegion region;

    public Block(String name) {
        this.name = name;
        blocks.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }
    public boolean canRotate() {
        return canRotate;
    }
}