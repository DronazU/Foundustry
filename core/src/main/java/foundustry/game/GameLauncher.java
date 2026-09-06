package foundustry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import foundustry.log.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLauncher extends ApplicationAdapter {

    private Game game;

    @Override
    public void create() {
        game = new Game();

        Events.go(new EventType.GameLaunchEvent(System.currentTimeMillis()));
    }

    @Override
    public void render() {
        game.update();
        game.render();

        Events.go(new EventType.GameUpdateEvent());
    }

    @Override
    public void dispose() {
        game.dispose();

        Events.go(new EventType.GameExitEvent());
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