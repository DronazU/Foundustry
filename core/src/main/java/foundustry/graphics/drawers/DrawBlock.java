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
                for (int x = 0; x < map.length; x++) {
                    for (int y = 0; y < map[x].length; y++) {
                        Tile tile = map[x][y];

                        if (tile != null) {
                            tile.draw(currentBatch, map, x, y);
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
