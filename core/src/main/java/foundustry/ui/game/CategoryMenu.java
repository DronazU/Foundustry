package foundustry.ui.game;

import com.badlogic.gdx.graphics.Color;
import foundustry.graphics.Atlas;
import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.uiItems.Button;

import java.util.function.Consumer;

public class CategoryMenu extends UI {

    private Category selectedCategory;
    private Consumer<Category> categoryListener;

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
                    Atlas.find("category-" + category.name().toLowerCase())
            );

            button.onHover(b -> b.setFillColor(Color.BLUE));

            button.onClick(b -> {
                selectedCategory = category;

                if (categoryListener != null) {
                    categoryListener.accept(category);
                }
            });

            items.add(button);
        }
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public float getButtonX(Category category) {
        float buttonWidth = 150f;
        float gap = 10f;

        float totalWidth = Category.values().length * buttonWidth
                + (Category.values().length - 1) * gap;

        float startX = (com.badlogic.gdx.Gdx.graphics.getWidth()
                - totalWidth) / 2f;

        return startX + category.ordinal() * (buttonWidth + gap);
    }

    public void setCategoryListener(Consumer<Category> listener) {
        categoryListener = listener;
    }
}
