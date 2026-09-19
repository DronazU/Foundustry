package foundustry.game;

import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.graphics.Atlas;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.types.Player;
import foundustry.world.Generator;
import foundustry.world.content.Blocks;

import static foundustry.world.Generator.map;

public class Game {

    private final Player player;
    Generator generator = new Generator();

    public Game() {
        Atlas.load();
        Init.init();
        player = new Player(0, 0);
        map = generator.createMap(300, 300);
    }

    public void update() {
        render();
        player.update();
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);
        Init.drawBlock.render();
    }

    public void dispose() {
        Atlas.dispose();
        DrawBlock.dispose();
    }

    public void load() {
        Atlas.load();
        Blocks.load();
    }
}