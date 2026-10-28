package foundustry.core.game;

import com.badlogic.gdx.Gdx;

public class Time {

    public static float delta = 0f;

    public static void update() {
        delta = Gdx.graphics.getDeltaTime();
    }
}