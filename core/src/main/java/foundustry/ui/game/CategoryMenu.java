package foundustry.ui.game;

import com.badlogic.gdx.graphics.Color;
import foundustry.graphics.Atlas;
import foundustry.types.Category;
import foundustry.ui.UI;
import foundustry.ui.UiLayout;
import foundustry.ui.uiItems.Button;

import java.util.function.Consumer;

public class CategoryMenu extends UI {

    private static final float BUTTON_WIDTH = 150f;
    private static final float BUTTON_HEIGHT = 50f;
    private static final float GAP = 10f;
    private static final float Y = 20f;

    private Category selectedCategory;
    private Consumer<Category> categoryListener;

    public CategoryMenu() {
        Category[] categories = Category.values();

        float startX = startX();

        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];

            Button button = new Button(
                    UiLayout.slotX(startX, i, categories.length, BUTTON_WIDTH, GAP),
                    Y,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT,
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
        return UiLayout.slotX(
                startX(),
                category.ordinal(),
                Category.values().length,
                BUTTON_WIDTH,
                GAP
        );
    }

    public void setCategoryListener(Consumer<Category> listener) {
        categoryListener = listener;
    }

    private static float startX() {
        return UiLayout.centeredStartX(
                Category.values().length,
                BUTTON_WIDTH,
                GAP
        );
    }
}
