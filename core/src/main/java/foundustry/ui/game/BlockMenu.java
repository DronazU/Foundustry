
package foundustry.ui.game;

import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.uiItems.Button;
import foundustry.world.content.Block;
import foundustry.world.content.Blocks;

import java.util.function.Consumer;

public class BlockMenu extends UI {

    private Category category;
    private Block selectedBlock;
    private Consumer<Block> selectionListener;

    public void setCategory(Category category, float productionX) {
        this.category = category;

        for (var item : items) {
            item.dispose();
        }
        items.clear();

        if (category == null) return;

        float buttonSize = 50f;
        float gap = 10f;
        float startX = 20f;
        float startY = 100f;
        int columns = 7;
        int limit = 15;

        int index = 0;

        for (Block block : Blocks.getByCategory(category)) {
            if (index >= limit) break;

            int column = index % columns;
            int row = index / columns;

            float rowStartX = row == 0 ? startX : productionX;
            float x = rowStartX + column * (buttonSize + gap);
            float y = startY + row * (buttonSize + gap);

            Button button = new Button(
                    x, y, buttonSize, buttonSize, block.region
            );

            button.onClick(b -> {
                selectedBlock = block;

                if (selectionListener != null) {
                    selectionListener.accept(block);
                }
            });

            items.add(button);
            index++;
        }
    }

    public void setSelectionListener(Consumer<Block> listener) {
        selectionListener = listener;
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public Category getCategory() {
        return category;
    }
}
