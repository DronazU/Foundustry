
package foundustry.ui.game;

import foundustry.types.Item;
import foundustry.ui.UI;
import foundustry.world.player.Inventory;

public class GameUI extends UI {
    private final Hotbar hotbar;
    private final InventoryUI inventoryUI;

    public GameUI(Inventory inventory) {
        hotbar = new Hotbar(inventory);
        inventoryUI = new InventoryUI(inventory, hotbar);

        childrens.add(hotbar);
        childrens.add(inventoryUI);
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
}
