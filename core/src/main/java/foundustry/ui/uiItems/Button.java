package foundustry.ui.uiItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.graphics.Fonts;
import foundustry.ui.UIItem;

import java.util.function.Consumer;

public class Button extends UIItem {

    private final String text;
    private Color defaultColor = Color.GRAY;

    private final ShapeRenderer shape;
    private final SpriteBatch batch;
    private final TextureRegion icon;

    private Color color = Color.GRAY;

    private Consumer<Button> hoverListener;
    private Consumer<Button> clickListener;

    public Button(float x, float y, float width, float height, String text) {
        super(x, y, width, height);

        this.text = text;
        this.icon = null;

        shape = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    public Button(float x, float y, float width, float height, TextureRegion icon) {
        super(x, y, width, height);

        this.text = null;
        this.icon = icon;

        shape = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void update() {
        if (isHovered() && hoverListener != null) hoverListener.accept(this);
        else setDefaultButtonState();
        if (isHovered() && Gdx.input.justTouched() && clickListener != null) clickListener.accept(this);
    }

    @Override
    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.rect(x, y, width, height);
        shape.end();

        batch.begin();

        if (icon != null) {
            float maxSize = 40f;
            float scale = Math.min(
                    maxSize / icon.getRegionWidth(),
                    maxSize / icon.getRegionHeight()
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
        } else if (text != null) {
            Fonts.ui.draw(batch, text, x + 20, y + height / 2f);
        }

        batch.end();
    }

    public boolean isHovered() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return mouseX >= x &&
                mouseX <= x + width &&
                mouseY >= y &&
                mouseY <= y + height;
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

    public void setDefaultButtonState() {
        setFillColor(defaultColor);
    }

    @Override
    public void dispose() {
        shape.dispose();
        batch.dispose();
    }
}