package foundustry.ui.uiItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foundustry.graphics.Fonts;
import foundustry.ui.UIItem;
import foundustry.ui.UiLayout;

import java.util.function.Consumer;

public class Button extends UIItem {

    private static final Color DEFAULT_COLOR = Color.GRAY;

    private static final float ICON_MAX_SIZE = 40f;
    private static final float TEXT_INSET_X = 20f;

    private static final GlyphLayout layout = new GlyphLayout();

    private final String text;
    private final TextureRegion icon;

    private final ShapeRenderer shape;
    private final SpriteBatch batch;

    private Color color = DEFAULT_COLOR;
    private boolean textCentered;

    private Consumer<Button> hoverListener;
    private Consumer<Button> clickListener;

    public Button(float x, float y, float width, float height, String text) {
        this(x, y, width, height, text, null);
    }

    public Button(float x, float y, float width, float height, TextureRegion icon) {
        this(x, y, width, height, null, icon);
    }

    private Button(float x, float y, float width, float height,
                   String text, TextureRegion icon) {
        super(x, y, width, height);

        this.text = text;
        this.icon = icon;

        shape = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void update() {
        boolean hovered = isHovered();

        if (hovered && hoverListener != null) {
            hoverListener.accept(this);
        } else {
            setDefaultButtonState();
        }

        if (hovered && Gdx.input.justTouched() && clickListener != null) {
            clickListener.accept(this);
        }
    }

    @Override
    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.rect(x, y, width, height);
        shape.end();

        batch.begin();

        if (icon != null) {
            drawIcon();
        } else if (text != null) {
            drawText();
        }

        batch.end();
    }

    private void drawText() {
        float textX = x + TEXT_INSET_X;

        if (textCentered) {
            layout.setText(Fonts.ui, text);
            textX = x + (width - layout.width) / 2f;
        }

        Fonts.ui.draw(batch, text, textX, y + height / 2f);
    }

    private void drawIcon() {
        float scale = Math.min(
                ICON_MAX_SIZE / icon.getRegionWidth(),
                ICON_MAX_SIZE / icon.getRegionHeight()
        );

        float iconWidth = icon.getRegionWidth() * scale;
        float iconHeight = icon.getRegionHeight() * scale;

        batch.draw(
                icon,
                x + (width - iconWidth) / 2f,
                y + (height - iconHeight) / 2f,
                iconWidth,
                iconHeight
        );
    }

    public boolean isHovered() {
        return UiLayout.mouseOver(x, y, width, height);
    }

    public void onHover(Consumer<Button> listener) {
        this.hoverListener = listener;
    }

    public void onClick(Consumer<Button> listener) {
        this.clickListener = listener;
    }

    public void setFillColor(Color color) {
        this.color = color;
    }

    public void setTextCentered(boolean textCentered) {
        this.textCentered = textCentered;
    }

    public void setDefaultButtonState() {
        setFillColor(DEFAULT_COLOR);
    }

    @Override
    public void dispose() {
        shape.dispose();
        batch.dispose();
    }
}
