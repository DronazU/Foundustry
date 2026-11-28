package foundustry.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import foundustry.types.item.Item;

/**
 * Месторождение руды в одной клетке мира.
 * Хранится отдельно от пола ({@link Tile#floor}) и построек ({@link Tile#build}):
 * спрайт руды рисуется поверх пола, но не заменяет его.
 */
public class OreDeposit {
    /** Предмет, который даёт этот тип руды (unrefined-iron / unrefined-copper). */
    public final Item item;

    /** Спрайт месторождения (одна из вариаций ore-iron_1..3 / ore-copper_1..3). */
    public final TextureRegion region;

    /** Оставшийся запас руды в клетке. Уменьшается будущим буром. */
    public int amount;

    public OreDeposit(Item item, TextureRegion region, int amount) {
        this.item = item;
        this.region = region;
        this.amount = amount;
    }

    /**
     * Признак истощённой клетки. Запас не восстанавливается со временем.
     */
    public boolean isDepleted() {
        return amount <= 0;
    }
}
