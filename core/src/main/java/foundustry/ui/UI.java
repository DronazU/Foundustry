package foundustry.ui;

import com.badlogic.gdx.utils.Array;

public abstract class UI {

    protected final Array<UIItem> items = new Array<>();

    public void update() {
        for (UIItem item : items) {
            item.update();
        }
    }

    public void render() {
        for (UIItem item : items) {
            item.render();
        }
    }

    public void dispose() {

    }
}