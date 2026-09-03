package foundustry.log;

public class Log {
    public void errExc(Exception exception) {
        System.err.println("[Error]" + exception.getMessage());
    }

    public void errExc(String message) {
        System.err.println("[Error]" + message);
    }

    public void info(String message) {
        System.out.println("\u001B[34m" + "[Info]" +  message + "\u001B[0m");
    }

    public void debug(String message) {
        System.out.println("\u001B[35m" + "[Debug]" + message + "\u001B[0m");
    }

    public void warn(String message) {
        System.out.println("\u001B[33m" + "[Warn]" + message + "\u001B[0m");
    }
}
