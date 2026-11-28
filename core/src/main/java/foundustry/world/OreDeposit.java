package foundustry.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.types.item.Item;

public class OreDeposit {
    public final Item item;
    public final TextureRegion region;

    public int amount;

    public OreDeposit(Item item, TextureRegion region, int amount) {
        this.item = item;
        this.region = region;
        this.amount = Math.max(0, amount);
    }

    public boolean isDepleted() {
        return amount <= 0;
    }

    public int extract(int requestedAmount) {
        if (requestedAmount <= 0 || isDepleted()) {
            return 0;
        }

        int extracted = Math.min(requestedAmount, amount);
        amount -= extracted;

        return extracted;
    }
}