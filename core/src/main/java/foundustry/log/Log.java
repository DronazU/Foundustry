package foundustry.log;

public class Log {
    private String whereIsIt() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (className.startsWith("foundustry") && !className.equals(Log.class.getName())) {
                String fileName = element.getFileName();
                int lineNumber = element.getLineNumber();
                if (fileName != null && lineNumber > 0) {
                    return "(" + fileName + ", line: " + lineNumber + ")";
                } else {
                    return "(unknown source)";
                }
            }
        }
        return "(unknown)";
    }

    public void errExc(Exception exception) {
        System.err.println("[Error]" + exception.getMessage() + " <- " + whereIsIt());
    }

    public void errExc(String message) {
        System.err.println("[Error]" + message + " <- " + whereIsIt());
    }

    public void info(String message) {
        System.out.println("\u001B[34m" + "[Info]" +  message + " <- " + whereIsIt() + "\u001B[0m");
    }

    public void debug(String message) {
        System.out.println("\u001B[35m" + "[Debug]" + message + " <- " + whereIsIt() + "\u001B[0m");
    }

    public void warn(String message) {
        System.out.println("\u001B[33m" + "[Warn]" + message + " <- " + whereIsIt() + "\u001B[0m");
    }
}