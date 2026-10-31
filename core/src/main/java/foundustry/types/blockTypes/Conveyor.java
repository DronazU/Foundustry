
package foundustry.types.blockTypes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;
import foundustry.world.Tile;

public class Conveyor extends LogisticBlock {

    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> cornerAnimation;

    private final Array<TextureRegion> flippedCornerFrames = new Array<>();

    private float animationTime;

    public float speed;

    public Conveyor(String name) {
        super(name);
        canRotate = true;
    }

    @Override
    public void load() {
        super.load();

        if (speed <= 0f) {
            throw new IllegalStateException(
                    "Conveyor speed must be greater than zero: " + name
            );
        }

        // Прямая анимация
        Array<TextureAtlas.AtlasRegion> frames =
                Atlas.find(name, true);

        frames.sort((a, b) -> Integer.compare(a.index, b.index));

        if (frames.size > 0) {
            animation = new Animation<>(
                    1f / (frames.size * speed),
                    frames,
                    Animation.PlayMode.LOOP
            );

            region = animation.getKeyFrame(0f);
        }

        Array<TextureAtlas.AtlasRegion> cornerFrames =
                Atlas.find(name + "-corner", true);

        cornerFrames.sort((a, b) -> Integer.compare(a.index, b.index));

        if (cornerFrames.size > 0) {
            cornerAnimation = new Animation<>(
                    1f / (cornerFrames.size * speed),
                    cornerFrames,
                    Animation.PlayMode.LOOP
            );

            for (TextureAtlas.AtlasRegion frame : cornerFrames) {
                TextureRegion flipped = new TextureRegion(frame);
                flipped.flip(true, false);

                flippedCornerFrames.add(flipped);
            }
        }
    }

    @Override
    public void update(float delta) {
        animationTime += delta;
    }

    @Override
    public TextureRegion getRegion() {
        if (animation == null) {
            return region;
        }

        return animation.getKeyFrame(animationTime);
    }

    public TextureRegion getCornerRegion(boolean flipX) {
        if (cornerAnimation == null) {
            return null;
        }

        if (!flipX) {
            return cornerAnimation.getKeyFrame(animationTime);
        }

        int frameIndex = cornerAnimation.getKeyFrameIndex(animationTime);

        return flippedCornerFrames.get(frameIndex);
    }

    public float getSpeed() {
        return speed;
    }

    public CornerOrientation getCornerOrientation(
            Tile[][] map,
            int tileX,
            int tileY,
            int rotation
    ) {
        if (map == null || cornerAnimation == null) {
            return null;
        }

        int[] output = getDirection(rotation);

        int clockwiseX = output[1];
        int clockwiseY = -output[0];

        int counterClockwiseX = -output[1];
        int counterClockwiseY = output[0];

        int[][] offsets = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        for (int[] offset : offsets) {
            int neighborX = tileX + offset[0];
            int neighborY = tileY + offset[1];

            if (neighborX < 0 || neighborX >= map.length) continue;
            if (neighborY < 0 || neighborY >= map[neighborX].length) continue;

            Tile neighbor = map[neighborX][neighborY];

            if (neighbor == null || !(neighbor.block instanceof Conveyor)) {
                continue;
            }

            int[] neighborDirection = getDirection(neighbor.rotation);

            if (neighborDirection[0] != -offset[0]
                    || neighborDirection[1] != -offset[1]) {
                continue;
            }

            if (neighborDirection[0] == clockwiseX
                    && neighborDirection[1] == clockwiseY) {
                return new CornerOrientation(false);
            }

            if (neighborDirection[0] == counterClockwiseX
                    && neighborDirection[1] == counterClockwiseY) {
                return new CornerOrientation(true);
            }
        }

        return null;
    }

    private int[] getDirection(int rotation) {
        return switch (Math.floorMod(rotation, 360)) {
            case 0 -> new int[]{0, 1};
            case 90 -> new int[]{-1, 0};
            case 180 -> new int[]{0, -1};
            case 270 -> new int[]{1, 0};
            default -> new int[]{0, 1};
        };
    }

    public static class CornerOrientation {
        public final boolean flipX;

        public CornerOrientation(boolean flipX) {
            this.flipX = flipX;
        }
    }
}
