package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.remote.SessionId;

import java.io.File;

public class BrowserStackApi implements BrowserStack {

    public static void setTestAsPassed(SessionId id, String message) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "PASSED");
        payload.addProperty("reason", message);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    public static void setTestAsFailed(SessionId id, String message) {
        JsonObject payload = new JsonObject();
        payload.addProperty("status", "FAILED");
        payload.addProperty("reason", message);
        String status = new Gson().toJson(payload);
        setTestStatus(id, status);
    }

    private static void setTestStatus(SessionId id, String status) {
        RestAssured.given()
                .contentType("application/json")
                .body(status)
                .put(SESSION_API + id + ".json")
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
                .post(UPLOAD_API);
        res.then().assertThat().statusCode(200);
        return new Gson().fromJson(res.asString(), JsonObject.class).get("app_url").getAsString();
    }

    public static void deleteAppFromBs(String appId) {
        RestAssured.given()
                .delete(DELETE_API + appId)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static Response getRecentApps() {
        return recentApp(RECENT_API);
    }

    public static String getRecentApp(String customId) {
        Response res = recentApp(RECENT_API + customId);
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
        System.out.println("App id: " + getRecentApp("test_app"));
        System.out.println("All apps: " + getRecentApps().asPrettyString());
    }
}
