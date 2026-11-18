package foundustry.graphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.log.Log;

public class Atlas {

    private static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas("textureAtlas.atlas");
    }

    public static TextureRegion find(String region) {
        if (atlas == null) {
            Log.warn("Atlas is not loaded, cannot find region " + region);
            return null;
        }

        TextureAtlas.AtlasRegion thisRegion = atlas.findRegion(region);

        if (thisRegion == null) {
            Log.warn("Region " + region + " not found.");
        }

        return thisRegion;
    }

    public static Array<TextureAtlas.AtlasRegion> findRegions(String name) {
        if (atlas == null) {
            Log.warn("Atlas is not loaded, cannot find regions " + name);
            return new Array<>();
        }

        return atlas.findRegions(name);
    }

    public static void dispose() {
        if (atlas != null) {
            atlas.dispose();
            atlas = null;
        }
    }
}
