package it.giacomobergami.facerei.Queries.requests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import it.giacomobergami.facerei.JavaXSLT.transformers.Transformers;
import it.giacomobergami.facerei.JavaXSLT.transformers.html.ComponentHTML;
import it.giacomobergami.facerei.Queries.PerformQuery;
import it.giacomobergami.facerei.utils.HTTP.Replies;
import it.giacomobergami.facerei.utils.HTTP.Requests;
import it.giacomobergami.facerei.utils.HandleXMLS.FileSystem;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultText;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Original implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            ((ComponentHTML)Transformers.t.transformations.get("component")).reset();
            try {
                Map<String, String> map = Requests.queryPOSTToMap(httpExchange);
                //Getting all the tags that have to be retrieved from the text
                List<List<PerformQuery.QueryType>> q = map.keySet().stream().map(x -> {
                    return Arrays.asList(new PerformQuery.QueryType(new ResourceIDStructured(x), PerformQuery.QueryClass.Folk));
                }).collect(Collectors.toList());

                int id = 0;

                Document doc = DocumentHelper.createDocument();
                Element results = doc.addElement("blitz");
                Document docR = DocumentHelper.createDocument();
                Element resultsR = docR.addElement("div").addAttribute("id", "results");


                for (File file : FileSystem.filterTagByValue(map)) {
                    Component t = Transformers.dataTransformer(Transformers.readFromFile(file.getAbsolutePath()).get());
                    List<Contents> provider = new ArrayList<>();
                    List<List<Contents>> query = PerformQuery.performQuery(q, 0, t, provider);
                    List<Contents> x = PerformQuery.rankResults(query).stream().flatMap(j -> j.stream()).collect(Collectors.toList());
                    if (!x.isEmpty()) {
                        Element elem = results.addElement("component").addAttribute("id", (id++) + "").addAttribute("name","User").addAttribute("abstract",file.getName());
                        x.stream().filter(y -> y.hasUnderlyingNode() != null).forEach(y -> {
                            Element k = elem.addElement("component").addAttribute("name", ((Component) y).name).addAttribute("abstract", ((Component) y).description);
                            Element element = (Element) y.hasUnderlyingNode();
                            for (int i = 0, size = element.nodeCount(); i < size; i++) {
                                Node node = element.node(i);
                                if (node instanceof Element) {
                                    String name = node.getName();
                                    if (name.equals("tag")) {
                                        Element tag = k.addElement("tag");
                                        if (((Element) node).attributeValue("folk") != null)
                                            tag.addAttribute("folk", ((Element) node).attributeValue("folk"));
                                        if (((Element) node).attributeValue("graph") != null)
                                            tag.addAttribute("graph", ((Element) node).attributeValue("graph"));
                                        tag.addText(node.getText());
                                    }
                                } else if (node instanceof DefaultText) {
                                    k.addText(node.getText());
                                }
                            }
                        });
                        resultsR.addElement("div").addAttribute("id", "result").add(Transformers.htmlTransformer(elem).getRootElement());
                    }
                }

                //Component t = Transformers.dataTransformer(Transformers.readFromFile("CORTONA.xml").get());
               Replies.writeResponse(httpExchange, docR.asXML());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }