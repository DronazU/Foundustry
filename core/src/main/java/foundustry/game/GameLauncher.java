package foundustry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.graphics.Atlas;

public class GameLauncher extends ApplicationAdapter {

    @Override
    public void create() {
        Atlas.loadAtlas();

        Events.go(new EventType.GameLaunchEvent(System.currentTimeMillis()));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    public void dispose() {
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Foundustry");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new GameLauncher(), config);
    }
}
