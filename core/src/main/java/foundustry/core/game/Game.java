
package foundustry.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.core.Init;
import foundustry.core.Vars;
import foundustry.core.game.data.GameFiles;
import foundustry.core.game.data.database.SaveManager;
import foundustry.graphics.Atlas;
import foundustry.graphics.Fonts;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;
import foundustry.world.player.Player;
import foundustry.ui.game.GameUI;
import foundustry.ui.game.MainMenu;
import foundustry.world.Generator;
import foundustry.types.Block;
import foundustry.world.content.Blocks;
import foundustry.world.content.Items;
import foundustry.world.content.UnitTypes;

import static foundustry.world.Generator.map;

public class Game {
    private final Player player;
    private final SaveManager saveManager;
    private final GameState gameState = new GameState();
    private final MainMenu mainMenu = new MainMenu(gameState);
    private final GameUI gameUI;

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
        Items.load();
        UnitTypes.load();

        player = new Player();

        player.inventory.add(Items.unrefinedCopper, 75);
        player.inventory.add(Items.unrefinedIron, 24);
        player.inventory.add(Items.conveyor, 40);

        gameUI = new GameUI(player.inventory);
        player.setGameUI(gameUI);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                gameUI.scrollHotbar(amountY);
                return true;
            }
        });

        map = generator.createMap(generator.width, generator.height);

        saveManager = new SaveManager();

        float[] position = saveManager.loadPosition();

        if (position != null) {
            player.unit.x = position[0];
            player.unit.y = position[1];
        }
    }

    public void update() {
        Time.update();

        if (gameState.isPaused()) {
            return;
        }

        if (gameState.isMenu()) {
            mainMenu.update();
            return;
        }

        for (Block block : Vars.blocks) {
            block.update(Time.delta);
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