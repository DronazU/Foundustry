
package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foundustry.graphics.Fonts;
import foundustry.types.Item;
import foundustry.ui.UI;
import foundustry.world.player.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryUI extends UI {
    private static final int COLUMNS = 8;
    private static final float SLOT_SIZE = 60f;
    private static final float GAP = 8f;

    private final Inventory inventory;
    private final Hotbar hotbar;

    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shape = new ShapeRenderer();

    public InventoryUI(Inventory inventory, Hotbar hotbar) {
        this.inventory = inventory;
        this.hotbar = hotbar;

        setVisible(false);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            setVisible(!isVisible());
        }

        if (!isVisible()) return;

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) return;

        List<Item> items = getSortedItems();

        float startX = (Gdx.graphics.getWidth()
                - COLUMNS * SLOT_SIZE - (COLUMNS - 1) * GAP) / 2f;

        float startY = Gdx.graphics.getHeight() - 150f;

        for (int i = 0; i < items.size(); i++) {
            int column = i % COLUMNS;
            int row = i / COLUMNS;

            float x = startX + column * (SLOT_SIZE + GAP);
            float y = startY - row * (SLOT_SIZE + GAP);

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= x && mouseX <= x + SLOT_SIZE
                    && mouseY >= y && mouseY <= y + SLOT_SIZE) {
                hotbar.setItem(hotbar.getSelectedSlot(), items.get(i));
                return;
            }
        }
    }

    @Override
    public void render() {
        if (!isVisible()) return;

        List<Item> items = getSortedItems();

        float startX = (Gdx.graphics.getWidth()
                - COLUMNS * SLOT_SIZE - (COLUMNS - 1) * GAP) / 2f;

        float startY = Gdx.graphics.getHeight() - 150f;

        float panelWidth = COLUMNS * SLOT_SIZE + (COLUMNS - 1) * GAP + 40f;
        float panelHeight = Gdx.graphics.getHeight() - 180f;
        float panelX = (Gdx.graphics.getWidth() - panelWidth) / 2f;

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.12f, 0.12f, 0.12f, 0.95f));
        shape.rect(panelX, 90, panelWidth, panelHeight);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < items.size(); i++) {
            int column = i % COLUMNS;
            int row = i / COLUMNS;

            float x = startX + column * (SLOT_SIZE + GAP);
            float y = startY - row * (SLOT_SIZE + GAP);

            shape.setColor(Color.DARK_GRAY);
            shape.rect(x, y, SLOT_SIZE, SLOT_SIZE);
        }

        shape.end();

        batch.begin();

        Fonts.ui.draw(batch, "Inventory", panelX + 20,
                Gdx.graphics.getHeight() - 90);

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            int column = i % COLUMNS;
            int row = i / COLUMNS;

            float x = startX + column * (SLOT_SIZE + GAP);
            float y = startY - row * (SLOT_SIZE + GAP);

            if (item.region != null) {
                batch.draw(item.region, x + 10, y + 12, 40, 40);
            }

            Fonts.ui.draw(batch, String.valueOf(inventory.get(item)),
                    x + SLOT_SIZE - 25, y + 8);
        }

        batch.end();
    }

    private List<Item> getSortedItems() {
        List<Item> result = new ArrayList<>(inventory.getItems().keySet());

        result.sort(Comparator
                .comparingInt((Item item) -> item.category.ordinal())
                .thenComparing(item -> item.name));

        return result;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
    }
}