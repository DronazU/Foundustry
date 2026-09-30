package foundustry.ui;

public abstract class UIItem {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public UIItem(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();

    public abstract void render();
}