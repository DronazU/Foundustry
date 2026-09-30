package foundustry.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;
import foundustry.world.content.UnitType;
import foundustry.world.content.Block;

public class Vars {
    public static int tileSize = 32;

    public static Array<UnitType> units = new Array<>();
    public static Array<Block> blocks = new Array<>();

    public static class Shadow {
        public static TextureRegion circleShadow = Atlas.find("circle-shadow");
        public static TextureRegion squareShadow = Atlas.find("square-shadow");
    }
}