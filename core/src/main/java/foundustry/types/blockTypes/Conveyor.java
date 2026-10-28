
package foundustry.types.blockTypes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import foundustry.graphics.Atlas;

public class Conveyor extends LogisticBlock {

    private Animation<TextureRegion> animation;
    private float animationTime;

    public Conveyor(String name) {
        super(name);
        canRotate = true;
    }

    @Override
    public void load() {
        super.load();

        Array<TextureAtlas.AtlasRegion> frames = Atlas.find(name, true);

        if (frames.size > 1) {
            frames.sort((a, b) -> Integer.compare(a.index, b.index));

            animation = new Animation<>(
                    0.1f,
                    frames,
                    Animation.PlayMode.LOOP
            );

            region = animation.getKeyFrame(0f);
        }
    }

    @Override
    public void update(float delta) {
        if (animation != null) {
            animationTime += delta;
        }
    }

    @Override
    public TextureRegion getRegion() {
        if (animation == null) {
            return region;
        }

        return animation.getKeyFrame(animationTime);
    }
}
