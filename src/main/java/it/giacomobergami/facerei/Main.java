package it.giacomobergami.facerei;

import com.sun.net.httpserver.HttpServer;
import it.giacomobergami.facerei.Queries.requests.*;

import java.net.InetSocketAddress;

/**
 * Defines the server
 */
public class Main {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Main request center
        // --> The list of all the possible results
        server.createContext("/",new HTMLPage("index.html"));
        // --> Integrate all the possible different data
        //server.createContext("/integrate",new HTMLPage("chicopisco_viho.html"));
        // --> Aggregate all the possible configurations
        //server.createContext("/aggregate",new HTMLPage("chicopisco_paiura.html"));

        //////////
        // Provides the autocompletion of the tags getting the informations from the XML documents
        server.createContext("/autocomplete",new Autocomplete());

        // Visualize the XML data as HTML
        server.createContext("/visit", new ViewData());

        //////////
        /// QUERY REPLIES
        // Getting a document request --> Returning the result of the query (Separated different documents)
        server.createContext("/request", new Original());
        // Getting a document request --> Returning the list of all the possible results
        server.createContext("/rIntegrate", new Integrate());
        // Getting a document request --> Returning the aggregation of the solution via "ontology"
        server.createContext("/rAggregate", new Aggregate());

        server.setExecutor(null); // creates a default executor
        server.start();
    }


}
