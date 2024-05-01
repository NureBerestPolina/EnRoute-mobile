package com.example.enroute.Services;

public class ODataQueryBuilder {

    private StringBuilder query;

    public ODataQueryBuilder() {
        this.query = new StringBuilder();
    }

    public ODataQueryBuilder filter(String filter) {
        appendQuery("$filter=" + filter);
        return this;
    }

    public ODataQueryBuilder orderBy(String orderBy) {
        appendQuery("$orderby=" + orderBy);
        return this;
    }

    public ODataQueryBuilder top(int top) {
        appendQuery("$top=" + top);
        return this;
    }

    public ODataQueryBuilder skip(int skip) {
        appendQuery("$skip=" + skip);
        return this;
    }

    public ODataQueryBuilder expand(String expand) {
        appendQuery("$expand=" + expand);
        return this;
    }

    public String build() {
        return query.toString();
    }

    private void appendQuery(String queryPart) {
        if (query.length() > 0) {
            query.append("&");
        }
        query.append(queryPart);
    }
}
