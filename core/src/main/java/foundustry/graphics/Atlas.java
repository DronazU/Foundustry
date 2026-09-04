package foundustry.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import foundustry.log.Log;

public class Atlas {
    static Log Log = new Log();

    static SpriteBatch batch;
    static TextureAtlas atlas;

    public static void load() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("textureAtlas.atlas");
    }

    public static TextureAtlas.AtlasRegion find(String region) {
        TextureAtlas.AtlasRegion thisRegion = atlas.findRegion(region);
        if (thisRegion == null) Log.warn("Region " + region + " not found.");
        return thisRegion;
    }

    public static void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (atlas != null) {
            atlas.dispose();
        }
    }
}
