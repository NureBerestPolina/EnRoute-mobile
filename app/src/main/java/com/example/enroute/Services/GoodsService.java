package com.example.enroute.Services;

import android.content.Context;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Models.Good;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GoodsService {
    private Context context;

    public GoodsService(Context сontext) {
        this.context = сontext;
    }
    public List<Good> getGoods(UUID organizationId, UUID categoryId) {
        List<Good> goods = new ArrayList<>();
        ODataService<Good> t = new ODataService<Good>(Good.class, context);
        ODataQueryBuilder builder = new ODataQueryBuilder();
        builder.filter("OrganizationId eq " + organizationId.toString()
                    + " and CategoryId eq " +categoryId.toString());

        try {
            goods = t.getAll(ODataEndpointsNames.GOODS, builder);
        } catch (IOException e) {
        }

        return goods;
    }
}
