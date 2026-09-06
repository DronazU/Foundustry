package foundustry.game;

import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.graphics.Atlas;
import foundustry.types.Player;

public class Game {

    private final Player player;

    public Game() {
        Atlas.load();
        Init.init();
        player = new Player(0, 0);
    }

    public void update() {
        player.update();
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);
        Init.drawBlock.render();
    }

    public void dispose() {
        Atlas.dispose();
    }
}