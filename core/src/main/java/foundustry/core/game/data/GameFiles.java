package foundustry.core.game.data;

import foundustry.log.Log;

import java.io.File;

public final class GameFiles {

    public static final File ROOT = new File(
            System.getenv("LOCALAPPDATA") != null
                    ? System.getenv("LOCALAPPDATA") + "/Foundustry"
                    : System.getProperty("user.home") + "/Foundustry"
    );

    public static final File SAVES = new File(ROOT, "saves");
    public static final File LOGS = new File(ROOT, "logs");

    private GameFiles() {
    }

    public static void init() {
        createDirectory(ROOT);
        createDirectory(SAVES);
        createDirectory(LOGS);

        Log.info("Foundustry data directory: " + ROOT.getAbsolutePath());
    }

    private static void createDirectory(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException(
                    "Failed to create directory: " + directory.getAbsolutePath()
            );
        }

        if (!directory.isDirectory()) {
            throw new IllegalStateException(
                    "Path is not a directory: " + directory.getAbsolutePath()
            );
        }
    }
}
