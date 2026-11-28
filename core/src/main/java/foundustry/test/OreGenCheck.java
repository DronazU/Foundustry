package foundustry.test;

import foundustry.log.Log;
import foundustry.types.Block;
import foundustry.types.item.Item;
import foundustry.world.Chunk;
import foundustry.world.Generator;
import foundustry.world.OreDeposit;
import foundustry.world.Tile;
import foundustry.world.contentRegister.Blocks;
import foundustry.world.contentRegister.Items;

public class OreGenCheck {

    static int errors = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            errors++;
            Log.errExc("OreGenCheck: " + msg);
        }
    }

    static boolean sameOre(Tile a, Tile b) {
        if (a.ore == null && b.ore == null) return true;
        if (a.ore == null || b.ore == null) return false;

        return a.ore.item == b.ore.item
                && a.ore.amount == b.ore.amount;
    }

    public static void main(String[] args) {

        Block deepWater = new Block("deep-water");
        Block water = new Block("water");
        Block sandWater = new Block("sand-water");
        Block sandFloor = new Block("sand-floor");
        Block dirt = new Block("dirt");
        Block grass = new Block("grass");
        Block stone = new Block("stone");
        Block nothing = new Block("nothing");

        Blocks.deepWater = deepWater;
        Blocks.water = water;
        Blocks.sandWater = sandWater;
        Blocks.sandFloor = sandFloor;
        Blocks.dirt = dirt;
        Blocks.grass = grass;
        Blocks.stone = stone;
        Blocks.nothing = nothing;

        Items.unrefinedIron = new Item("unrefined-iron");
        Items.unrefinedCopper = new Item("unrefined-copper");

        long seed = 424242L;

        Generator g1 = new Generator();
        g1.seed = seed;

        Generator g1b = new Generator();
        g1b.seed = seed;

        Generator g2 = new Generator();
        g2.seed = 987654L;

        Chunk c1a = g1.generateChunk(0, 0);
        Chunk c1b = g1b.generateChunk(0, 0);
        Chunk cRight = g1.generateChunk(1, 0);
        Chunk cUp = g1.generateChunk(0, 1);
        Chunk cOther = g2.generateChunk(0, 0);

        // 1. Детерминизм

        boolean deterministic = true;

        for (int x = 0; x < 32 && deterministic; x++) {
            for (int y = 0; y < 32; y++) {
                if (!sameOre(c1a.tiles[x][y], c1b.tiles[x][y])) {
                    deterministic = false;
                    break;
                }
            }
        }

        check(deterministic, "одинаковый seed даёт одинаковую генерацию");

        // 2. Разные seed

        boolean different = false;

        for (int x = 0; x < 32 && !different; x++) {
            for (int y = 0; y < 32; y++) {
                if (!sameOre(c1a.tiles[x][y], cOther.tiles[x][y])) {
                    different = true;
                    break;
                }
            }
        }

        check(different, "разные seed дают разную генерацию");

        // 3. Границы чанков

        int boundaryContinuesX = 0;
        int boundaryContinuesY = 0;

        for (int y = 0; y < 32; y++) {
            Tile left = c1a.tiles[31][y];
            Tile right = cRight.tiles[0][y];

            if (left.ore != null && right.ore != null
                    && left.ore.item == right.ore.item) {
                boundaryContinuesX++;
            }
        }

        for (int x = 0; x < 32; x++) {
            Tile bottom = c1a.tiles[x][31];
            Tile top = cUp.tiles[x][0];

            if (bottom.ore != null && top.ore != null
                    && bottom.ore.item == top.ore.item) {
                boundaryContinuesY++;
            }
        }

        Log.info("Продолжения руды по X: " + boundaryContinuesX + "/32");

        Log.info("Продолжения руды по Y: " + boundaryContinuesY + "/32");

        // 4. Проверка типов руды и поверхности

        boolean noWaterOre = true;

        int landTiles = 0;
        int oreTiles = 0;
        int ironTiles = 0;
        int copperTiles = 0;

        long totalAmount = 0;

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {

                Tile tile = c1a.tiles[x][y];

                if (tile.ore != null) {
                    oreTiles++;
                    totalAmount += tile.ore.amount;

                    check(tile.ore.amount >= 1, "запас руды положительный");

                    check(!tile.ore.isDepleted(), "месторождение не истощено при генерации");

                    check(tile.ore.item == Items.unrefinedIron || tile.ore.item == Items.unrefinedCopper, "тип руды известен");

                    if (tile.floor == water
                            || tile.floor == deepWater
                            || tile.floor == sandWater
                            || tile.floor == nothing) {

                        noWaterOre = false;

                        Log.errExc("Руда на воде или пустой клетке: " + x + "," + y);
                    }

                    if (tile.ore.item == Items.unrefinedIron) {
                        ironTiles++;
                    } else if (tile.ore.item == Items.unrefinedCopper) {
                        copperTiles++;
                    }
                }

                if (tile.floor != water
                        && tile.floor != deepWater
                        && tile.floor != sandWater
                        && tile.floor != nothing) {
                    landTiles++;
                }
            }
        }

        check(noWaterOre, "руда не размещается на воде и nothing");

        // 5. Проверка размеров залежей

        boolean[][] visited = new boolean[32][32];

        int maxBlob = 0;
        int blobCount = 0;

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {

                if (c1a.tiles[x][y].ore == null || visited[x][y]) {
                    continue;
                }

                int size = flood(c1a, visited, x, y);

                maxBlob = Math.max(maxBlob, size);
                blobCount++;
            }
        }

        Log.info("Клеток с рудой: " + oreTiles + " / " + landTiles);
        Log.info("Железо: " + ironTiles + ", медь: " + copperTiles);
        Log.info("Количество залежей: " + blobCount);
        Log.info("Общий запас: " + totalAmount);
        Log.info("Максимальная залежь: " + maxBlob);

        check(oreTiles > 20, "в чанке достаточно руды");

        check(maxBlob > 5, "есть крупная залежь больше 5 клеток");

        // 6. Диапазон запасов

        int minAmount = Integer.MAX_VALUE;
        int maxAmount = 0;

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {

                OreDeposit ore = c1a.tiles[x][y].ore;

                if (ore == null) continue;

                minAmount = Math.min(minAmount, ore.amount);
                maxAmount = Math.max(maxAmount, ore.amount);
            }
        }

        if (oreTiles > 0) {
            Log.info("Диапазон запасов: " + minAmount + " .. " + maxAmount);
        } else {
            Log.warn("В чанке нет месторождений");
        }

        // Итог

        Log.info("--------------------------------");

        if (errors == 0) {
            Log.info("ALL CHECKS PASSED");
        } else {
            Log.errExc(errors + " CHECK(S) FAILED");
        }
    }

    static int flood(Chunk chunk, boolean[][] visited, int sx, int sy) {

        int size = 0;

        int[][] stack = new int[32 * 32][2];
        int sp = 0;

        Item targetItem = chunk.tiles[sx][sy].ore.item;

        stack[sp][0] = sx;
        stack[sp][1] = sy;
        sp++;

        visited[sx][sy] = true;

        while (sp > 0) {

            sp--;

            int x = stack[sp][0];
            int y = stack[sp][1];

            Tile tile = chunk.tiles[x][y];

            if (tile.ore == null || tile.ore.item != targetItem) {
                continue;
            }

            size++;

            if (x > 0 && !visited[x - 1][y]
                    && chunk.tiles[x - 1][y].ore != null
                    && chunk.tiles[x - 1][y].ore.item == targetItem) {

                visited[x - 1][y] = true;
                stack[sp][0] = x - 1;
                stack[sp][1] = y;
                sp++;
            }

            if (x < 31 && !visited[x + 1][y]
                    && chunk.tiles[x + 1][y].ore != null
                    && chunk.tiles[x + 1][y].ore.item == targetItem) {

                visited[x + 1][y] = true;
                stack[sp][0] = x + 1;
                stack[sp][1] = y;
                sp++;
            }

            if (y > 0 && !visited[x][y - 1]
                    && chunk.tiles[x][y - 1].ore != null
                    && chunk.tiles[x][y - 1].ore.item == targetItem) {

                visited[x][y - 1] = true;
                stack[sp][0] = x;
                stack[sp][1] = y - 1;
                sp++;
            }

            if (y < 31 && !visited[x][y + 1]
                    && chunk.tiles[x][y + 1].ore != null
                    && chunk.tiles[x][y + 1].ore.item == targetItem) {

                visited[x][y + 1] = true;
                stack[sp][0] = x;
                stack[sp][1] = y + 1;
                sp++;
            }
        }

        return size;
    }
}