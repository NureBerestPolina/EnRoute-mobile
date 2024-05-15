package com.example.enroute.Services;

import static com.example.enroute.Constants.Constants.BASE_URL;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.enroute.Models.Category;
import com.example.enroute.Models.Good;
import com.example.enroute.Models.Order;
import com.example.enroute.Models.Organization;
import com.example.enroute.Models.PickupCounter;
import com.example.enroute.RequestModels.MakeOrderRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderService {
    private Context context;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public OrderService(Context сontext) {
        this.context = сontext;
        this.httpClient = HttpClientFactory.getInstance(context).getHttpClient();
        this.gson = new Gson();
    }

    public void seedOrganizations(Spinner shopSpinner) {
        OrganizationsService organizationsService = new OrganizationsService(context);
        List<Organization> organizations = organizationsService.getOrganizations();

        if (organizations != null) {
            ArrayAdapter<Organization> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, organizations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shopSpinner.setAdapter(adapter);
        }
    }

    public void seedCategories(Spinner categoriesSpinner) {
        CategoriesService categoriesService = new CategoriesService(context);
        List<Category> categories = categoriesService.getCategories();

        if (categories != null) {
            ArrayAdapter<Category> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoriesSpinner.setAdapter(adapter);
        }
    }

    public void seedCounters(Spinner countersSpinner, UUID organizationId) {
        CountersService countersService = new CountersService(context);
        List<PickupCounter> counters = countersService.getOrganizationsCounters(organizationId);

        if (counters != null) {
            ArrayAdapter<PickupCounter> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, counters);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countersSpinner.setAdapter(adapter);
        }
    }

    public void seedGoods(Spinner goodsSpinner, UUID organizationId, UUID categoryId) {
        GoodsService goodsService = new GoodsService(context);
        List<Good> goods = goodsService.getGoods(organizationId, categoryId);

        if (goods != null) {
            ArrayAdapter<Good> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, goods);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            goodsSpinner.setAdapter(adapter);
        }
    }

    public void makeOrder(MakeOrderRequest orderRequest) throws IOException {
        String json = gson.toJson(orderRequest);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/MakeOrder")
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public List<Good> getOrderRecommendations(UUID userId) throws IOException {
        String url = BASE_URL + "/order-recommendations/" + userId.toString();

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

    private List<Good> parseResponseList(String json) {
        // Manually extract the "value" array from the JSON string
        int startIndex = json.indexOf("[");
        if (startIndex == -1) {
            return null; // "value" array not found
        }

        int endIndex = json.lastIndexOf("]");
        if (endIndex == -1) {
            return null; // Unable to find the end of the "value" array
        }

        String valueArrayJson = json.substring(startIndex, endIndex + 1);

        Type listType = new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{Good.class};
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
}
