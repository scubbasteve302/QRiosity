package com.qriosity.mvc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Main JSON Object that is coming back from Zappos
 */
public class ZapposJson {

    private String limit;
    private String statusCode;
    private List<ZapposJsonItem> results;

    public ZapposJson() {
        this.results = new ArrayList<ZapposJsonItem>();
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<ZapposJsonItem> getResults() {
        return results;
    }

    public void setResults(List<ZapposJsonItem> results) {
        this.results = results;
    }
}
