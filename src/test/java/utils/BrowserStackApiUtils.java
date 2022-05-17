package utils;

import com.browserstack.local.Local;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.remote.SessionId;

import java.io.File;
import java.util.HashMap;

public class BrowserStackApiUtils {
    private static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String SERVER_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    private static final String API_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@api-cloud.browserstack.com";

    private static final String DELETE_ENDPOINT = "/app-automate/app/delete/";
    private static final String UPLOAD_ENDPOINT = "/app-automate/upload";
    private static final String RECENT_ENDPOINT = "/app-automate/recent_apps/";
    private static final String SESSION_ENDPOINT = "/app-automate/sessions/";

    private static final String PASSED_MESSAGE = "All steps passed!";
    private static final String FAILED_MESSAGE = "Test execution failed!";

    private static Local local;

    public static void enableLocalTesting() throws Exception {
        local = new Local();
        HashMap<String, String> localArgs = new HashMap<>();
        localArgs.put("key", AUTOMATE_ACCESS_KEY);
        local.start(localArgs);
    }

    public static void disableLocalTesting() throws Exception {
        local.stop();
    }

    public static void setTestAsPassed(SessionId id) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "PASSED");
        payload.addProperty("reason", PASSED_MESSAGE);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    public static void setTestAsFailed(SessionId id) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "FAILED");
        payload.addProperty("reason", FAILED_MESSAGE);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    private static void setTestStatus(SessionId id, String status) {
        RestAssured.given()
                .contentType("application/json")
                .body(status)
                .put(API_URL + SESSION_ENDPOINT + id + ".json")
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static String uploadAppToBs(String appPath, String customId) {
        System.out.println("App Uploading(" + appPath + ")...");
        Response res = RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart("file", new File(appPath))
                .formParam("custom_id", customId)
                .post(API_URL + UPLOAD_ENDPOINT);
        res.then().assertThat().statusCode(200);
        return new Gson().fromJson(res.asString(), JsonObject.class).get("app_url").getAsString();
    }

    public static void deleteAppFromBs(String appId) {
        RestAssured.given()
                .delete(API_URL + DELETE_ENDPOINT + appId)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static Response getRecentApps() {
        return recentApp(API_URL + RECENT_ENDPOINT);
    }

    public static String getRecentApp(String customId) {
        Response res = recentApp(API_URL + RECENT_ENDPOINT + customId);
        try {
            return new Gson().fromJson(res.asString(), JsonArray.class).get(0).getAsJsonObject().get("app_url").getAsString();
        } catch (Exception ignored) {
            return res.body().asString();
        }
    }

    private static Response recentApp(String apiUrl) {
        Response res = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl);
        res.then().assertThat().statusCode(200);
        return res;
    }

    public static void main(String[] args) throws Exception {
        enableLocalTesting();
        System.out.println("App id: " + getRecentApp("test_app"));
        System.out.println("All apps: " + getRecentApps().asPrettyString());
        disableLocalTesting();
    }
}
