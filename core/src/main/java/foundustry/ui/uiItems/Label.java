package foundustry.ui.uiItems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foundustry.graphics.Fonts;
import foundustry.ui.UIItem;

public class Label extends UIItem {

    private final String text;
    private final SpriteBatch batch;

    public Label(float x, float y, String text) {
        super(x, y, 0, 0);

        this.text = text;
        this.batch = new SpriteBatch();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        batch.begin();
        Fonts.ui.draw(batch, text, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}