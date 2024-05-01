package com.example.enroute.Interceptors;

import com.example.enroute.Services.AuthService;
import com.example.enroute.Services.TokenStorageService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private TokenStorageService tokenService;
    private AuthService authService;

    public AuthInterceptor(TokenStorageService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();

        String token = tokenService.getToken();
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        Request authRequest = requestBuilder.build();

        Response response = chain.proceed(authRequest);

        if (response.code() == 401) {
            // Handle token refreshing
            String refreshToken = tokenService.getRefreshToken();
            if (refreshToken != null) {
                // Synchronize to make sure only one request refreshes the token
                synchronized (this) {
                    String newToken = authService.refreshToken(token, refreshToken).getRefreshToken();
                    if (newToken != null) {
                        requestBuilder.header("Authorization", "Bearer " + newToken);
                        authRequest = requestBuilder.build();
                        return chain.proceed(authRequest);
                    } else {
                        // Token refresh failed, logout or handle accordingly
                        authService.logout();
                    }
                }
            } else {
                // No refresh token available, logout or handle accordingly
                authService.logout();
            }
        }

        return response;
    }
}

