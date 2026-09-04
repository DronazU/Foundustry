package foundustry.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;

public class Block {
    public String name;
    public TextureRegion region;
    public int size;

    public static final Array<Block> worldBlocks = new Array<>();

    public Block(String name) {
        this.name = name;
        worldBlocks.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        if (region != null) batch.draw(region, x, y, 32, 32);
    }
}