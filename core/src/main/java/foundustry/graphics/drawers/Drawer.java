package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static foundustry.core.Init.camera;

/**
 * Base class for world space drawers.
 * Owns a single {@link SpriteBatch} and its begin/end lifecycle.
 */
public abstract class Drawer {

    protected final SpriteBatch batch;

    private boolean disposed;

    protected Drawer() {
        batch = new SpriteBatch();
    }

    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    protected void end() {
        if (batch.isDrawing()) {
            batch.end();
        }
    }

    public void dispose() {
        if (disposed) {
            return;
        }

        batch.dispose();
        disposed = true;
    }
}
