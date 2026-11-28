
package foundustry.test.tmp;

import foundustry.types.Block;
import foundustry.world.Chunk;
import foundustry.world.Generator;
import foundustry.world.OreDeposit;
import foundustry.world.Tile;
import foundustry.world.contentRegister.Blocks;
import foundustry.world.contentRegister.Items;
import foundustry.types.item.Item;

public class OreGenCheck {
    static int errors = 0;

    static void check(boolean cond, String msg) {
        if (cond) {
            System.out.println("ok:   " + msg);
        } else {
            errors++;
            System.out.println("FAIL: " + msg);
        }
    }

    static boolean sameOre(Tile a, Tile b) {
        if (a.ore == null && b.ore == null) return true;
        if (a.ore == null || b.ore == null) return false;
        return a.ore.item == b.ore.item && a.ore.amount == b.ore.amount;
    }

    public static void main(String[] args) {
        // Заглушки контента (атлас в этой проверке не грузим, спрайты будут null).
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
        Chunk c1b = g1b.generateChunk(0, 0); // тот же seed, другой экземпляр
        Chunk cRight = g1.generateChunk(1, 0);
        Chunk cUp = g1.generateChunk(0, 1);
        Chunk cOther = g2.generateChunk(0, 0); // другой seed

        // 1. Детерминизм: одинаковый seed -> одинаковые залежи и запасы.
        boolean deterministic = true;
        for (int x = 0; x < 32 && deterministic; x++) {
            for (int y = 0; y < 32; y++) {
                if (!sameOre(c1a.tiles[x][y], c1b.tiles[x][y])) {
                    deterministic = false;
                    break;
                }
            }
        }
        check(deterministic, "детерминизм при повтороой генерации того же чанка");

        // 2. Разные seed -> разная генерация.
        boolean different = false;
        for (int x = 0; x < 32 && !different; x++) {
            for (int y = 0; y < 32; y++) {
                if (!sameOre(c1a.tiles[x][y], cOther.tiles[x][y])) {
                    different = true;
                    break;
                }
            }
        }
        check(different, "разный seed даёт другую генерацию");

        // 3. Границы чанков: залежи не обрываются — функция чистая по мировым координатам.
        int boundaryContinuesX = 0, boundaryTotalX = 0;
        int boundaryContinuesY = 0, boundaryTotalY = 0;
        for (int y = 0; y < 32; y++) {
            Tile left = c1a.tiles[31][y];
            Tile right = cRight.tiles[0][y]; // worldX = 32
            boundaryTotalX++;
            if (left.ore != null && right.ore != null) boundaryContinuesX++;
        }
        for (int x = 0; x < 32; x++) {
            Tile bottom = c1a.tiles[x][31];
            Tile top = cUp.tiles[x][0]; // worldY = 32
            boundaryTotalY++;
            if (bottom.ore != null && top.ore != null) boundaryContinuesY++;
        }
        System.out.println("info: границы X с рудой с обеих сторон: " + boundaryContinuesX + "/" + boundaryTotalX);
        System.out.println("info: границы Y с рудой с обеих сторон: " + boundaryContinuesY + "/" + boundaryTotalY);
        check(boundaryContinuesX > 0 || boundaryContinuesY > 0,
                "залежи продолжаются сквозь границы чанков хотя бы в одном месте");

        // 4. Руда не на воде и не на nothing.
        boolean noWaterOre = true;
        int landTiles = 0, oreTiles = 0, ironTiles = 0, copperTiles = 0;
        long totalAmount = 0;
        int maxBlob = 0;
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                Tile tile = c1a.tiles[x][y];
                if (tile.ore != null) {
                    oreTiles++;
                    totalAmount += tile.ore.amount;
                    check(tile.ore.amount >= 1, "запас положителен (" + tile.ore.amount + ")");
                    check(!tile.ore.isDepleted(), "клетка не истощена при генерации");
                    check(tile.ore.item == Items.unrefinedIron || tile.ore.item == Items.unrefinedCopper,
                            "тип руды один из двух известных");
                    if (tile.floor == water || tile.floor == deepWater
                            || tile.floor == sandWater || tile.floor == nothing) {
                        noWaterOre = false;
                        System.out.println("FAIL: руда на воде/нигде в " + x + "," + y);
                    }
                    if (tile.ore.item == Items.unrefinedIron) ironTiles++;
                    else copperTiles++;
                }
                if (tile.floor != water && tile.floor != deepWater && tile.floor != sandWater
                        && tile.floor != nothing) landTiles++;
            }
        }
        check(noWaterOre, "руда не размещена на воде (и nothing)");

        // 5. Залежи занимают несколько соседних клеток (связные компоненты внутри чанка).
        boolean[][] visited = new boolean[32][32];
        int maxBlob = 0;
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                if (c1a.tiles[x][y].ore == null || visited[x][y]) continue;
                int size = flood(c1a, visited, x, y);
                maxBlob = Math.max(maxBlob, size);
            }
        }
        System.out.println("info: клеток с рудой: " + oreTiles + " / " + landTiles + " земляных");
        System.out.println("info: железо: " + ironTiles + ", медь: " + copperTiles);
        System.out.println("info: общий запас в чанке: " + totalAmount + ", макс. связный блок: " + maxBlob);
        check(oreTiles > 20, "есть достаточно месторождений (>20 клеток)");
        check(maxBlob > 5, "залежи крупные (связная группа > 5 клеток)");

        // 6. Диапазон запасов.
        int minAmount = Integer.MAX_VALUE, maxAmount = 0;
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                OreDeposit o = c1a.tiles[x][y].ore;
                if (o != null) {
                    minAmount = Math.min(minAmount, o.amount);
                    maxAmount = Math.max(maxAmount, o.amount);
                }
            }
        }
        System.out.println("info: диапазон запасов: " + minAmount + " .. " + maxAmount);

        System.out.println(errors == 0 ? "ALL CHECKS PASSED" : (errors + " CHECK(S) FAILED"));
        if (errors > 0) System.exit(1);
    }

    static int flood(Chunk chunk, boolean[][] visited, int sx, int sy) {
        int size = 0;
        int[][] stack = new int[32 * 32 * 2][2];
        int sp = 0;
        stack[sp][0] = sx;
        stack[sp][1] = sy;
        sp++;
        while (sp > 0) {
            sp--;
            int x = stack[sp][0];
            int y = stack[sp][1];
            if (x < 0 || x >= 32 || y < 0 || y >= 32) continue;
            if (visited[x][y]) continue;
            visited[x][y] = true;
            Tile tile = chunk.tiles[x][y];
            if (tile.ore == null) continue;
            size++;
            stack[sp][0] = x - 1;
            stack[sp][1] = y;
            sp++;
            stack[sp][0] = x + 1;
            stack[sp][1] = y;
            sp++;
            stack[sp][0] = x;
            stack[sp][1] = y - 1;
            sp++;
            stack[sp][0] = x;
            stack[sp][1] = y + 1;
            sp++;
        }
        return size;
    }
}
