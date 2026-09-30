package foundustry.core.game;

public class GameState {
    private static State state = State.MENU;
    public enum State {
        GAME,
        MENU,
        PAUSE
    }

    public State getState() {
        return state;
    }

    public void setState(State otherState) {
        state = otherState;
    }

    public boolean is(State otherState) {
        return state == otherState;
    }

    public boolean isPlaying(){
        return state == State.GAME;
    }

    public boolean isPaused(){
        return state == State.PAUSE;
    }

    public boolean isGame(){
        return state != State.MENU;
    }

    public boolean isMenu(){
        return state == State.MENU;
    }
}
