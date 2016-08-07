package it.giacomobergami.facerei.Queries.requests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.giacomobergami.facerei.JavaXSLT.transformers.Transformers;
import it.giacomobergami.facerei.JavaXSLT.transformers.html.ComponentHTML;
import it.giacomobergami.facerei.utils.HTTP.Replies;
import it.giacomobergami.facerei.utils.HTTP.Requests;
import org.dom4j.Document;

import java.io.File;
import java.io.IOException;

public class ViewData implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                String name = "data/" + Requests.queryGETToMap(httpExchange).get("file");
                File file = new File(name);
                if (!file.exists()) {
                    Replies.write404(httpExchange);
                } else {
                    ((ComponentHTML) Transformers.t.transformations.get("component")).reset();
                    Document doc = Transformers.xmlView(Transformers.readFromFile(name).get().getRootElement());
                    httpExchange.getResponseHeaders().set("Content-Type", "text/html");
                    Replies.writeResponse(httpExchange, doc.asXML());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }