package utils;

import com.browserstack.local.Local;

import java.util.HashMap;

public class BrowserStackLocal {
    private static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static Local local;

    public static void enableLocalTesting() throws Exception {
        local = new Local();
        HashMap<String, String> localArgs = new HashMap<>();
        localArgs.put("key", AUTOMATE_ACCESS_KEY);
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
