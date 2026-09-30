package foundustry.core.game;

import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.core.Init;
import foundustry.graphics.Atlas;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;
import foundustry.types.UnitTypes.Player;
import foundustry.ui.game.MainMenu;
import foundustry.world.Generator;
import foundustry.world.content.Blocks;
import foundustry.world.content.UnitTypes;

import static foundustry.world.Generator.map;

public class Game {
    private final Player player;
    private final MainMenu mainMenu = new MainMenu();
    private final GameState gameState = new GameState();

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
        UnitTypes.load();
        player = new Player();
        map = generator.createMap(generator.width, generator.height);
    }

    public void update() {
        if (gameState.isMenu()) {
            mainMenu.update();
            return;
        }

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