package utils;

import io.restassured.response.Response;
import org.openqa.selenium.remote.SessionId;

public interface BrowserStack {
    String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    String BUILD_NAME = System.getenv("BROWSERSTACK_BUILD_NAME");
    String APP_ID = System.getenv("BROWSERSTACK_APP_ID");
    String SERVER_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    String API_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@api-cloud.browserstack.com";
    String DELETE_API = API_URL + "/app-automate/app/delete/";
    String UPLOAD_API = API_URL + "/app-automate/upload";
    String RECENT_API = API_URL + "/app-automate/recent_apps/";
    String SESSION_API = API_URL + "/app-automate/sessions/";
    String PASSED_MESSAGE = "All steps passed!";
    String FAILED_MESSAGE = "Test execution failed!";

    String uploadAppToBs(String appPath, String customId);

    void deleteAppFromBs(String appId);

    void setTestAsPassed(SessionId id);

    void setTestAsFailed(SessionId id, String message);

    Response getRecentApps();

    String getRecentApp(String customId);

    void enableLocalTesting();

    boolean isLocalRunning();

    void disableLocalTesting();
}
