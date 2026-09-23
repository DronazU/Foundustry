package foundustry.types.UnitTypes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UnitType {
    public String name = "dog";
    public float speed;

    public abstract void render(SpriteBatch batch);
}
