package foundustry.world;

import foundustry.types.Block;
import foundustry.types.item.Item;
import foundustry.types.recipe.Recipe;
import foundustry.world.contentType.block.production.GenericCrafter;
import foundustry.world.player.inventory.Inventory;

public class Building {
    public final Block block;

    public final int tileX;
    public final int tileY;

    public final int rotation;
    public final boolean flipX;

    public final Inventory inputInventory = new Inventory();
    public final Inventory outputInventory = new Inventory();

    public Recipe currentRecipe;
    public float craftProgress;

    public Building(Block block, int tileX, int tileY, int rotation, boolean flipX) {
        this.block = block;
        this.tileX = tileX;
        this.tileY = tileY;
        this.rotation = rotation;
        this.flipX = flipX;
    }

    public int getSize() {
        return block.size;
    }

    public void addInput(Item item, int amount) {
        inputInventory.add(item, amount);
    }

    public int getInput(Item item) {
        return inputInventory.get(item);
    }

    public int getOutput(Item item) {
        return outputInventory.get(item);
    }

    public boolean selectRecipe(Recipe recipe) {
        if (!(block instanceof GenericCrafter crafter)) return false;
        if (recipe == null || !crafter.getAvailableRecipes().contains(recipe, true)) return false;

        currentRecipe = recipe;
        craftProgress = 0f;

        return true;
    }
}