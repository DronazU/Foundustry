package foundustry.types.UnitTypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import foundustry.game.Time;
import foundustry.game.Vars;
import foundustry.graphics.Atlas;
import foundustry.log.Log;
import foundustry.world.Block;
import foundustry.world.content.Blocks;

import static foundustry.game.Init.camera;
import static foundustry.world.Generator.map;

public class Player extends UnitType {
    public float x;
    public float y;
    public float speed = 10f * 60f;
    public Block block = Blocks.nothing;
    public float rotation;

    public final TextureRegion region;

    public Player(float x, float y) {
        super(x, y);
        speed = 10 * 60f;
        region = Atlas.find("alpha-drone");
        rotateSpeed = 10f;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) camera.zoom += Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) camera.zoom -= Time.delta();
        if (camera.zoom < 0.5f) camera.zoom = 0.5f;
        if (camera.zoom > 3.0f) camera.zoom = 3.0f;

        boolean a = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean d = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean w = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean s = Gdx.input.isKeyPressed(Input.Keys.S);

        if (a) x -= speed * Time.delta();
        if (d) x += speed * Time.delta();
        if (w) y += speed * Time.delta();
        if (s) y -= speed * Time.delta();

        if (a || d || w || s) {
            rotation = MathUtils.lerpAngleDeg(
                    rotation,
                    MathUtils.atan2(
                            (a ? 1 : 0) - (d ? 1 : 0),
                            (w ? 1 : 0) - (s ? 1 : 0)
                    ) * MathUtils.radiansToDegrees,
                    Math.min(1f, rotateSpeed * Time.delta())
            );
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Vector3 mousePosition = getMousePosition();
            block = map[(int)mousePosition.x][(int)mousePosition.y].block;
            Log.debug("block: " + block);
        }

        camera.position.set(this.x, this.y, 0);
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

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                x - 32,
                y - 32,
                32,
                32,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                rotation
        );
    }
}
