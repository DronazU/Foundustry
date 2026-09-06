package foundustry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import foundustry.graphics.drawers.DrawBlock;

public class Init {
    public static DrawBlock drawBlock;
    public static OrthographicCamera camera;

    public static void init() {
        drawBlock = new DrawBlock();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
