package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foundustry.graphics.Fonts;
import foundustry.types.item.Item;
import foundustry.ui.UI;
import foundustry.ui.UiLayout;
import foundustry.world.player.inventory.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryUI extends UI {
    private static final int COLUMNS = 8;

    private static final float SLOT_SIZE = 60f;
    private static final float GAP = 8f;
    private static final float SLOT_RADIUS = 8f;
    private static final float PANEL_RADIUS = 12f;

    private static final float PANEL_SIDE_MARGIN = 56f;
    private static final float PANEL_BOTTOM = 90f;
    private static final float PANEL_HEIGHT_MARGIN = 180f;
    private static final float GRID_TOP_MARGIN = 175f;
    private static final float PANEL_GAP = 20f;

    private static final float ITEM_INSET_X = 10f;
    private static final float ITEM_INSET_Y = 12f;
    private static final float ITEM_SIZE = 40f;

    private static final float GRID_WIDTH =
            COLUMNS * SLOT_SIZE + (COLUMNS - 1) * GAP;

    private static final float PANEL_WIDTH =
            GRID_WIDTH + PANEL_SIDE_MARGIN;

    private static final Color PANEL_COLOR =
            new Color(0.12f, 0.12f, 0.12f, 0.5f);

    private static final Color SLOT_COLOR =
            new Color(0.22f, 0.22f, 0.22f, 1f);

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
        if (!isVisible()) return;

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        List<Item> items = getSortedItems();

        for (int i = 0; i < items.size(); i++) {
            if (UiLayout.mouseOver(
                    slotX(i),
                    slotY(i),
                    SLOT_SIZE,
                    SLOT_SIZE
            )) {
                hotbar.setItem(
                        hotbar.getSelectedSlot(),
                        items.get(i)
                );
                return;
            }
        }
    }

    @Override
    public void render() {
        if (!isVisible()) return;

        List<Item> items = getSortedItems();

        float panelX = panelX();
        float panelHeight =
                Gdx.graphics.getHeight() - PANEL_HEIGHT_MARGIN;

        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(PANEL_COLOR);

        drawRoundedRect(
                panelX,
                PANEL_BOTTOM,
                PANEL_WIDTH,
                panelHeight,
                PANEL_RADIUS
        );

        shape.setColor(SLOT_COLOR);

        for (int i = 0; i < items.size(); i++) {
            drawRoundedRect(
                    slotX(i),
                    slotY(i),
                    SLOT_SIZE,
                    SLOT_SIZE,
                    SLOT_RADIUS
            );
        }

        shape.end();

        batch.begin();
        
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            if (item.region == null) continue;

            float x = slotX(i);
            float y = slotY(i);

            batch.draw(
                    item.region,
                    x + ITEM_INSET_X,
                    y + ITEM_INSET_Y,
                    ITEM_SIZE,
                    ITEM_SIZE
            );
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            float x = slotX(i);
            float y = slotY(i);

            Fonts.ui.draw(
                    batch,
                    String.valueOf(inventory.get(item)),
                    x + SLOT_SIZE - 25f,
                    y + 9f
            );
        }

        batch.end();
    }

    private void drawRoundedRect(
            float x,
            float y,
            float width,
            float height,
            float radius
    ) {
        shape.rect(
                x + radius,
                y,
                width - radius * 2f,
                height
        );

        shape.rect(
                x,
                y + radius,
                width,
                height - radius * 2f
        );

        shape.circle(x + radius, y + radius, radius, 12);
        shape.circle(x + width - radius, y + radius, radius, 12);
        shape.circle(x + radius, y + height - radius, radius, 12);
        shape.circle(
                x + width - radius,
                y + height - radius,
                radius,
                12
        );
    }

    private float panelX() {
        float totalWidth = PANEL_WIDTH * 2f + PANEL_GAP;

        return (Gdx.graphics.getWidth() - totalWidth) / 2f;
    }

    private float slotX(int index) {
        float startX = panelX()
                + (PANEL_WIDTH - GRID_WIDTH) / 2f;

        return UiLayout.slotX(
                startX,
                index,
                COLUMNS,
                SLOT_SIZE,
                GAP
        );
    }

    private float slotY(int index) {
        return UiLayout.slotY(
                Gdx.graphics.getHeight() - GRID_TOP_MARGIN,
                index,
                COLUMNS,
                SLOT_SIZE,
                GAP
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