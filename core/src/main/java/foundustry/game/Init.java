package foundustry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import foundustry.graphics.drawers.DrawBlock;
import foundustry.graphics.drawers.DrawUnit;

public class Init {
    public static OrthographicCamera camera;
    public static DrawBlock drawBlock;
    public static DrawUnit drawUnit;

    public static void init() {
        drawBlock = new DrawBlock();
        drawUnit = new DrawUnit();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
