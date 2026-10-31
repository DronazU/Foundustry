package foundustry.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.core.Vars;
import foundustry.types.blockTypes.Conveyor;
import foundustry.world.content.Block;

public class Tile {
    public float x, y;

    public Block floor;
    public Block block;
    public int rotation;

    public Tile(float x, float y, Block floor) {
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.block = null;
    }



    public void draw(
            SpriteBatch batch,
            Tile[][] map,
            int tileX,
            int tileY
    ) {
        float size = Vars.tileSize;

        // Поверхность
        if (floor != null && floor.region != null) {
            batch.draw(floor.region, x, y, size, size);
        }

        if (block == null) return;

        TextureRegion region = block.getRegion();

        if (block instanceof Conveyor conveyor) {
            Conveyor.CornerOrientation corner =
                    conveyor.getCornerOrientation(
                            map,
                            tileX,
                            tileY,
                            rotation
                    );

            if (corner != null) {
                TextureRegion cornerRegion =
                        conveyor.getCornerRegion(corner.flipX);

                if (cornerRegion != null) {
                    region = cornerRegion;
                }
            }
        }

        if (region != null) {
            batch.draw(
                    region,
                    x, y,
                    size / 2f, size / 2f,
                    size, size,
                    1f, 1f,
                    rotation
            );
        }
    }

}