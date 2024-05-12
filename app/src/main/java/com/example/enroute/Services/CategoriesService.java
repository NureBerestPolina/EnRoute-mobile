package com.example.enroute.Services;
import android.content.Context;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Models.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesService {
    private Context context;

    public CategoriesService(Context сontext) {
        this.context = сontext;
    }
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        ODataService<Category> t = new ODataService<Category>(Category.class, context);
        ODataQueryBuilder builder = new ODataQueryBuilder();
        try {
            categories = t.getAll(ODataEndpointsNames.CATEGORIES, builder);
        } catch (IOException e) {
        }

        return categories;
    }
}
