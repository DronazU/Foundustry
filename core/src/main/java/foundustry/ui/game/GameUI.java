package foundustry.ui.game;

import foundustry.ui.UI;

public class GameUI extends UI {

    private final BuildMenu buildMenu;

    public GameUI() {
        buildMenu = new BuildMenu();

        childrens.add(buildMenu);
    }
}