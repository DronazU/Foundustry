package foundustry.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {

    public static BitmapFont ui;

    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Roboto.ttf")
        );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 18;
        parameter.characters =
                FreeTypeFontGenerator.DEFAULT_CHARS
                        + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                        + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

        ui = generator.generateFont(parameter);
        generator.dispose();
    }

    public static void dispose() {
        if (ui != null) {
            ui.dispose();
            ui = null;
        }
    }
}
