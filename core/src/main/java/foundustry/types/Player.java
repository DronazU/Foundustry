package foundustry.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import static foundustry.game.Init.camera;

public class Player extends UnitType {
    public float x, y;
    public float speed = 2f;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.zoom += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.zoom -= delta;

        if (camera.zoom < 0.5f) camera.zoom = 0.5f;
        if (camera.zoom > 3.0f) camera.zoom = 3.0f;

        camera.position.set(this.x, this.y, 0);
    }

    public void update() {
        handleInput();
    }
}
