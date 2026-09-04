package foundustry.graphics.drawers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.graphics.Atlas;
import foundustry.world.Generator;
import foundustry.world.Tile;
import foundustry.world.content.Blocks;

public class DrawBlock {
    private SpriteBatch batch;
    private Tile[][] gameMap;

    public DrawBlock() {
        batch = new SpriteBatch();
        Atlas.load();

        Blocks.load();

        Generator generator = new Generator();
        generator.width = 40;
        generator.height = 30;

        gameMap = generator.generate();
    }


    public void render() {
        batch.begin();

        if (gameMap != null) {
            for (Tile[] tiles : gameMap) {
                for (Tile tile : tiles) {
                    if (tile != null) {
                        tile.draw(batch);
                    }
                }
            }
        }

        batch.end();
    }

    public void dispose() {
        if (batch != null) batch.dispose();
        Atlas.dispose();
    }
}
