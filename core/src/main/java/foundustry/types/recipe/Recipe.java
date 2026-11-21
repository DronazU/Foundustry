package foundustry.types.recipe;

import com.badlogic.gdx.utils.ObjectSet;
import foundustry.core.Vars;
import foundustry.types.item.Item;
import foundustry.types.item.ItemStack;
import foundustry.types.item.ItemStackList;

public class Recipe {
    public final String displayName;
    public final float craftTime;

    public final ItemStackList inputs = new ItemStackList();
    public final ItemStackList outputs = new ItemStackList();

    public final ObjectSet<ProductionType> productionTypes = new ObjectSet<>();

    public Recipe(String displayName, float craftTime) {
        this.displayName = displayName;
        this.craftTime = craftTime;
        Vars.recipes.add(this);
    }

    public Recipe input(Item item, int amount) {
        inputs.with(item, amount);
        return this;
    }

    public Recipe output(Item item, int amount) {
        outputs.with(item, amount);
        return this;
    }

    public Recipe forType(ProductionType... types) {
        for (ProductionType type : types) {
            productionTypes.add(type);
        }

        return this;
    }

    public boolean canCraftIn(ProductionType type) {
        return productionTypes.contains(type) || productionTypes.contains(ProductionType.GENERAL);
    }

    public boolean isValid() {
        if (displayName == null || displayName.isBlank() || craftTime <= 0f) {
            return false;
        }

        if (inputs.getItems().isEmpty() || outputs.getItems().isEmpty()
                || productionTypes.isEmpty()) {
            return false;
        }

        for (ItemStack stack : inputs.getItems()) {
            if (stack.item == null || stack.amount <= 0) {
                return false;
            }
        }

        for (ItemStack stack : outputs.getItems()) {
            if (stack.item == null || stack.amount <= 0) {
                return false;
            }
        }
        return true;
    }
}