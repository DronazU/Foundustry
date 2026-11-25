package foundustry.world.contentType.block.production;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;
import foundustry.types.Block;
import foundustry.types.Category;
import foundustry.types.recipe.ProductionType;
import foundustry.types.recipe.Recipe;
import foundustry.world.contentRegister.Recipes;

public class GenericCrafter extends Block {
    public final ProductionType productionType;

    public final Array<TextureAtlas.AtlasRegion> workingRegions = new Array<>();

    public float frameDuration = 0.15f;

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

    @Override
    public void load() {
        super.load();

        workingRegions.clear();
        workingRegions.addAll(Atlas.findRegions(name + "_animation"));

        workingRegions.sort((first, second) ->
                Integer.compare(first.index, second.index));
    }

    public TextureRegion getWorkingRegion(float time) {
        if (workingRegions.size == 0 || frameDuration <= 0f) {
            return getRegion();
        }

        int frame = (int)(time / frameDuration) % workingRegions.size;

        return workingRegions.get(frame);
    }

    public Array<Recipe> getAvailableRecipes() {
        return Recipes.getByType(productionType);
    }
}