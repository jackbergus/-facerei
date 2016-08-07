package it.giacomobergami.facerei.Queries.requests;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.giacomobergami.facerei.utils.HTTP.AutoCompleteData;
import it.giacomobergami.facerei.utils.HTTP.Replies;
import it.giacomobergami.facerei.utils.HTTP.Requests;
import it.giacomobergami.facerei.utils.HandleXMLS.FileSystem;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
     * Provides the Folksonomy tags from the tag associated to each document
     */
    public class Autocomplete implements HttpHandler {
        @Override
        public void handle(HttpExchange response) throws IOException {
            final Collection<String> countryList = FileSystem.getAllTags();

            // Map real data into JSON
            response.getResponseHeaders().set("Content-Type", "application/json");

            final String param = Requests.queryPOSTToMap(response).get("term");
            final Set<AutoCompleteData> result = new HashSet<>();
            for (final String country : countryList) {
                if (country.toLowerCase().contains(param.toLowerCase())) {
                    result.add(new AutoCompleteData(country, country));
                }
            }
            Replies.writeResponse(response,new Gson().toJson(result));
        }
    }