package foundustry.ui.game;

import com.badlogic.gdx.graphics.Color;
import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.uiItems.Button;

public class CategoryMenu extends UI {

    private Category selectedCategory;

    public CategoryMenu() {
        Category[] categories = Category.values();

        float buttonWidth = 150;
        float buttonHeight = 50;
        float gap = 10;

        float totalWidth = categories.length * buttonWidth + (categories.length - 1) * gap;

        float startX = (com.badlogic.gdx.Gdx.graphics.getWidth() - totalWidth) / 2f;

        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];

            Button button = new Button(
                    startX + i * (buttonWidth + gap),
                    20,
                    buttonWidth,
                    buttonHeight,
                    category.localizedName
            );

            button.onHover(b -> b.setFillColor(Color.BLUE));

            button.onClick(b -> {
                selectedCategory = category;
            });

            items.add(button);
        }
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }
}
