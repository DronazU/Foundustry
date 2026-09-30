package foundustry.ui.game;

import foundustry.ui.UI;
import foundustry.ui.UIItems.Button;
import foundustry.ui.UIItems.Label;

public class MainMenu extends UI {

    private final Label title;
    private final Button play;
    private final Button settings;
    private final Button exit;

    public MainMenu() {
        title = new Label(600, 700, "FOUNDustry");

        play = new Button(500, 450, 200, 60, "PLAY");
        settings = new Button(500, 370, 200, 60, "SETTINGS");
        exit = new Button(500, 290, 200, 60, "EXIT");
    }

    @Override
    public void update() {
        title.update();
        play.update();
        settings.update();
        exit.update();
    }

    @Override
    public void render() {
        title.render();

        play.render();
        settings.render();
        exit.render();
    }

    @Override
    public void dispose() {

    }
}