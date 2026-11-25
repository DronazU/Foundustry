package foundustry.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.types.Block;
import foundustry.types.item.Item;
import foundustry.types.item.ItemStack;
import foundustry.types.recipe.Recipe;
import foundustry.world.contentType.block.production.GenericCrafter;
import foundustry.world.player.inventory.Inventory;

public class Building {
    public final Block block;

    public final int tileX;
    public final int tileY;

    public final int rotation;
    public final boolean flipX;
    public float animationTime;

    public final Inventory inputInventory = new Inventory();
    public final Inventory outputInventory = new Inventory();

    public Recipe currentRecipe;
    public float craftProgress;
    public boolean isCrafting;

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

        if (isCrafting && recipe != currentRecipe) return false;
        if (recipe == currentRecipe) return true;

        currentRecipe = recipe;
        craftProgress = 0f;

        return true;
    }

    public void update(float delta) {
        if (block instanceof GenericCrafter && currentRecipe == null) {
            animationTime += delta;
            return;
        }

        if (!(block instanceof GenericCrafter)
                || currentRecipe == null
                || currentRecipe.craftTime <= 0f
                || delta <= 0f) {
            return;
        }

        while (delta > 0f) {
            if (!isCrafting) {
                if (!inputInventory.removeAll(currentRecipe.inputs)) {
                    return;
                }

                isCrafting = true;
            }

            float remaining = currentRecipe.craftTime - craftProgress;
            float elapsed = Math.min(delta, remaining);

            craftProgress += elapsed;
            animationTime += elapsed;
            delta -= elapsed;

            if (craftProgress >= currentRecipe.craftTime) {
                for (ItemStack stack : currentRecipe.outputs.getItems()) {
                    outputInventory.add(stack.item, stack.amount);
                }

                craftProgress = 0f;
                animationTime = 0f;
                isCrafting = false;
            }
        }
    }

    public TextureRegion getDrawRegion() {
        if (block instanceof GenericCrafter crafter) {
            return crafter.getWorkingRegion(animationTime);
        }

        return block.getRegion();
    }
}