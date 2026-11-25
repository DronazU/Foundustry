
package foundustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.types.Block;
import foundustry.types.item.Item;
import foundustry.world.contentType.block.logistics.Conveyor;
import foundustry.world.contentType.block.logistics.ConveyorItem;

public class Tile {
    public static final int MAX_CONVEYOR_ITEMS = 4;
    private static final float ITEM_GAP = 0.25f;

    private static final Color previousColor = new Color();

    public float x, y;

    public Block floor;
    public Building build;

    public final Array<ConveyorItem> conveyorItems = new Array<>();

    public Tile(float x, float y, Block floor) {
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    public boolean isBuildingAnchor() {
        return build != null
                && MathUtils.floor(x / Vars.tileSize) == build.tileX
                && MathUtils.floor(y / Vars.tileSize) == build.tileY;
    }

    public boolean addConveyorItem(Item item) {
        if (build == null || !(build.block instanceof Conveyor) || item == null) {
            return false;
        }

        if (conveyorItems.size >= MAX_CONVEYOR_ITEMS) {
            return false;
        }

        for (ConveyorItem conveyorItem : conveyorItems) {
            if (conveyorItem.position < ITEM_GAP) {
                return false;
            }
        }

        conveyorItems.add(new ConveyorItem(item, 0f));
        return true;
    }

    public void updateConveyor(World world, float delta) {
        if (!isBuildingAnchor() || !(build.block instanceof Conveyor conveyor)) {
            return;
        }

        conveyorItems.sort((a, b) ->
                Float.compare(b.position, a.position));

        int directionX = MathUtils.round(MathUtils.cosDeg(build.rotation));
        int directionY = MathUtils.round(MathUtils.sinDeg(build.rotation));

        int tileX = MathUtils.floor(x / Vars.tileSize);
        int tileY = MathUtils.floor(y / Vars.tileSize);

        Tile nextTile = world.getTile(
                tileX + directionX,
                tileY + directionY
        );

        for (int i = 0; i < conveyorItems.size;) {
            ConveyorItem item = conveyorItems.get(i);

            float maxPosition = i == 0
                    ? 1f
                    : conveyorItems.get(i - 1).position - ITEM_GAP;

            item.position = Math.min(
                    item.position + conveyor.speed * delta,
                    maxPosition
            );

            if (item.position >= 1f && nextTile != null
                    && nextTile.addConveyorItem(item.item)) {
                conveyorItems.removeIndex(i);
                continue;
            }

            i++;
        }
    }

    public void drawFloor(SpriteBatch batch) {
        float size = Vars.tileSize;

        if (floor != null && floor.region != null) {
            batch.draw(floor.region, x, y, size, size);
        }
    }

    public void drawShadow(SpriteBatch batch) {
        if (!isBuildingAnchor() || !build.block.hasShadow) return;
        if (Vars.Shadow.squareShadow == null) return;

        float size = Vars.tileSize * build.getSize() * 1.25f;
        float offset = (size - Vars.tileSize * build.getSize()) / 2f;

        previousColor.set(batch.getColor());
        batch.setColor(0f, 0f, 0f, 1f);

        batch.draw(
                Vars.Shadow.squareShadow,
                x - offset,
                y - offset,
                size,
                size
        );

        batch.setColor(previousColor);
    }

    public void drawBlock(SpriteBatch batch) {
        if (!isBuildingAnchor()) return;

        float size = Vars.tileSize * build.getSize();

        DrawBlock.drawBlock(
                batch,
                build.getDrawRegion(),
                x,
                y,
                build.rotation,
                build.flipX,
                1f,
                size
        );
    }

    public void drawConveyorItems(SpriteBatch batch) {
        if (!isBuildingAnchor() || !(build.block instanceof Conveyor)) return;

        float centerX = x + Vars.tileSize / 2f;
        float centerY = y + Vars.tileSize / 2f;

        float directionX = MathUtils.cosDeg(build.rotation);
        float directionY = MathUtils.sinDeg(build.rotation);

        float itemSize = Vars.tileSize * 0.4f;

        for (ConveyorItem item : conveyorItems) {
            if (item.item.region == null) continue;

            float offset = (item.position - 0.5f) * Vars.tileSize;

            float itemX = centerX + directionX * offset;
            float itemY = centerY + directionY * offset;

            batch.draw(
                    item.item.region,
                    itemX - itemSize / 2f,
                    itemY - itemSize / 2f,
                    itemSize,
                    itemSize
            );
        }
    }

    public void updateBuilding(float delta) {
        if (!isBuildingAnchor()) {
            return;
        }

        build.update(delta);
    }
}