package utils;

public class PropertyUtils {
    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    public static String getString(String key) {
        return System.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(System.getProperty(key));
    }

    public static int getInteger(String key) {
        return Integer.parseInt(System.getProperty(key));
    }

    public static String setProperty(String key, String value) {
        return System.setProperty(key, value);
    }

    public static String setProperty(String key, int value) {
        return System.setProperty(key, String.valueOf(value));
    }

    public static String setProperty(String key, boolean value) {
        return System.setProperty(key, String.valueOf(value));
    }
}
