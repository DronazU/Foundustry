package foundustry.game;

import com.badlogic.gdx.Gdx;

public class EventType {
    public static class GameLaunchEvent {
        public final long startTime;

        public GameLaunchEvent(long startTime) {
            this.startTime = startTime;
        }
    }

    public static class GameUpdateEvent {
        public GameUpdateEvent() {}
    }

    public static class GameExitEvent {
        public GameExitEvent() {}
    }

    public static class KeyPressEvent {
        public final int keycode;

        public KeyPressEvent(int keycode) {
            this.keycode = keycode;
        }
    }
}