package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.game.Vars;
import foundustry.graphics.Atlas;

public abstract class UnitType {
    public String name = "dog";
    public float speed = 1f;
    public float x;
    public float y;
    public float rotateSpeed = 10f;
    public float drag = 0.05f;
    public float accel = 0.05f;
    public int elevation = 0;
    public boolean flying = false;

    public TextureRegion region;

    public UnitType() {
        Vars.units.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }
}
