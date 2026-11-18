package foundustry.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.graphics.Atlas;

public abstract class UnitType {
    private static final float SHADOW_GROUND_OFFSET = 0.25f;
    private static final float SHADOW_FLYING_OFFSET = 1f;
    private static final float SHADOW_OPACITY = 0.25f;

    private static final Color previousColor = new Color();

    public String name;
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
        if (region == null) return;

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
        if (region == null) return;

        float drawSize = size * Vars.tileSize;
        float offset = Vars.tileSize * (flying ? SHADOW_FLYING_OFFSET : SHADOW_GROUND_OFFSET);

        float shadowX = x - offset;
        float shadowY = y - offset;

        previousColor.set(batch.getColor());
        batch.setColor(0f, 0f, 0f, SHADOW_OPACITY);

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

        batch.setColor(previousColor);
    }
}
