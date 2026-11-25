package foundustry.world.contentType.block.production;

import com.badlogic.gdx.utils.Array;
import foundustry.types.Block;
import foundustry.types.Category;
import foundustry.types.recipe.ProductionType;
import foundustry.types.recipe.Recipe;
import foundustry.world.contentRegister.Recipes;

public class GenericCrafter extends Block {
    public final ProductionType productionType;

    public GenericCrafter(String name, ProductionType productionType) {
        super(name);

        if (productionType == null) {
            throw new IllegalArgumentException(
                    "Production type cannot be null: " + name
            );
        }

        this.productionType = productionType;
        category = Category.PRODUCTION;
    }

    public Array<Recipe> getAvailableRecipes() {
        return Recipes.getByType(productionType);
    }
}