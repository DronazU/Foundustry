package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.types.UnitTypes.UnitType;

import static foundustry.game.Init.camera;
import static foundustry.game.Vars.units;

public class DrawUnit {
    public static SpriteBatch batch;

    public DrawUnit() {
        batch = new SpriteBatch();
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (UnitType unit : units) unit.render(batch);
        batch.end();
    }

    public static void dispose() {
        batch.dispose();
    }
}