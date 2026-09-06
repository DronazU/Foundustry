package foundustry.game;

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
        Init.drawBlock.render();
    }

    public void dispose() {
        Atlas.dispose();
    }
}