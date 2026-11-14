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
import foundustry.types.Block;
import foundustry.ui.game.GameUI;
import foundustry.ui.game.MainMenu;
import foundustry.world.Chunk;
import foundustry.world.World;
import foundustry.world.content.Blocks;
import foundustry.world.content.Items;
import foundustry.world.content.UnitTypes;
import foundustry.world.player.Player;

public class Game {
    private final Player player;
    private final SaveManager saveManager;
    private final GameState gameState = new GameState();
    private final MainMenu mainMenu = new MainMenu(gameState);
    private final GameUI gameUI;

    private World world;

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
        player.inventory.add(Items.cornerConveyor, 31);

        gameUI = new GameUI(player.inventory);
        player.setGameUI(gameUI);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                gameUI.scrollHotbar(amountY);
                return true;
            }
        });

        saveManager = new SaveManager();

        SaveManager.WorldData worldData = saveManager.loadWorld();

        if (worldData == null) {
            worldData = saveManager.createWorld(
                    (float)Math.random() * 1000f
            );
        }

        world = new World(
                worldData.seed,
                worldData.id,
                saveManager
        );

        player.setWorld(world);

        Init.camera.position.set(
                player.unit.x,
                player.unit.y,
                0
        );
    }

    public void update() {
        Time.update();

        if (gameState.isPaused()) {
            return;
        }

        if (gameState.isMenu()) {
            mainMenu.update();

            if (gameState.consumeNewWorldRequest()) {
                createNewWorld();
            }

            return;
        }

        for (Block block : Vars.blocks) {
            block.update(Time.delta);
        }

        gameUI.update();
        player.update();

        world.update(player.unit.x, player.unit.y);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            saveGame();
        }
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);

        if (gameState.isMenu()) {
            mainMenu.render();
            return;
        }

        Init.drawBlock.render(world);
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

        saveGame();
        saveManager.close();
    }

    private void createNewWorld() {
        SaveManager.WorldData worldData = saveManager.createWorld(
                (float)Math.random() * 1000f
        );

        world = new World(
                worldData.seed,
                worldData.id,
                saveManager
        );

        player.setWorld(world);

        player.unit.x = Chunk.SIZE * Vars.tileSize / 2f;
        player.unit.y = Chunk.SIZE * Vars.tileSize / 2f;

        player.unit.velocityX = 0f;
        player.unit.velocityY = 0f;
        player.unit.rotation = 0f;

        world.update(player.unit.x, player.unit.y);

        player.inventory.clear();

        player.inventory.add(Items.unrefinedCopper, 75);
        player.inventory.add(Items.unrefinedIron, 24);
        player.inventory.add(Items.conveyor, 40);

        Init.camera.position.set(
                player.unit.x,
                player.unit.y,
                0
        );

        saveManager.savePosition(
                player.unit.x,
                player.unit.y
        );

        gameState.setState(GameState.State.GAME);
    }

    private void saveGame() {
        world.save();

        saveManager.savePosition(
                player.unit.x,
                player.unit.y
        );
    }
}