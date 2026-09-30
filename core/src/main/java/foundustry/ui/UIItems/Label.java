package foundustry.ui.UIItems;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.ui.UIItem;

public class Label extends UIItem {

    private final String text;
    private final BitmapFont font;
    private final SpriteBatch batch;

    public Label(float x, float y, String text) {
        super(x, y, 0, 0);

        this.text = text;

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        batch.begin();
        font.draw(batch, text, x, y);
        batch.end();
    }
}