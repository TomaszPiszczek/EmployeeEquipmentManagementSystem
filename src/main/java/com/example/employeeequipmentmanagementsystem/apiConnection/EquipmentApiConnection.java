package com.example.employeeequipmentmanagementsystem.apiConnection;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Key;
import java.util.Date;
import java.util.prefs.Preferences;

public class EquipmentApiConnection {

    public void authenticate(Preferences userPreferences) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + userPreferences.get("email","") + "\",\"password\":\"" + userPreferences.get("password","") + "\"}");
        String path = "auth/authentication";
        JSONObject jsonResponse = getJsonResponse(path,"POST",bodyPublisher,"");

        userPreferences.put("token",jsonResponse.getString("token"));
    }

    public  JSONObject getJsonResponse(String path, String method, HttpRequest.BodyPublisher body, String token){
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/v1/" + path))
                    .header("Content-Type", "application/json");

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
            return new JSONObject(response.body());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;

    }

    public void login(String email,String password,String userName) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");
        String path = "auth/authentication";

        JSONObject jsonResponse = getJsonResponse(path,"POST",bodyPublisher,"");

        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("password",password);
        userPreferences.put("userName",userName);
        userPreferences.put("token",jsonResponse.getString("token"));
    }

    public static boolean isTokenValid(String jwtToken) {
        try {
            Jws<Claims> claims =  Jwts.parserBuilder()
                    .setSigningKey("46cf5776e9795db0e5606570207c667d282704912fd91352020c553b766a4d8c").build()
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }




}
