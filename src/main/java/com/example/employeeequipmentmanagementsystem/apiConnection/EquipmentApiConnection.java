package com.example.employeeequipmentmanagementsystem.apiConnection;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.employeeequipmentmanagementsystem.exception.LoginException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(new URI("https://equipmentapi2.azurewebsites.net/api/v1/" + path)).header("Content-Type", "application/json");
            Preferences userPref = Preferences.userRoot();


            if(!isTokenValid(userPref.get("token",""))){
                authenticate(Preferences.userRoot());
            }
            if(!userPref.get("token","").equals("")){
                requestBuilder.header("Authorization", "Bearer " + userPref.get("token",""));
            }



            switch (method.toUpperCase()) {
                case "GET" -> requestBuilder.GET();
                case "POST" ->{
                    if(body == null){
                       body = HttpRequest.BodyPublishers.noBody();
                    }
                    requestBuilder.POST(body);
                }
                case "DELETE" -> requestBuilder.DELETE();
                case "PATCH" -> {
                    if(body == null){
                        body = HttpRequest.BodyPublishers.noBody();
                    }
                    requestBuilder.method("PATCH",body);
                }

                default -> throw new IllegalArgumentException("Wrong method " + method);
            }

            HttpRequest request = requestBuilder.build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.body() ==null) return null;
            if(response.statusCode() == 403) throw new RuntimeException(response.statusCode() + "Forbidden " + response.body());
            if(response.statusCode() == 404) throw new RuntimeException(response.statusCode() + response.body());
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
       
        userPreferences.put("token", jsonResponse.get("token").getAsString());
    }

    public static boolean isTokenValid(String jwtToken) {
            DecodedJWT jwt = JWT.decode(jwtToken);
            return !jwt.getExpiresAt().before(new Date());
    }
    public static void authenticate(Preferences userPreferences) {
        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"email\":\"" + userPreferences.get("email", "") + "\",\"password\":\"" + userPreferences.get("password", "") + "\"}");
            String path = "auth/authentication";

            HttpRequest.Builder authRequestBuilder = HttpRequest.newBuilder().uri(new URI("https://equipmentapi2.azurewebsites.net/api/v1/" + path))
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


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
