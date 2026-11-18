package foundustry.ui.uiItems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.graphics.Fonts;
import foundustry.ui.UIItem;

public class Label extends UIItem {

    private final String text;
    private final Fonts.Style style;
    private final boolean centered;

    private final SpriteBatch batch;
    private final GlyphLayout layout = new GlyphLayout();

    private Color color = Color.WHITE;

    public Label(float x, float y, String text) {
        this(x, y, text, Fonts.Style.UI, false);
    }

    public Label(float x, float y, String text, Fonts.Style style) {
        this(x, y, text, style, false);
    }

    public Label(float x, float y, String text, Fonts.Style style, boolean centered) {
        super(x, y, 0, 0);

        this.text = text;
        this.style = style;
        this.centered = centered;
        this.batch = new SpriteBatch();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        BitmapFont font = Fonts.font(style);

        if (font == null) return;

        float drawX = x;

        if (centered) {
            layout.setText(font, text);
            drawX = x - layout.width / 2f;
        }

        batch.begin();

        try {
            font.setColor(color);
            font.draw(batch, text, drawX, y);
        } finally {
            font.setColor(Color.WHITE);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
