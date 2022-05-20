package utils;

import java.util.ResourceBundle;

public class ResourceHelper {
    public ResourceBundle bundle;

    public ResourceHelper getResource(String file) {
        bundle = ResourceBundle.getBundle(file);
        return this;
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(bundle.getString(key));
    }

    public int getInteger(String key) {
        return Integer.parseInt(bundle.getString(key));
    }
}
