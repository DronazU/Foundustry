package foundustry.ui;

import com.badlogic.gdx.utils.Array;

public abstract class UI {

    protected final Array<UIItem> items = new Array<>();
    protected final Array<UI> childrens = new Array<>();

    public void update() {
        for (UIItem item : items) {
            item.update();
        }

        for (UI ui : childrens) {
            ui.update();
        }
    }

    public void render() {
        for (UIItem item : items) {
            item.render();
        }

        for (UI ui : childrens) {
            ui.render();
        }
    }

    public void dispose() {
        for (UI ui : childrens) {
            ui.dispose();
        }
    }
}