package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foundustry.graphics.Fonts;
import foundustry.types.Item;
import foundustry.ui.UI;
import foundustry.ui.UiLayout;
import foundustry.world.player.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryUI extends UI {
    private static final int COLUMNS = 8;
    private static final float SLOT_SIZE = 60f;
    private static final float GAP = 8f;

    private static final float PANEL_SIDE_MARGIN = 40f;
    private static final float PANEL_BOTTOM = 90f;
    private static final float PANEL_HEIGHT_MARGIN = 180f;
    private static final float PANEL_TITLE_MARGIN = 90f;
    private static final float GRID_TOP_MARGIN = 150f;
    private static final float TITLE_INSET_X = 20f;

    private static final float ITEM_INSET_X = 10f;
    private static final float ITEM_INSET_Y = 12f;
    private static final float ITEM_SIZE = 40f;

    private static final Color PANEL_COLOR = new Color(0.12f, 0.12f, 0.12f, 0.95f);

    private final Inventory inventory;
    private final Hotbar hotbar;

    private final List<Item> sortedItems = new ArrayList<>();

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

        for (int i = 0; i < items.size(); i++) {
            if (UiLayout.mouseOver(slotX(i), slotY(i), SLOT_SIZE, SLOT_SIZE)) {
                hotbar.setItem(hotbar.getSelectedSlot(), items.get(i));
                return;
            }
        }
    }

    @Override
    public void render() {
        if (!isVisible()) return;

        List<Item> items = getSortedItems();

        float gridWidth = COLUMNS * SLOT_SIZE + (COLUMNS - 1) * GAP;
        float panelWidth = gridWidth + PANEL_SIDE_MARGIN;
        float panelHeight = Gdx.graphics.getHeight() - PANEL_HEIGHT_MARGIN;
        float panelX = (Gdx.graphics.getWidth() - panelWidth) / 2f;

        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(PANEL_COLOR);
        shape.rect(panelX, PANEL_BOTTOM, panelWidth, panelHeight);

        shape.setColor(Color.DARK_GRAY);

        for (int i = 0; i < items.size(); i++) {
            shape.rect(slotX(i), slotY(i), SLOT_SIZE, SLOT_SIZE);
        }

        shape.end();

        batch.begin();

        Fonts.ui.draw(batch, "Inventory",
                panelX + TITLE_INSET_X,
                Gdx.graphics.getHeight() - PANEL_TITLE_MARGIN);

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            float x = slotX(i);
            float y = slotY(i);

            if (item.region != null) {
                batch.draw(item.region,
                        x + ITEM_INSET_X,
                        y + ITEM_INSET_Y,
                        ITEM_SIZE,
                        ITEM_SIZE);
            }

            Fonts.ui.draw(batch, String.valueOf(inventory.get(item)),
                    x + SLOT_SIZE - 25, y + 8);
        }

        batch.end();
    }

    private float slotX(int index) {
        return UiLayout.slotX(
                UiLayout.centeredStartX(COLUMNS, SLOT_SIZE, GAP),
                index, COLUMNS, SLOT_SIZE, GAP
        );
    }

    private float slotY(int index) {
        return UiLayout.slotY(
                Gdx.graphics.getHeight() - GRID_TOP_MARGIN,
                index, COLUMNS, SLOT_SIZE, GAP
        );
    }

    private List<Item> getSortedItems() {
        sortedItems.clear();
        sortedItems.addAll(inventory.getItems().keySet());

        sortedItems.sort(Comparator
                .comparingInt((Item item) -> item.category.ordinal())
                .thenComparing(item -> item.name));

        return sortedItems;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
    }
}
