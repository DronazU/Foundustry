package foundustry.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foundustry.core.EventType;
import foundustry.core.Events;
import foundustry.core.game.GameState;
import foundustry.graphics.Fonts;
import foundustry.ui.UI;
import foundustry.ui.uiItems.Button;
import foundustry.ui.uiItems.Label;

public class MainMenu extends UI {

    private static final String TITLE_TEXT = "Foundustry";
    private static final String SUBTITLE_TEXT = "build · automate · expand";
    private static final String CONTROLS_TEXT =
            "WASD — move    R — rotate    RMB — build    E — inventory";
    private static final String NEW_WORLD_HINT = "Press N to generate a new world";
    private static final String VERSION_TEXT = "v1.0-SNAPSHOT";

    private static final int BUTTON_COUNT = 3;

    private static final float BUTTON_WIDTH = 280f;
    private static final float BUTTON_HEIGHT = 56f;
    private static final float BUTTON_GAP = 14f;

    private static final float PANEL_PADDING = 28f;
    private static final float PANEL_ACCENT_HEIGHT = 3f;
    private static final float PANEL_VERTICAL_OFFSET = 40f;

    private static final float TITLE_GAP = 56f;
    private static final float SUBTITLE_GAP = 26f;

    private static final float FOOTER_MARGIN = 28f;
    private static final float FOOTER_LINE_GAP = 22f;

    private static final Color BACKGROUND_COLOR = new Color(0.05f, 0.06f, 0.085f, 1f);
    private static final Color PANEL_COLOR = new Color(0.11f, 0.12f, 0.15f, 0.94f);
    private static final Color ACCENT_COLOR = new Color(0.24f, 0.55f, 0.95f, 1f);
    private static final Color HOVER_COLOR = new Color(0.20f, 0.45f, 0.80f, 1f);
    private static final Color MUTED_COLOR = new Color(0.55f, 0.60f, 0.68f, 1f);

    private final ShapeRenderer shape = new ShapeRenderer();

    private final GameState state;

    private final float panelX;
    private final float panelY;
    private final float panelWidth;
    private final float panelHeight;

    public MainMenu(GameState state) {
        this.state = state;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        panelWidth = BUTTON_WIDTH + PANEL_PADDING * 2f;
        panelHeight = BUTTON_HEIGHT * BUTTON_COUNT
                + BUTTON_GAP * (BUTTON_COUNT - 1)
                + PANEL_PADDING * 2f;

        panelX = (screenWidth - panelWidth) / 2f;
        panelY = (screenHeight - panelHeight) / 2f - PANEL_VERTICAL_OFFSET;

        float centerX = screenWidth / 2f;
        float buttonX = panelX + PANEL_PADDING;
        float firstButtonY = panelY + panelHeight - PANEL_PADDING - BUTTON_HEIGHT;

        Label title = new Label(
                centerX,
                panelY + panelHeight + TITLE_GAP,
                TITLE_TEXT,
                Fonts.Style.TITLE,
                true
        );

        Label subtitle = new Label(
                centerX,
                panelY + panelHeight + SUBTITLE_GAP,
                SUBTITLE_TEXT,
                Fonts.Style.SMALL,
                true
        );
        subtitle.setColor(ACCENT_COLOR);

        Button play = createButton(buttonX, firstButtonY, "PLAY");
        Button settings = createButton(
                buttonX,
                firstButtonY - (BUTTON_HEIGHT + BUTTON_GAP),
                "SETTINGS"
        );
        Button exit = createButton(
                buttonX,
                firstButtonY - (BUTTON_HEIGHT + BUTTON_GAP) * 2f,
                "EXIT"
        );

        Label controls = new Label(
                centerX,
                FOOTER_MARGIN + FOOTER_LINE_GAP,
                CONTROLS_TEXT,
                Fonts.Style.SMALL,
                true
        );
        controls.setColor(MUTED_COLOR);

        Label newWorldHint = new Label(
                centerX,
                FOOTER_MARGIN,
                NEW_WORLD_HINT,
                Fonts.Style.SMALL,
                true
        );
        newWorldHint.setColor(MUTED_COLOR);

        Label version = new Label(
                FOOTER_MARGIN,
                FOOTER_MARGIN,
                VERSION_TEXT,
                Fonts.Style.SMALL
        );
        version.setColor(MUTED_COLOR);

        play.onClick(button -> state.setState(GameState.State.GAME));

        exit.onClick(button -> {
            Events.go(new EventType.GameExitEvent());
            Gdx.app.exit();
        });

        items.add(title);
        items.add(subtitle);
        items.add(play);
        items.add(settings);
        items.add(exit);
        items.add(controls);
        items.add(newWorldHint);
        items.add(version);
    }

    private Button createButton(float x, float y, String text) {
        Button button = new Button(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, text);

        button.setTextCentered(true);
        button.onHover(b -> b.setFillColor(HOVER_COLOR));

        return button;
    }

    @Override
    public void update() {
        super.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            state.requestNewWorld();
        }
    }

    @Override
    public void render() {
        drawBackground();
        super.render();
    }

    private void drawBackground() {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(BACKGROUND_COLOR);
        shape.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shape.setColor(PANEL_COLOR);
        shape.rect(panelX, panelY, panelWidth, panelHeight);

        shape.setColor(ACCENT_COLOR);
        shape.rect(
                panelX,
                panelY + panelHeight - PANEL_ACCENT_HEIGHT,
                panelWidth,
                PANEL_ACCENT_HEIGHT
        );

        shape.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        shape.dispose();
    }
}
