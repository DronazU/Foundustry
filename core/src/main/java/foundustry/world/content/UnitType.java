package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.game.Vars;
import foundustry.graphics.Atlas;

public abstract class UnitType {
    public String name = "dog";
    public float speed = 10 * 60f;
    public float x;
    public float y;
    public float rotateSpeed = 10f;
    public float drag = 0.05f;
    public float accel = 0.05f;
    public int elevation = 0;
    public boolean flying = false;
    public float rotation = 90f;
    public int size = 1;
    public float velocityX;
    public float velocityY;

    public TextureRegion region;

    public UnitType(String name) {
        this.name = name;
        Vars.units.add(this);
    }

    public void load() {
        this.region = Atlas.find(this.name);
    }

    public void render(SpriteBatch batch) {
        float drawSize = size * Vars.tileSize;

        batch.draw(
                region,
                x - drawSize / 2f,
                y - drawSize / 2f,
                drawSize / 2f,
                drawSize / 2f,
                drawSize,
                drawSize,
                1f,
                1f,
                rotation
        );
    }
}
