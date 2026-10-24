package foundustry.types.blockTypes;

import foundustry.types.Category;
import foundustry.world.content.Block;

public class LogisticBlock extends Block {

    public LogisticBlock(String name) {
        super(name);
        category = Category.LOGISTICS;
    }
}
