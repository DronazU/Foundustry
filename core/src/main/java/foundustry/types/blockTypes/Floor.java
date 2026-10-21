package foundustry.types.blockTypes;

import foundustry.types.Category;
import foundustry.world.content.Block;

public class Floor extends Block {

    public Floor(String name) {
        super(name);
        category = Category.PRODUCTION;
    }
}
