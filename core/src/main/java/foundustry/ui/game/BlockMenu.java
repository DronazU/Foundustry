package foundustry.ui.game;

import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.uiItems.Button;
import foundustry.world.content.Block;
import foundustry.world.content.Blocks;

public class BlockMenu extends UI {

    private Category category;


    public void setCategory(Category category) {
        this.category = category;
        items.clear();

        if (category == null) return;

        float buttonSize = 50f;
        float gap = 10f;
        float startX = 20f;
        float y = 100f;

        int index = 0;

        for (Block block : Blocks.getByCategory(category)) {
            Button button = new Button(
                    startX + index * (buttonSize + gap),
                    y,
                    buttonSize,
                    buttonSize,
                    block.region
            );

            items.add(button);
            index++;
        }
    }


    public Category getCategory() {
        return category;
    }
}