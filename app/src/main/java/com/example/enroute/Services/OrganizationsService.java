package com.example.enroute.Services;

import android.content.Context;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Models.Organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrganizationsService {
    private Context context;

    public OrganizationsService(Context сontext) {
        this.context = сontext;
    }
    public List<Organization> getOrganizations() {
        List<Organization> organizations = new ArrayList<>();
        ODataService<Organization> t = new ODataService<Organization>(Organization.class, context);
        ODataQueryBuilder builder = new ODataQueryBuilder();
        try {
            organizations = t.getAll(ODataEndpointsNames.ORGANIZATIONS, builder);
        } catch (IOException e) {
        }

        return organizations;
    }

    public Organization getOrganizationByManagerId(UUID managerId) {
        Organization organization = null;
        ODataService<Organization> t = new ODataService<Organization>(Organization.class, context);
        ODataQueryBuilder builder = new ODataQueryBuilder();
        builder.filter("ManagerId eq " + managerId.toString());
        try {
            organization = t.getAll(ODataEndpointsNames.ORGANIZATIONS, builder).get(0);
        } catch (IOException e) {
        }

        return organization;
    }
}
