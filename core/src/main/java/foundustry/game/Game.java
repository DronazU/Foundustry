package foundustry.game;

import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.graphics.Atlas;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;
import foundustry.types.UnitTypes.Player;
import foundustry.world.Generator;
import foundustry.world.content.Blocks;

import static foundustry.world.Generator.map;

public class Game {

    private final Player player;
    Generator generator = new Generator() {{
        width = 300;
        height = 300;
        scale = 0.01f;
        seed = (float)Math.random() * 1000;
    }};

    public Game() {
        Atlas.load();
        Init.init();
        Blocks.load();
        player = new Player(0, 0);
        map = generator.createMap(generator.width, generator.height);
    }

    public void update() {
        render();
        player.update();
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);
        Init.drawBlock.render();
        Init.drawUnit.render();
    }

    public void dispose() {
        Atlas.dispose();
        DrawBlock.dispose();
        DrawUnit.dispose();
    }
}