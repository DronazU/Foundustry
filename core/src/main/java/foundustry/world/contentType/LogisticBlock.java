package foundustry.world.contentType;

import foundustry.types.Block;
import foundustry.types.Category;

public class LogisticBlock extends Block {

    public LogisticBlock(String name) {
        super(name);
        category = Category.LOGISTICS;
    }
}
