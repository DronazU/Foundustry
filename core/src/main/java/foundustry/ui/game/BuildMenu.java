
package foundustry.ui.game;

import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.world.content.Block;

public class BuildMenu extends UI {

    private final CategoryMenu categoryMenu;
    private final BlockMenu blockMenu;

    private Category displayedCategory;
    private Block selectedBlock;

    public BuildMenu() {
        categoryMenu = new CategoryMenu();
        blockMenu = new BlockMenu();

        blockMenu.setSelectionListener(block -> {
            selectedBlock = block;

            blockMenu.setVisible(false);
        });

        categoryMenu.setCategoryListener(category -> {
            displayedCategory = category;

            blockMenu.setCategory(
                    category,
                    categoryMenu.getButtonX(Category.PRODUCTION)
            );

            categoryMenu.setVisible(true);
            blockMenu.setVisible(true);
        });

        childrens.add(categoryMenu);
        childrens.add(blockMenu);
    }

    @Override
    public void update() {
        super.update();

        Category selected = categoryMenu.getSelectedCategory();

        if (selected != displayedCategory) {
            displayedCategory = selected;

            blockMenu.setCategory(
                    selected,
                    categoryMenu.getButtonX(Category.PRODUCTION)
            );
        }
    }

    public Category getSelectedCategory() {
        return categoryMenu.getSelectedCategory();
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }
}
