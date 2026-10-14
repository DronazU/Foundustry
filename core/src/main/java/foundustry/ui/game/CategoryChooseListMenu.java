package foundustry.ui.game;

import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.UIItem;
import foundustry.ui.UIItems.Button;
import foundustry.world.content.Block;
import foundustry.world.content.Blocks;

public class CategoryChooseListMenu extends UI {

    private Category category;

    public CategoryChooseListMenu() {
        setVisible(false);
    }

    public void setCategory(Category category) {
        this.category = category;

        for (UIItem item : items) {
            item.dispose();
        }
        items.clear();

        if (category == null) return;

        int index = 0;

        for (Block block : Blocks.getByCategory(category)) {
            float x = 20 + index * 160;
            float y = 90;

            Button button = new Button(
                    x, y, 150, 50, block.name
            );

            items.add(button);
            index++;
        }
    }

    public Category getCategory() {
        return category;
    }
}
