package foundustry.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.graphics.Atlas;

public class Block {
    public String name = "parrot";
    public int size = 1;
    public boolean canRotate = false;
    public Category category = null;
    public boolean hasShadow = true;

    public TextureRegion region;

    public Block(String name) {
        this.name = name;
        Vars.blocks.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }
    public boolean canRotate() {
        return canRotate;
    }
    public void update(float delta) { }

    public TextureRegion getRegion() {
        return region;
    }
}