package foundustry.ui.game;

import com.badlogic.gdx.graphics.Color;
import foundustry.ui.UI;
import foundustry.ui.UIItems.Button;

public class BuildMenu extends UI {

    private final Button build;

    public BuildMenu() {
        build = new Button(500, 50, 200, 60, "BUILD");

        build.onHover(button -> {
            button.setFillColor(Color.BLUE);
        });

        items.add(build);
    }
}