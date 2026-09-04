package foundustry.game;

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
}