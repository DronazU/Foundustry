package foundustry.world.contentRegister;

import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.types.recipe.ProductionType;
import foundustry.types.recipe.Recipe;

public class Recipes {
    public static Recipe
    /** furnace */
    copperIngot, ironIngot
            ;

    public static void load() {
        copperIngot = new Recipe("Медный слиток", 2.5f)
                .input(Items.unrefinedCopper, 2)
                .output(Items.copperIngot, 1)
                .forType(ProductionType.FURNACE);

        ironIngot = new Recipe("Железный слиток", 2.5f)
                .input(Items.unrefinedIron, 2)
                .output(Items.ironIngot, 1)
                .forType(ProductionType.FURNACE);
    }

    public static Array<Recipe> getByType(ProductionType type) {
        Array<Recipe> result = new Array<>();

        for (Recipe recipe : Vars.recipes) {
            if (recipe.canCraftIn(type) && recipe.isValid()) {
                result.add(recipe);
            }
        }

        return result;
    }
}
