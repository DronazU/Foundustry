package foundustry.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import foundustry.log.Log;

public class Atlas {
    static Log Log = new Log();

    static SpriteBatch batch;
    static TextureAtlas atlas;

    public static void loadAtlas() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("core/assets/textureAtlas.atlas");
    }

    public static TextureAtlas.AtlasRegion find(String region) {
        TextureAtlas.AtlasRegion thisRegion = atlas.findRegion(region);
        if (thisRegion == null) Log.warn("Region " + region + "not found.");
        return thisRegion;
    }
}
