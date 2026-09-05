package foundustry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.ScreenUtils;
import foundustry.graphics.Atlas;
import foundustry.log.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLauncher extends ApplicationAdapter {
    static Log Log = new Log();

    private Init init;

    @Override
    public void create() {
        Init.init();
        Atlas.load();

        Events.go(new EventType.GameLaunchEvent(System.currentTimeMillis()));
    }

    @Override
    public void render() {
        ScreenUtils.clear(42, 62, 94, 1);
        Init.drawBlock.render();

        Events.go(new EventType.GameUpdateEvent());
    }

    public void dispose() {
        Init.drawBlock.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Foundustry");
        config.setWindowedMode(1200, 900);

        Events.on(EventType.GameLaunchEvent.class, event -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Log.info("Start time: " + sdf.format(new Date(event.startTime)));
        });

        new Lwjgl3Application(new GameLauncher(), config);
    }
}
