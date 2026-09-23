package foundustry.types.UnitTypes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.game.Vars;

public abstract class UnitType {
    public String name = "dog";
    public float speed;
    public float x;
    public float y;
    public float rotateSpeed;

    public UnitType(float x, float y) {
        this.x = x;
        this.y = y;
        Vars.units.add(this);
    }

    public abstract void render(SpriteBatch batch);
}
