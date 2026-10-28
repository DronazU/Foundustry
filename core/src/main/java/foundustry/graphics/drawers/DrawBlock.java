package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.world.Tile;

import static foundustry.core.Init.camera;
import static foundustry.world.Generator.map;

public class DrawBlock {
    public static SpriteBatch batch;

    public DrawBlock() {
        batch = new SpriteBatch();
    }


    public void render() {
        camera.update();

        SpriteBatch currentBatch = batch;
        currentBatch.setProjectionMatrix(camera.combined);
        currentBatch.begin();

        try {
            if (map != null) {
                for (Tile[] tiles : map) {
                    for (Tile tile : tiles) {
                        if (tile != null) {
                            tile.draw(currentBatch);
                        }
                    }
                }
            }
        } finally {
            if (currentBatch.isDrawing()) {
                currentBatch.end();
            }
        }
    }
    public static void dispose() {
        if (batch != null) batch.dispose();
    }
}
