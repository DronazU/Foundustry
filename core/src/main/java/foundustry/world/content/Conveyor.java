
package foundustry.world.content;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;

public class Conveyor extends LogisticBlock {

    private Animation<TextureRegion> animation;
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

        Array<TextureAtlas.AtlasRegion> frames =
                Atlas.find(name, true);

        frames.sort((a, b) -> Integer.compare(a.index, b.index));

        if (frames.size > 0) {
            animation = new Animation<>(
                    0.5f / (frames.size * speed),
                    frames,
                    Animation.PlayMode.LOOP
            );

            region = animation.getKeyFrame(0f);
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

    public float getSpeed() {
        return speed;
    }
}