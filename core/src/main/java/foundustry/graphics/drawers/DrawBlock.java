package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.world.Chunk;
import foundustry.world.Tile;
import foundustry.world.World;

import static foundustry.core.Init.camera;

public class DrawBlock {
    public static SpriteBatch batch;

    public DrawBlock() {
        batch = new SpriteBatch();
    }

    public void render(World world) {
        camera.update();

        SpriteBatch currentBatch = batch;
        currentBatch.setProjectionMatrix(camera.combined);
        currentBatch.begin();

        try {
            if (world == null) return;

            for (Chunk chunk : world.getLoadedChunks()) {
                for (Tile[] tiles : chunk.tiles) {
                    for (Tile tile : tiles) {
                        if (tile != null) {
                            tile.drawFloor(currentBatch);
                        }
                    }
                }
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                for (Tile[] tiles : chunk.tiles) {
                    for (Tile tile : tiles) {
                        if (tile != null) {
                            tile.drawShadow(currentBatch);
                        }
                    }
                }
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                for (Tile[] tiles : chunk.tiles) {
                    for (Tile tile : tiles) {
                        if (tile != null) {
                            tile.drawBlock(currentBatch);
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
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
    }
}