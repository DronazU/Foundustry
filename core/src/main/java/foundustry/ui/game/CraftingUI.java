package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.graphics.Fonts;
import foundustry.types.item.Item;
import foundustry.ui.UI;
import foundustry.ui.UiLayout;
import foundustry.world.player.inventory.CraftingEntry;
import foundustry.world.player.inventory.CraftingQueue;
import foundustry.world.player.inventory.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CraftingUI extends UI {
    private static final int COLUMNS = 8;
    private static final int QUEUE_COLUMNS = 6;

    private static final float SLOT_SIZE = 60f;
    private static final float GAP = 8f;

    private static final float PANEL_SIDE_MARGIN = 40f;
    private static final float PANEL_BOTTOM = 90f;
    private static final float PANEL_HEIGHT_MARGIN = 180f;
    private static final float PANEL_TITLE_MARGIN = 90f;
    private static final float GRID_TOP_MARGIN = 150f;
    private static final float PANEL_GAP = 20f;

    private static final float ITEM_SIZE = 40f;
    private static final float QUEUE_SLOT_SIZE = 54f;
    private static final float QUEUE_GAP = 8f;
    private static final float QUEUE_TITLE_Y = 370f;
    private static final float QUEUE_START_Y = 300f;

    private static final float GRID_WIDTH =
            COLUMNS * SLOT_SIZE + (COLUMNS - 1) * GAP;

    private static final float PANEL_WIDTH =
            GRID_WIDTH + PANEL_SIDE_MARGIN;

    private static final Color PANEL_COLOR =
            new Color(0.12f, 0.12f, 0.12f, 0.95f);

    private final Inventory inventory;
    private final CraftingQueue queue;

    private final List<Item> sortedItems = new ArrayList<>();

    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shape = new ShapeRenderer();

    public CraftingUI(Inventory inventory, CraftingQueue queue) {
        this.inventory = inventory;
        this.queue = queue;

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
            Item item = items.get(i);

            if (!UiLayout.mouseOver(
                    slotX(i),
                    slotY(i),
                    SLOT_SIZE,
                    SLOT_SIZE
            )) {
                continue;
            }

            if (inventory.getCraftableAmount(item) > 0
                    && item.craftTime > 0f) {
                queue.add(item, 1, inventory);
            }

            return;
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
        shape.rect(
                panelX + PANEL_WIDTH + PANEL_GAP,
                PANEL_BOTTOM,
                PANEL_WIDTH,
                panelHeight
        );

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            int craftable = inventory.getCraftableAmount(item);

            if (!item.inventoryCraftable
                    || item.craftTime <= 0f
                    || item.requirements.getItems().size == 0) {
                shape.setColor(Color.RED);
            } else if (craftable > 0) {
                shape.setColor(Color.LIGHT_GRAY);
            } else {
                shape.setColor(Color.GRAY);
            }

            shape.rect(
                    slotX(i),
                    slotY(i),
                    SLOT_SIZE,
                    SLOT_SIZE
            );
        }

        Array<CraftingEntry> entries = queue.getEntries();

        for (int i = 0; i < entries.size; i++) {
            float x = queueSlotX(i);
            float y = queueSlotY(i);

            shape.setColor(Color.DARK_GRAY);
            shape.rect(x, y, QUEUE_SLOT_SIZE, QUEUE_SLOT_SIZE);
        }

        shape.end();

        batch.begin();

        Fonts.ui.draw(
                batch,
                "Crafting",
                panelX + PANEL_WIDTH + PANEL_GAP + 20f,
                Gdx.graphics.getHeight() - PANEL_TITLE_MARGIN
        );

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            float x = slotX(i);
            float y = slotY(i);

            if (item.region != null) {
                batch.draw(
                        item.region,
                        x + 10f,
                        y + 12f,
                        ITEM_SIZE,
                        ITEM_SIZE
                );
            }

            int craftable = inventory.getCraftableAmount(item);

            if (item.inventoryCraftable
                    && item.craftTime > 0f
                    && item.requirements.getItems().size > 0) {
                Fonts.ui.draw(
                        batch,
                        String.valueOf(craftable),
                        x + SLOT_SIZE - 22f,
                        y + 9f
                );
            }
        }

        Fonts.ui.draw(
                batch,
                "Crafting queue",
                panelX + PANEL_WIDTH + PANEL_GAP + 20f,
                QUEUE_TITLE_Y
        );

        for (int i = 0; i < entries.size; i++) {
            CraftingEntry entry = entries.get(i);

            float x = queueSlotX(i);
            float y = queueSlotY(i);

            if (entry.item.region != null) {
                batch.draw(
                        entry.item.region,
                        x + 7f,
                        y + 7f,
                        40f,
                        40f
                );
            }

            Fonts.ui.draw(
                    batch,
                    String.valueOf(entry.amount),
                    x + 4f,
                    y + 10f
            );

            if (i == 0) {
                Fonts.ui.draw(
                        batch,
                        String.format("%.1f", queue.getRemainingTime()),
                        x + 13f,
                        y + 31f
                );
            }
        }

        batch.end();
    }

    private float panelX() {
        float totalWidth = PANEL_WIDTH * 2f + PANEL_GAP;

        return (Gdx.graphics.getWidth() - totalWidth) / 2f;
    }

    private float slotX(int index) {
        float startX = panelX()
                + PANEL_WIDTH
                + PANEL_GAP
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

    private float queueSlotX(int index) {
        float startX = panelX()
                + PANEL_WIDTH
                + PANEL_GAP
                + 20f;

        return UiLayout.slotX(
                startX,
                index,
                QUEUE_COLUMNS,
                QUEUE_SLOT_SIZE,
                QUEUE_GAP
        );
    }

    private float queueSlotY(int index) {
        return QUEUE_START_Y
                - (index / QUEUE_COLUMNS)
                * (QUEUE_SLOT_SIZE + QUEUE_GAP);
    }

    private List<Item> getSortedItems() {
        sortedItems.clear();
        for (Item item : Vars.items) {
            sortedItems.add(item);
        }

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