package foundustry.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import foundustry.game.Time;
import foundustry.log.Log;
import foundustry.world.Block;
import foundustry.world.Tile;
import foundustry.world.content.Blocks;
import org.lwjgl.input.Mouse;

import static foundustry.game.Init.camera;
import static foundustry.graphics.drawers.DrawBlock.gameMap;
import static foundustry.world.Generator.tileSize;

public class Player extends UnitType {
    public float x, y;
    public float speed = 4f * 60f;
    public Block block = Blocks.nothing;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) camera.zoom += Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) camera.zoom -= Time.delta();
        if (camera.zoom < 0.5f) camera.zoom = 0.5f;
        if (camera.zoom > 3.0f) camera.zoom = 3.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * Time.delta();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * Time.delta();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) block = Blocks.grass;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) block = Blocks.stone;

        camera.position.set(this.x, this.y, 0);
    }

    public void update() {
        handleInput();
        build();
    }

    public void build() {
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePosition);
            int tileX = (int)mousePosition.x / tileSize;
            int tileY = (int)mousePosition.y / tileSize;
            gameMap[tileX][tileY].block = block;
        }
    }
}
