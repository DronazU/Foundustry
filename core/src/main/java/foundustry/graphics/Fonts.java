package foundustry.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {

    public enum Style {
        UI,
        TITLE,
        SMALL
    }

    private static final int UI_SIZE = 18;
    private static final int TITLE_SIZE = 44;
    private static final int SMALL_SIZE = 14;

    private static final String CHARACTERS =
            FreeTypeFontGenerator.DEFAULT_CHARS
                    + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                    + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                    + "—·";

    public static BitmapFont ui;
    public static BitmapFont title;
    public static BitmapFont small;

    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Roboto.ttf")
        );

        ui = generate(generator, UI_SIZE);
        title = generate(generator, TITLE_SIZE);
        small = generate(generator, SMALL_SIZE);

        generator.dispose();
    }

    /**
     * Resolved on demand, so labels created before {@link #load()} still work.
     */
    public static BitmapFont font(Style style) {
        return switch (style) {
            case TITLE -> title;
            case SMALL -> small;
            default -> ui;
        };
    }

    private static BitmapFont generate(FreeTypeFontGenerator generator, int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        parameter.characters = CHARACTERS;

        return generator.generateFont(parameter);
    }

    public static void dispose() {
        ui = dispose(ui);
        title = dispose(title);
        small = dispose(small);
    }

    private static BitmapFont dispose(BitmapFont font) {
        if (font != null) {
            font.dispose();
        }

        return null;
    }
}
