package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import foundustry.core.game.GameState;
import foundustry.ui.UI;
import foundustry.ui.UIItems.Button;
import foundustry.ui.UIItems.Label;

public class MainMenu extends UI {

    private final Label title;
    private final Button play;
    private final Button settings;
    private final Button exit;
    private final GameState state;

    public MainMenu(GameState state) {
        this.state = state;

        title = new Label(600, 700, "FOUNDustry");
        play = new Button(500, 450, 200, 60, "PLAY");
        play.onHover(button -> {
            button.setFillColor(Color.BLUE);
        });
        play.onClick(button -> {
            state.setState(GameState.State.GAME);
        });
        settings = new Button(500, 370, 200, 60, "SETTINGS");
        settings.onHover(button -> {
            button.setFillColor(Color.BLUE);
        });
        exit = new Button(500, 290, 200, 60, "EXIT");
        exit.onHover(button -> {
            button.setFillColor(Color.BLUE);
        });
        exit.onClick(button -> {
            Gdx.app.exit();
        });

        items.add(title);
        items.add(play);
        items.add(settings);
        items.add(exit);
    }
}