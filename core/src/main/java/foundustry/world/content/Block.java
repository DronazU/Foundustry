package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.graphics.Atlas;

import static foundustry.game.Vars.blocks;

public class Block {
    public String name;
    public int size;

    public TextureRegion region;

    public Block(String name) {
        this.name = name;
        blocks.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }
}