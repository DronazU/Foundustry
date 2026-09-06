package foundustry.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import foundustry.game.Time;

import static foundustry.game.Init.camera;

public class Player extends UnitType {
    public float x, y;
    public float speed = 2f * 60f;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) camera.zoom += Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) camera.zoom -= Time.delta();
        if (camera.zoom < 0.5f) camera.zoom = 0.5f;
        if (camera.zoom > 3.0f) camera.zoom = 3.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * Time.delta() / camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * Time.delta() / camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * Time.delta() / camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * Time.delta() / camera.zoom;

        camera.position.set(this.x, this.y, 0);
    }

    public void update() {
        handleInput();
    }
}
