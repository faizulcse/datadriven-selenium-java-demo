package utils;

public class PropertyUtils {
    public static String getString(String key) {
        return System.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(System.getProperty(key));
    }

    public static int getInteger(String key) {
        return Integer.parseInt(System.getProperty(key));
    }
}
