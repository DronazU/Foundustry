package foundustry.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item {
    public final String name;

    public float flammability = 0f;
    public float explosiveness = 0f;
    public float radioactivity = 0f;
    public boolean stackable = true;

    public TextureRegion region;

    public Item(String name) {
        this.name = name;
    }
}