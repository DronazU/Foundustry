package foundustry.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import foundustry.core.Vars;
import foundustry.graphics.Atlas;
import foundustry.types.Block;
import foundustry.types.item.Item;
import foundustry.world.contentRegister.Blocks;
import foundustry.world.contentRegister.Items;

import static foundustry.world.SimplexNoise.noise;

public class Generator {
    public float scale = 0.01f;
    public float seed;

    /** Масштаб шума залежей: чем меньше, тем крупнее залежи. */
    private static final float ORE_SCALE = 0.05f;
    /** Порог появления руды по модулю шума. Между -T и +T руды нет. */
    private static final float ORE_THRESHOLD = 0.45f;
    /** Минимальный запас руды в клетке залежи. */
    private static final int ORE_BASE_AMOUNT = 300;
    /** Доп. изменение запаса в зависимости от шума. */
    private static final int ORE_AMOUNT_VARIATION = 700;

    private TextureRegion[] ironVariants;
    private TextureRegion[] copperVariants;

    public Chunk generateChunk(int chunkX, int chunkY) {
        Chunk chunk = new Chunk(chunkX, chunkY);

        ensureVariantsLoaded();

        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                int worldX = chunkX * Chunk.SIZE + x;
                int worldY = chunkY * Chunk.SIZE + y;

                float value = noise(
                        worldX * scale + seed,
                        worldY * scale + seed
                );

                Block floor;

                if (value < -0.5f) {
                    floor = Blocks.deepWater;
                } else if (value < -0.3f) {
                    floor = Blocks.water;
                } else if (value < -0.1f) {
                    floor = Blocks.sandWater;
                } else if (value < 0.1f) {
                    floor = Blocks.sandFloor;
                } else if (value < 0.3f) {
                    floor = Blocks.dirt;
                } else if (value < 0.6f) {
                    floor = Blocks.grass;
                } else {
                    floor = Blocks.stone;
                }

                Tile tile = new Tile(
                        worldX * Vars.tileSize,
                        worldY * Vars.tileSize,
                        floor
                );

                tile.ore = generateOre(worldX, worldY, floor);
                chunk.tiles[x][y] = tile;
            }
        }
        return chunk;
    }

    /**
     * Генерирует месторождение для клетки.
     * Результат детерминированно зависит от координат клетки и seed мира,
     * поэтому соседние чанки генерируют одни и те же залежи на своих границах.
     * Руда не размещается на воде.
     */
    private OreDeposit generateOre(int worldX, int worldY, Block floor) {
        if (floor == Blocks.water
                || floor == Blocks.deepWater
                || floor == Blocks.sandWater
                || floor == Blocks.nothing) {
            return null;
        }

        float n = noise(
                worldX * ORE_SCALE + seed,
                worldY * ORE_SCALE + seed
        );

        if (Math.abs(n) <= ORE_THRESHOLD) {
            return null;
        }

        Item item = n > 0
                ? Items.unrefinedIron
                : Items.unrefinedCopper;

        float strength = (Math.abs(n) - ORE_THRESHOLD) / (1f - ORE_THRESHOLD);
        int amount = MathUtils.clamp(
                MathUtils.round(ORE_BASE_AMOUNT + strength * ORE_AMOUNT_VARIATION),
                1,
                ORE_BASE_AMOUNT + ORE_AMOUNT_VARIATION
        );

        TextureRegion[] variants = n > 0 ? ironVariants : copperVariants;
        TextureRegion region = null;

        if (variants != null && variants.length > 0) {
            int variant = Math.floorMod(
                    worldX * 1000003 + worldY * 98245163,
                    variants.length
            );
            region = variants[variant];
        }

        return new OreDeposit(item, region, amount);
    }

    private void ensureVariantsLoaded() {
        if (ironVariants != null) {
            return;
        }

        ironVariants = toSortedVariants(Atlas.findRegions("ore-iron"));
        copperVariants = toSortedVariants(Atlas.findRegions("ore-copper"));
    }

    private static TextureRegion[] toSortedVariants(
            Array<TextureAtlas.AtlasRegion> regions
    ) {
        TextureRegion[] variants = new TextureRegion[regions.size];

        for (int i = 0; i < regions.size; i++) {
            variants[i] = regions.get(i);
        }

        // Порядок регионов в атласе может меняться при перепаковке,
        // поэтому сортируем варианты по индексу (как сделано в Conveyor).
        java.util.Arrays.sort(variants, (a, b) -> {
            int indexA = ((TextureAtlas.AtlasRegion) a).index;
            int indexB = ((TextureAtlas.AtlasRegion) b).index;
            return Integer.compare(indexA, indexB);
        });

        return variants;
    }
}