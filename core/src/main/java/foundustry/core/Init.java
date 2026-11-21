
package foundustry.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;
import foundustry.graphics.particle.ParticleSystem;

public class Init {
    public static OrthographicCamera camera;
    public static DrawBlock drawBlock;
    public static DrawUnit drawUnit;
    public static ParticleSystem particleSystem;

    public static void init() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        drawBlock = new DrawBlock();
        drawUnit = new DrawUnit();
        particleSystem = new ParticleSystem();
    }
}
