package foundustry.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.core.Init;
import foundustry.core.game.data.GameFiles;
import foundustry.core.game.data.database.SaveManager;
import foundustry.graphics.Atlas;
import foundustry.graphics.Fonts;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;
import foundustry.types.unitTypes.Player;
import foundustry.ui.game.GameUI;
import foundustry.ui.game.MainMenu;
import foundustry.world.Generator;
import foundustry.world.content.Blocks;
import foundustry.world.content.UnitTypes;

import static foundustry.world.Generator.map;

public class Game {
    private final Player player;
    private final SaveManager saveManager;
    private final GameState gameState = new GameState();
    private final MainMenu mainMenu = new MainMenu(gameState);
    private final GameUI gameUI = new GameUI();

    Generator generator = new Generator() {{
        width = 300;
        height = 300;
        scale = 0.01f;
        seed = (float)Math.random() * 1000;
    }};

    public Game() {
        Fonts.load();
        Atlas.load();
        Init.init();
        GameFiles.init();
        Blocks.load();
        UnitTypes.load();

        player = new Player();
        map = generator.createMap(generator.width, generator.height);

        saveManager = new SaveManager();

        float[] position = saveManager.loadPosition();

        if (position != null) {
            player.unit.x = position[0];
            player.unit.y = position[1];
        }
    }

    public void update() {

        if (gameState.isPaused()) {
            return;
        }

        if (gameState.isMenu()) {
            mainMenu.update();
            return;
        }

        gameUI.update();
        player.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            saveManager.savePosition(
                    player.unit.x,
                    player.unit.y
            );
        }
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);

        if (gameState.isMenu()) {
            mainMenu.render();
            return;
        }

        Init.drawBlock.render();
        Init.drawUnit.render();

        gameUI.render();
    }

    public void dispose() {
        mainMenu.dispose();
        gameUI.dispose();

        Atlas.dispose();
        DrawBlock.dispose();
        DrawUnit.dispose();
        Fonts.dispose();
        saveManager.close();
    }
}