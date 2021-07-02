package com.example.coronatracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Locale;

/**
 * Keeps track of a list of all countries and their statuses regarding the coronavirus.
 */
public class CountryInfo {
    /** The Json response from the GET request. */
    private JsonObject info;

    /** The number of total deaths. */
    private String numDeaths;

    /** The number of recovered cases. */
    private String numRecovered;

    /** The number of total cases. */
    private String numCases;

    /** All countries of the world, received from the Json Response. */
    private HashMap<String, JsonObject> mapOfCountries;

    /**
     * Creates a Country Info object, which stores information about
     * the number of deaths, recovered, and total cases for a specified country
     *
     * @param response the JsonObject response from the API
     */
    public CountryInfo(JsonObject response) {
        info = response;
        JsonArray countries = info.get("Countries").getAsJsonArray();
        mapOfCountries = new HashMap<>();
        for (JsonElement c : countries) {
            JsonObject cObj = c.getAsJsonObject();
            String countryName = cObj.get("Country").getAsString();
            mapOfCountries.put(countryName, cObj);
        }
    }

    /**
     * Extracts essential country info from the Json response
     * @param country the name of the country analyzed
     */
    public void extractInfo(String country) {
        JsonObject ctObj = null;
        if (mapOfCountries.containsKey(country)) {
            ctObj = mapOfCountries.get(country);
        } else if (country.equals("Global")) {
            ctObj = info.get("Global").getAsJsonObject();
        }
        JsonElement deaths = ctObj.get("TotalDeaths");
        numDeaths = String.format(Locale.US, "%,d", deaths.getAsInt());
        JsonElement recovered = ctObj.get("TotalRecovered");
        numRecovered = String.format(Locale.US, "%,d", recovered.getAsInt());
        JsonElement cases = ctObj.get("TotalConfirmed");
        numCases = String.format(Locale.US, "%,d", cases.getAsInt());
    }

    /**
     * Gets the number of total cases for the country
     * @return the total cases
     */
    public String getNumCases() {
        return numCases;
    }

    /**
     * Gets the number of total deaths for the country
     * @return the total deaths
     */
    public String getNumDeaths() {
        return numDeaths;
    }

    /**
     * Gets the number of total recovered cases for the country
     * @return the number of total recovered
     */
    public String getNumRecovered() {
        return numRecovered;
    }
}
