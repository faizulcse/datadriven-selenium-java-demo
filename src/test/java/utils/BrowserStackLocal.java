package utils;

import com.browserstack.local.Local;

import java.util.HashMap;

public class BrowserStackLocal implements BrowserStack {
    private static Local local;

    public static void enableLocalTesting() throws Exception {
        local = new Local();
        HashMap<String, String> localArgs = new HashMap<>();
        localArgs.put("key", ACCESS_KEY);
        local.start(localArgs);
    }

    public static boolean isLocalRunning() throws Exception {
        return local != null && local.isRunning();
    }

    public static void disableLocalTesting() throws Exception {
        if (isLocalRunning())
            local.stop();
    }

    public static void main(String[] args) throws Exception {
        BrowserStackLocal.enableLocalTesting();
        System.out.println(BrowserStackLocal.isLocalRunning());
        BrowserStackLocal.disableLocalTesting();
    }
}
