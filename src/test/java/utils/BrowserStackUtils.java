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

public class BrowserStackUtils implements BrowserStack {
    private static Local local;

    @Override
    public void setTestAsPassed(SessionId id) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "PASSED");
        payload.addProperty("reason", PASSED_MESSAGE);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    @Override
    public void setTestAsFailed(SessionId id, String message) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "FAILED");
        payload.addProperty("reason", message);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    @Override
    public void setTestStatus(SessionId id, String status) {
        RestAssured.given()
                .contentType("application/json")
                .body(status)
                .put(SESSION_API + id + ".json")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Override
    public String uploadAppToBs(String appPath, String customId) {
        System.out.println("App Uploading(" + appPath + ")...");
        Response res = RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart("file", new File(appPath))
                .formParam("custom_id", customId)
                .post(UPLOAD_API);
        res.then().assertThat().statusCode(200);
        return new Gson().fromJson(res.asString(), JsonObject.class).get("app_url").getAsString();
    }

    @Override
    public void deleteAppFromBs(String appId) {
        RestAssured.given()
                .delete(DELETE_API + appId)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Override
    public Response getRecentApps() {
        Response res = recentApp(RECENT_API);
        res.then().assertThat().statusCode(200);
        return res;

    }

    @Override
    public String getRecentApp(String customId) {
        Response res = recentApp(RECENT_API + customId);
        res.then().assertThat().statusCode(200);
        try {
            return new Gson().fromJson(res.asString(), JsonArray.class).get(0).getAsJsonObject().get("app_url").getAsString();
        } catch (Exception ignored) {
            return res.body().asString();
        }
    }

    @Override
    public Response recentApp(String apiUrl) {
        return RestAssured.given()
                .contentType("application/json")
                .get(apiUrl);
    }

    @Override
    public void enableLocalTesting() {
        local = new Local();
        HashMap<String, String> localArgs = new HashMap<>();
        localArgs.put("key", ACCESS_KEY);
        try {
            local.start(localArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLocalRunning() {
        try {
            return local != null && local.isRunning();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void disableLocalTesting() {
        if (isLocalRunning()) {
            try {
                local.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BrowserStackUtils bs = new BrowserStackUtils();
        System.out.println("App id: " + bs.getRecentApp("test_app"));
        System.out.println("All apps: " + bs.getRecentApps().asPrettyString());
    }
}
