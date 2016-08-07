package it.giacomobergami.facerei.utils.HTTP;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vasistas on 07/08/16.
 */
public class Replies {
    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void write404(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(404,0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
}
