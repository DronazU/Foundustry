
package foundustry.ui.game;

import foundustry.ui.UI;
import foundustry.world.content.Block;

public class GameUI extends UI {

    private final BuildMenu buildMenu;

    public GameUI() {
        buildMenu = new BuildMenu();

        childrens.add(buildMenu);
    }

    public Block getSelectedBlock() {
        return buildMenu.getSelectedBlock();
    }
}
