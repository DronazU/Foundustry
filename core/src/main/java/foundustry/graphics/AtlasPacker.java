package foundustry.graphics;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import foundustry.log.Log;

public class AtlasPacker {
    static Log Log = new Log();

    public static void main(String[] args) {
        try {
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.combineSubdirectories = true;
            settings.flattenPaths = true;
            settings.maxWidth = 4096;
            settings.maxHeight = 4096;

            TexturePacker.process(settings, "core/assets-raw/", "core/assets/", "textureAtlas");
            Log.info("Atlas packed.");
        } catch (Exception e) {
            Log.errExc(e);
        }
    }
}