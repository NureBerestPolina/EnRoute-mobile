package com.example.enroute.Services;

import android.content.Context;
import android.content.SharedPreferences;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.enroute.Models.User;
import com.example.enroute.Utils.DateHelper;
import com.google.gson.Gson;

import java.util.UUID;

public class TokenStorageService {
    private static final String TOKEN_KEY = "auth-token";
    private static final String REFRESHTOKEN_KEY = "auth-refreshtoken";
    private static final String USER_KEY = "auth-user";

    /*Context context = getActivity();
    SharedPreferences sharedPref = context.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);*/
    private SharedPreferences sharedPreferences;

    public TokenStorageService(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public void signOut() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
        User user = getUserFromToken(token);
        saveUser(user);
    }

    private User getUserFromToken(String token) {
        JWT jwt = new JWT(token);
        Claim idClaim = jwt.getClaim("sub");
        Claim emailClaim = jwt.getClaim("email");
        Claim nameClaim = jwt.getClaim("name");
        Claim registerDateClaim = jwt.getClaim("registerDate");
        Claim rolesClaim = jwt.getClaim("roles");

        User user = new User(UUID.fromString(idClaim.asString()),
                emailClaim.asString(),
                nameClaim.asString(),
                DateHelper.ParseFromUTCString(registerDateClaim.asString()),
                rolesClaim.asString());

        return user;
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void saveRefreshToken(String refreshToken) {
        sharedPreferences.edit().putString(REFRESHTOKEN_KEY, refreshToken).apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESHTOKEN_KEY, null);
    }

    public void saveUser(User user) {
        String userJson = new Gson().toJson(user);
        sharedPreferences.edit().putString(USER_KEY, userJson).apply();
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        return new Gson().fromJson(userJson, User.class);
    }
}
