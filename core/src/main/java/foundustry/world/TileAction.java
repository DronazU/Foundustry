package foundustry.world;

/**
 * Action performed on every non-null tile of a chunk or a world.
 */
@FunctionalInterface
public interface TileAction {
    void accept(Tile tile);
}
