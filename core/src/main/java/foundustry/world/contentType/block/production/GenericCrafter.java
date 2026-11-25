package foundustry.world.contentType.block.production;

import foundustry.types.Block;
import foundustry.types.recipe.ProductionType;

public class GenericCrafter extends Block {
    public final ProductionType productionType;

    public GenericCrafter(String name, ProductionType productionType) {
        super(name);
        this.productionType = productionType;
    }
}