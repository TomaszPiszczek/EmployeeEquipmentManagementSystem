package com.example.employeeequipmentmanagementsystem.apiConnection;


import com.example.employeeequipmentmanagementsystem.exception.LoginException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.prefs.Preferences;

public class EquipmentApiConnection {



    /**
     * @param path   api path after : :port/api/v1/
     * @param method method type {GET, POST, PATCH,DELETE}
     * @return return specified type from JSON deserialization.
     */

    public static  <T> T callApi(String path, String method, HttpRequest.BodyPublisher body, Type type) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/api/v1/" + path)).header("Content-Type", "application/json");
            Preferences userPref = Preferences.userRoot();


                if(!isTokenValid(userPref.get("token",""))){
                    authenticate(Preferences.userRoot());
                }
                if(!userPref.get("token","").equals("")){
                    requestBuilder.header("Authorization", "Bearer " + userPref.get("token",""));
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

            if(response.body() ==null) return null;
            if(response.statusCode() == 403) throw new LoginException(response.statusCode() + "Wrong email or password" + response.body());
            if(response.statusCode() == 404) throw new FileNotFoundException(response.body());
            if( type == String.class) return (T) response.body();
            if(type== null) return null;
            Gson gson = new Gson();
            return gson.fromJson(response.body(), type);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public static void login(String email, String password) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");
        String path = "auth/authentication";

        JsonObject jsonResponse = callApi(path, "POST", bodyPublisher,JsonObject.class);
        if(jsonResponse == null) return;

        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("password", password);
        userPreferences.put("email", email);
        userPreferences.put("token", jsonResponse.get("token").getAsString());
    }

    public static boolean isTokenValid(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey("46cf5776e9795db0e5606570207c667d282704912fd91352020c553b766a4d8c").build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public static void authenticate(Preferences userPreferences) {
        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + userPreferences.get("email", "") + "\",\"password\":\"" + userPreferences.get("password", "") + "\"}");
            String path = "auth/authentication";

            HttpRequest.Builder authRequestBuilder = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/api/v1/" + path))
                    .header("Content-Type", "application/json").POST(bodyPublisher);

            HttpRequest authRequest = authRequestBuilder.build();
            HttpClient authClient = HttpClient.newHttpClient();
            HttpResponse<String> authResponse = authClient.send(authRequest, HttpResponse.BodyHandlers.ofString());

            if (authResponse.statusCode() == 403) {
                throw new LoginException(authResponse.statusCode() + " Wrong email or password" + authResponse.body());
            }

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(authResponse.body(), JsonObject.class);
            userPreferences.put("token", jsonResponse.get("token").getAsString());

        } catch (LoginException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
