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

public class Hotbar extends UI {
    private static final int SLOT_COUNT = 10;
    private static final float SLOT_SIZE = 54f;
    private static final float GAP = 5f;
    private static final float Y = 15f;

    private static final float ITEM_INSET_X = 7f;
    private static final float ITEM_INSET_Y = 9f;
    private static final float ITEM_SIZE = 40f;

    private final Inventory inventory;
    private final Item[] slots = new Item[SLOT_COUNT];

    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shape = new ShapeRenderer();

    private int selectedSlot;

    public Hotbar(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void update() {
        for (int i = 0; i < SLOT_COUNT; i++) {
            int key = i == 9 ? Input.Keys.NUM_0 : Input.Keys.NUM_1 + i;

            if (Gdx.input.isKeyJustPressed(key)) {
                selectedSlot = i;
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int hovered = slotAtMouse();

            if (hovered >= 0) {
                selectedSlot = hovered;
            }
        }
    }

    public void scroll(float amountY) {
        if (amountY == 0) return;

        selectedSlot = (selectedSlot + (int) amountY + SLOT_COUNT)
                % SLOT_COUNT;
    }

    @Override
    public void render() {
        float startX = startX();

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < SLOT_COUNT; i++) {
            shape.setColor(i == selectedSlot
                    ? Color.GOLD
                    : Color.DARK_GRAY);

            shape.rect(
                    UiLayout.slotX(startX, i, SLOT_COUNT, SLOT_SIZE, GAP),
                    Y,
                    SLOT_SIZE,
                    SLOT_SIZE
            );
        }

        shape.end();

        batch.begin();

        for (int i = 0; i < SLOT_COUNT; i++) {
            float x = UiLayout.slotX(startX, i, SLOT_COUNT, SLOT_SIZE, GAP);
            Item item = slots[i];

            Fonts.ui.draw(batch,
                    i == 9 ? "0" : String.valueOf(i + 1),
                    x + 4, Y + SLOT_SIZE - 4);

            if (item == null) continue;

            if (item.region != null) {
                batch.draw(item.region,
                        x + ITEM_INSET_X,
                        Y + ITEM_INSET_Y,
                        ITEM_SIZE,
                        ITEM_SIZE);
            }

            int amount = inventory.get(item);

            Fonts.ui.draw(batch, String.valueOf(amount),
                    x + SLOT_SIZE - 25, Y + 10);
        }

        batch.end();
    }

    private int slotAtMouse() {
        float startX = startX();

        for (int i = 0; i < SLOT_COUNT; i++) {
            float x = UiLayout.slotX(startX, i, SLOT_COUNT, SLOT_SIZE, GAP);

            if (UiLayout.mouseOver(x, Y, SLOT_SIZE, SLOT_SIZE)) {
                return i;
            }
        }

        return -1;
    }

    private static float startX() {
        return UiLayout.centeredStartX(SLOT_COUNT, SLOT_SIZE, GAP);
    }

    public void setItem(int slot, Item item) {
        if (slot < 0 || slot >= SLOT_COUNT) return;

        slots[slot] = item;
    }

    public Item getItem(int slot) {
        if (slot < 0 || slot >= SLOT_COUNT) return null;

        return slots[slot];
    }

    public Item getSelectedItem() {
        return slots[selectedSlot];
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
    }
}
