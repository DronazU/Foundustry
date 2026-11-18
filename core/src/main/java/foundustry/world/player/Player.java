package foundustry.world.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import foundustry.core.Vars;
import foundustry.core.game.Time;
import foundustry.types.Block;
import foundustry.types.Item;
import foundustry.types.UnitType;
import foundustry.ui.game.GameUI;
import foundustry.world.Tile;
import foundustry.world.World;
import foundustry.world.content.Blocks;
import foundustry.world.content.UnitTypes;

import static foundustry.core.Init.camera;

public class Player {
    private static final float ZOOM_SPEED = 1f;
    private static final float MIN_ZOOM = 0.5f;
    private static final float MAX_ZOOM = 3f;
    private static final float TICKS_PER_SECOND = 60f;
    private boolean blockFlipX = false;

    public final Inventory inventory = new Inventory();

    public UnitType unit = UnitTypes.constructionDrone;

    private GameUI gameUI;
    private World world;
    private int blockRotation = 0;

    private final Vector3 mouseTile = new Vector3();

    public void handleInput() {
        handleZoom();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            unit.flying = true;
        }

        float moveX = movementAxis(Input.Keys.A, Input.Keys.D);
        float moveY = movementAxis(Input.Keys.S, Input.Keys.W);

        float length = (float)Math.sqrt(moveX * moveX + moveY * moveY);

        if (length != 0f) {
            moveX /= length;
            moveY /= length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && canRotateSelectedBlock()) {
            blockRotation = (blockRotation + 90) % 360;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && canFlipSelectedBlock()) {
            blockFlipX = !blockFlipX;
        }

        applyVelocity(moveX, moveY);
        applyRotation(moveX, moveY);

        camera.position.set(unit.x, unit.y, 0);
    }

    public void update() {
        handleInput();
        build();
    }

    public void build() {
        if (world == null || gameUI == null || gameUI.isInventoryOpen()) {
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            return;
        }

        Item item = gameUI.getSelectedItem();

        if (item == null || item.placeableBlock == null
                || item.placeableBlock == Blocks.nothing) {
            return;
        }

        if (inventory.get(item) <= 0) {
            return;
        }

        Vector3 tilePosition = getMouseTile();

        int tileX = (int)tilePosition.x;
        int tileY = (int)tilePosition.y;

        Tile tile = world.getTile(tileX, tileY);

        if (tile == null || tile.block != null) {
            return;
        }

        Block selectedBlock = item.placeableBlock;

        boolean placed = world.setBlock(
                tileX,
                tileY,
                selectedBlock,
                selectedBlock.canRotate ? blockRotation : 0,
                selectedBlock.canFlip && blockFlipX
        );

        if (!placed) {
            return;
        }

        inventory.remove(item, 1);
    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private void handleZoom() {
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            camera.zoom += ZOOM_SPEED * Time.delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            camera.zoom -= ZOOM_SPEED * Time.delta;
        }

        camera.zoom = MathUtils.clamp(camera.zoom, MIN_ZOOM, MAX_ZOOM);
    }

    private void applyVelocity(float moveX, float moveY) {
        float targetX = moveX * unit.speed;
        float targetY = moveY * unit.speed;

        unit.velocityX += (targetX - unit.velocityX)
                * unit.accel * TICKS_PER_SECOND * Time.delta;
        unit.velocityY += (targetY - unit.velocityY)
                * unit.accel * TICKS_PER_SECOND * Time.delta;

        if (moveX == 0f) {
            unit.velocityX *= 1f - unit.drag * TICKS_PER_SECOND * Time.delta;
        }

        if (moveY == 0f) {
            unit.velocityY *= 1f - unit.drag * TICKS_PER_SECOND * Time.delta;
        }

        unit.x += unit.velocityX * Time.delta;
        unit.y += unit.velocityY * Time.delta;
    }

    private void applyRotation(float moveX, float moveY) {
        if (moveX == 0f && moveY == 0f) {
            return;
        }

        boolean a = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean d = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean w = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean s = Gdx.input.isKeyPressed(Input.Keys.S);

        float targetAngle = MathUtils.atan2(
                (a ? 1 : 0) - (d ? 1 : 0),
                (w ? 1 : 0) - (s ? 1 : 0)
        ) * MathUtils.radiansToDegrees;

        unit.rotation = MathUtils.lerpAngleDeg(
                unit.rotation,
                targetAngle,
                Math.min(1f, unit.rotateSpeed * Time.delta)
        );
    }

    private Vector3 getMouseTile() {
        mouseTile.set(Gdx.input.getX(), Gdx.input.getY(), 0f);

        camera.unproject(mouseTile);

        mouseTile.x = MathUtils.floor(mouseTile.x / Vars.tileSize);
        mouseTile.y = MathUtils.floor(mouseTile.y / Vars.tileSize);

        return mouseTile;
    }

    private boolean canRotateSelectedBlock() {
        if (gameUI == null) {
            return false;
        }

        Item item = gameUI.getSelectedItem();

        return item != null
                && item.placeableBlock != null
                && item.placeableBlock != Blocks.nothing
                && item.placeableBlock.canRotate;
    }

    private static float movementAxis(int negativeKey, int positiveKey) {
        float axis = 0f;

        if (Gdx.input.isKeyPressed(negativeKey)) axis -= 1f;
        if (Gdx.input.isKeyPressed(positiveKey)) axis += 1f;

        return axis;
    }

    private boolean canFlipSelectedBlock() {
        if (gameUI == null) {
            return false;
        }

        Item item = gameUI.getSelectedItem();

        return item != null
                && item.placeableBlock != null
                && item.placeableBlock.canFlip;
    }

    public Block getSelectedBlock() {
        if (gameUI == null || gameUI.isInventoryOpen()) {
            return null;
        }

        Item item = gameUI.getSelectedItem();

        if (item == null || item.placeableBlock == null
                || item.placeableBlock == Blocks.nothing) {
            return null;
        }

        return item.placeableBlock;
    }

    public int getBlockRotation() {
        return blockRotation;
    }

    public int getMouseTileX() {
        return (int)getMouseTile().x;
    }

    public int getMouseTileY() {
        return (int)getMouseTile().y;
    }
}
