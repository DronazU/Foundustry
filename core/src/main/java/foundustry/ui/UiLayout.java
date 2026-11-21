package foundustry.ui;

import com.badlogic.gdx.Gdx;

/**
 * Shared layout math for centered grids of equally sized slots.
 */
public final class UiLayout {

    private UiLayout() {
    }

    public static float centeredStartX(int count, float slotSize, float gap) {
        if (count <= 0) {
            return Gdx.graphics.getWidth() / 2f;
        }

        float totalWidth = count * slotSize + (count - 1) * gap;

        return (Gdx.graphics.getWidth() - totalWidth) / 2f;
    }

    public static float slotX(float startX, int index, int columns, float slotSize, float gap) {
        return startX + (index % columns) * (slotSize + gap);
    }

    public static float slotY(float startY, int index, int columns, float slotSize, float gap) {
        return startY - (index / columns) * (slotSize + gap);
    }

    public static boolean mouseOver(float x, float y, float width, float height) {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
