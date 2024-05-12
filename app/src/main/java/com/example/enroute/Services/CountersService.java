package com.example.enroute.Services;

import android.content.Context;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Models.PickupCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CountersService {
    private Context context;

    public CountersService(Context сontext) {
        this.context = сontext;
    }
    public List<PickupCounter> getOrganizationsCounters(UUID organizationId) {
        List<PickupCounter> counters = new ArrayList<>();
        ODataService<PickupCounter> t = new ODataService<PickupCounter>(PickupCounter.class, context);
        ODataQueryBuilder builder = new ODataQueryBuilder();
        builder.filter("OrganizationId eq " + organizationId.toString());

        try {
            counters = t.getAll(ODataEndpointsNames.PICKUP_COUNTERS, builder);
        } catch (IOException e) {
        }

        return counters;
    }
}
