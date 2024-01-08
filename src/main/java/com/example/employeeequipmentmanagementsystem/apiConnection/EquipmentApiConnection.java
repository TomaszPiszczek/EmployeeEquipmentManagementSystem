package com.example.employeeequipmentmanagementsystem.apiConnection;


import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.prefs.Preferences;

public class EquipmentApiConnection {


    public void authenticate(Preferences userPreferences) {


        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + userPreferences.get("email", "") + "\",\"password\":\"" + userPreferences.get("password", "") + "\"}");
        String path = "auth/authentication";
        JsonObject jsonResponse = getApi(path, "GET", bodyPublisher, "", JsonObject.class);

        userPreferences.put("token", jsonResponse.get("token").getAsString());

    }

    /**
     * @param path   api path after : :port/api/v1/
     * @param method method type {GET, POST, PATCH,DELETE}
     * @param body
     * @param token
     * @param type
     * @param <T>
     * @return Specified type from JSON deserialization.
     */

    public <T> T getApi(String path, String method, HttpRequest.BodyPublisher body, String token, Type type) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/api/v1/" + path)).header("Content-Type", "application/json");
            System.out.println(isTokenValid(token));

            if (token != null && !token.isEmpty()) {
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            switch (method.toUpperCase()) {
                case "GET" -> requestBuilder.GET();
                case "POST" -> requestBuilder.POST(body);
                case "DELETE" -> requestBuilder.DELETE();
                case "PATCH" -> requestBuilder.method("PATCH", body);
                default -> throw new IllegalArgumentException("Wrong method " + method);
            }

            HttpRequest request = requestBuilder.build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            Gson gson = new Gson();
            return gson.fromJson(response.body(), type);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void login(String email, String password, String userName) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");
        String path = "auth/authentication";

        JsonObject jsonResponse = getApi(path, "POST", bodyPublisher, "", JsonObject.class);

        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("password", password);
        userPreferences.put("userName", userName);
        userPreferences.put("token", jsonResponse.get("token").getAsString());
    }

    public boolean isTokenValid(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey("46cf5776e9795db0e5606570207c667d282704912fd91352020c553b766a4d8c").build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


}
