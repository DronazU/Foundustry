package foundustry.ui.game;

import foundustry.types.Category;
import foundustry.ui.UI;

public class BuildMenu extends UI {

    private final CategoryMenu categoryMenu;

    public BuildMenu() {
        categoryMenu = new CategoryMenu();

        childrens.add(categoryMenu);
    }

    public Category getSelectedCategory() {
        return categoryMenu.getSelectedCategory();
    }
}