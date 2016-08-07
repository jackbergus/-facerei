package it.giacomobergami.facerei.Queries.requests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.giacomobergami.facerei.utils.HTTP.Replies;
import it.giacomobergami.facerei.utils.HandleXMLS.FileSystem;

import java.io.IOException;

public class HTMLPage implements HttpHandler {
        public HTMLPage(String page) {
            this.page = page;
        }
        private  final String page;
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Replies.writeResponse(httpExchange, FileSystem.readFile(page));
        }
    }