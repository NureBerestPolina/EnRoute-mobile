package com.example.enroute.Services;

import android.content.Context;

import com.example.enroute.Constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ODataService<T> {
    private static final String BASE_URL = Constants.BASE_URL + "/odata";

    private final Class<T> responseType;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public ODataService(Class<T> responseType, Context context) {
        this.responseType = responseType;
        this.httpClient = HttpClientFactory.getInstance(context).getHttpClient();
        this.gson = new Gson();
    }

    public List<T> getAll(String endpoint, ODataQueryBuilder queryBuilder) throws IOException {
        String url = BASE_URL + "/" + endpoint;
        if (queryBuilder != null) {
            url += "?" + queryBuilder.build();
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return parseResponseList(responseBody);
        }
    }

    public T getById(String endpoint, UUID id, ODataQueryBuilder queryBuilder) throws IOException {
        String url = BASE_URL + "/" + endpoint + "/" + id.toString();
        if (queryBuilder != null) {
            url += "?" + queryBuilder.build();
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return parseResponse(responseBody);
        }
    }

    public void create(String endpoint, T entity) throws IOException {
        String json = gson.toJson(entity);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + endpoint)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public void update(String endpoint, String id, T entity) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        String json = gson.toJson(entity);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + endpoint + "/" + id)
                .put(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public void delete(String endpoint, String id) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + endpoint + "(" + id + ")")
                .delete()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private List<T> parseResponseList(String json) {
        // Manually extract the "value" array from the JSON string
        int startIndex = json.indexOf("\"value\":");
        if (startIndex == -1) {
            return null; // "value" array not found
        }

        int endIndex = json.lastIndexOf("]");
        if (endIndex == -1) {
            return null; // Unable to find the end of the "value" array
        }

        String valueArrayJson = json.substring(startIndex + 8, endIndex + 1);

        Type listType = new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{responseType};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return null;
            }
        };

        return gson.fromJson(valueArrayJson, listType);
    }

    private T parseResponse(String json) {
        return gson.fromJson(json, responseType);
    }
}
