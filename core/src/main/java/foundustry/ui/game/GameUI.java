package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import foundustry.core.game.Time;
import foundustry.types.item.Item;
import foundustry.ui.UI;
import foundustry.world.player.inventory.CraftingQueue;
import foundustry.world.player.inventory.Inventory;

public class GameUI extends UI {
    private final Inventory inventory;

    private final Hotbar hotbar;
    private final InventoryUI inventoryUI;
    private final CraftingUI craftingUI;

    private final CraftingQueue craftingQueue = new CraftingQueue();

    public GameUI(Inventory inventory) {
        this.inventory = inventory;

        hotbar = new Hotbar(inventory);
        inventoryUI = new InventoryUI(inventory, hotbar);
        craftingUI = new CraftingUI(inventory, craftingQueue);

        craftingUI.setVisible(false);

        childrens.add(hotbar);
        childrens.add(inventoryUI);
        childrens.add(craftingUI);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            boolean visible = !inventoryUI.isVisible();

            inventoryUI.setVisible(visible);
            craftingUI.setVisible(visible);
        }

        craftingQueue.update(Time.delta, inventory);

        super.update();
    }

    public Item getSelectedItem() {
        return hotbar.getSelectedItem();
    }

    public void scrollHotbar(float amountY) {
        hotbar.scroll(amountY);
    }

    public boolean isInventoryOpen() {
        return inventoryUI.isVisible();
    }

    public CraftingQueue getCraftingQueue() {
        return craftingQueue;
    }
}