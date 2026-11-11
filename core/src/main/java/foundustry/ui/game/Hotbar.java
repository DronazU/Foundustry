
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

public class Hotbar extends UI {
    private static final int SLOT_COUNT = 10;
    private static final float SLOT_SIZE = 54f;
    private static final float GAP = 5f;

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
            float startX = (Gdx.graphics.getWidth()
                    - SLOT_COUNT * SLOT_SIZE
                    - (SLOT_COUNT - 1) * GAP) / 2f;

            float y = 15f;

            for (int i = 0; i < SLOT_COUNT; i++) {
                float x = startX + i * (SLOT_SIZE + GAP);

                float mouseX = Gdx.input.getX();
                float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (mouseX >= x && mouseX <= x + SLOT_SIZE
                        && mouseY >= y && mouseY <= y + SLOT_SIZE) {
                    selectedSlot = i;
                    break;
                }
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
        float startX = (Gdx.graphics.getWidth()
                - SLOT_COUNT * SLOT_SIZE
                - (SLOT_COUNT - 1) * GAP) / 2f;

        float y = 15f;

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < SLOT_COUNT; i++) {
            float x = startX + i * (SLOT_SIZE + GAP);

            shape.setColor(i == selectedSlot
                    ? Color.GOLD
                    : Color.DARK_GRAY);

            shape.rect(x, y, SLOT_SIZE, SLOT_SIZE);
        }

        shape.end();

        batch.begin();

        for (int i = 0; i < SLOT_COUNT; i++) {
            float x = startX + i * (SLOT_SIZE + GAP);
            Item item = slots[i];

            Fonts.ui.draw(batch,
                    i == 9 ? "0" : String.valueOf(i + 1),
                    x + 4, y + SLOT_SIZE - 4);

            if (item == null) continue;

            if (item.region != null) {
                batch.draw(item.region, x + 7, y + 9, 40, 40);
            }

            int amount = inventory.get(item);

            Fonts.ui.draw(batch, String.valueOf(amount),
                    x + SLOT_SIZE - 25, y + 10);
        }

        batch.end();
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
