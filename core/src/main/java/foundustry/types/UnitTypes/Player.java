package foundustry.types.UnitTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import foundustry.game.Time;
import foundustry.game.Vars;
import foundustry.log.Log;
import foundustry.world.content.Block;
import foundustry.world.content.Blocks;
import foundustry.world.content.UnitType;
import foundustry.world.content.UnitTypes;

import static foundustry.game.Init.camera;
import static foundustry.world.Generator.map;

public class Player {
    public UnitType unit = UnitTypes.constructionDrone;
    public Block block = Blocks.nothing;

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) camera.zoom += Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) camera.zoom -= Time.delta();
        if (camera.zoom < 0.5f) camera.zoom = 0.5f;
        if (camera.zoom > 3.0f) camera.zoom = 3.0f;

        boolean a = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean d = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean w = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean s = Gdx.input.isKeyPressed(Input.Keys.S);

        float moveX = 0f;
        float moveY = 0f;

        if (a) moveX -= 1f;
        if (d) moveX += 1f;
        if (w) moveY += 1f;
        if (s) moveY -= 1f;

        if (moveX != 0f || moveY != 0f) {
            float length = (float)Math.sqrt(moveX * moveX + moveY * moveY);

            moveX /= length;
            moveY /= length;
        }

        float targetX = moveX * unit.speed;
        float targetY = moveY * unit.speed;

        unit.velocityX += (targetX - unit.velocityX) * unit.accel * 60f * Time.delta();
        unit.velocityY += (targetY - unit.velocityY) * unit.accel * 60f * Time.delta();

        if (moveX == 0f) {
            unit.velocityX *= 1f - unit.drag * 60f * Time.delta();
        }

        if (moveY == 0f) {
            unit.velocityY *= 1f - unit.drag * 60f * Time.delta();
        }

        unit.x += unit.velocityX * Time.delta();
        unit.y += unit.velocityY * Time.delta();

        if (a || d || w || s) {
            unit.rotation = MathUtils.lerpAngleDeg(
                    unit.rotation,
                    MathUtils.atan2(
                            (a ? 1 : 0) - (d ? 1 : 0),
                            (w ? 1 : 0) - (s ? 1 : 0)
                    ) * MathUtils.radiansToDegrees,
                    Math.min(1f, unit.rotateSpeed * Time.delta())
            );
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Vector3 mousePosition = getMousePosition();
            block = map[(int)mousePosition.x][(int)mousePosition.y].block;
            Log.debug("block: " + block);
        }

        camera.position.set(unit.x, unit.y, 0);
    }

    public void update() {
        handleInput();
        build();
    }

    public void build() {
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 mousePosition = getMousePosition();
            map[(int)mousePosition.x][(int)mousePosition.y].block = block;
        }
    }

    public Vector3 getMousePosition() {
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
        int tileX = (int)mousePosition.x / Vars.tileSize;
        int tileY = (int)mousePosition.y / Vars.tileSize;
        return new Vector3(tileX, tileY, 0);
    }
}
