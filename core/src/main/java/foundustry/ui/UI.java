package foundustry.ui;

import com.badlogic.gdx.utils.Array;

public abstract class UI {
    private boolean visible = true;

    protected final Array<UIItem> items = new Array<>();
    protected final Array<UI> childrens = new Array<>();

    public void update() {
        if (!visible) return;
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        for (UIItem item : items) {
            item.dispose();
        }

        for (UI ui : childrens) {
            ui.dispose();
        }
    }
}