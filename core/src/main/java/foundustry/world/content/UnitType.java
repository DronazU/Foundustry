package foundustry.world.content;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
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
        drawShadow(batch);
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

    public void drawShadow(SpriteBatch batch) {
        float drawSize = size * Vars.tileSize;
        float shadowX = x - Vars.tileSize * (flying ? 2f : 0.25f);
        float shadowY = y - Vars.tileSize * (flying ? 2f : 0.25f);
        batch.setColor(0f, 0f, 0f, 0.5f);
        batch.draw(
                region,
                shadowX - drawSize / 2f,
                shadowY - drawSize / 2f,
                drawSize / 2f,
                drawSize / 2f,
                drawSize,
                drawSize,
                1f,
                1f,
                rotation
        );
        batch.setColor(Color.WHITE);
    }
}
