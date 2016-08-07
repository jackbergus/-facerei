package it.giacomobergami.facerei.Queries.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import it.giacomobergami.facerei.utils.literature.concrete.Tag;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultText;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

public class Integrate implements HttpHandler {

        public JsonObject jsonObject;
        public Integrate() {
            JsonObject jsonObject1;
            // Read from File to String
            try {
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(new FileReader("semein.json"));
                jsonObject1 = jsonElement.getAsJsonObject();
            } catch (FileNotFoundException e) {
                jsonObject1 = null;
            } catch (IOException ioe){
                jsonObject1 = null;
            }
            jsonObject = jsonObject1;
        }

        public void reloadJSON() {
            JsonObject jsonObject1;
            // Read from File to String
            try {
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(new FileReader("semein.json"));
                jsonObject1 = jsonElement.getAsJsonObject();
            } catch (FileNotFoundException e) {
                jsonObject1 = null;
            } catch (IOException ioe){
                jsonObject1 = null;
            }
            jsonObject = jsonObject1;
        }

        /**
         * Rewrites the tag (abstract) into a more compact element
         * @param toConvert
         * @param dest
         */
        public void tagToElement(Tag toConvert, Element dest) {
            String folk = toConvert.getFolkPointer() == null ? "No Folksonomy" : reconstructPointer(toConvert.getFolkPointer());
            if (!folk.equals("No Folksonomy")) folk = "Folksonomy: " +  folk;
            String grap = toConvert.getGraphPointer() == null ? "No Graph pointer" : reconstructPointer(toConvert.getGraphPointer());
            if (!grap.equals("No Graph pointer")) {
                try {
                    grap = URLEncoder.encode(grap, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    grap = "<b>No Graph pointer</b>";
                }
                grap = "<a href=\"/visit/?file=" + grap + "\">Graph Pointer</a>";
            }
            dest.addAttribute("tabindex","0")
                    .addAttribute("class","btn btn-lg btn-danger")
                    .addAttribute("role","button")
                    .addAttribute("data-toggle","popover")
                    .addAttribute("title",folk)
                    .addAttribute("data-content",grap)
                    .addAttribute("data-trigger","hover")
                    .addText(toConvert.getTextualContent());
        }

        /**
         * Generates a list of
         * @param whereToAppend
         * @param tagElements
         * */
        public void generateTableFromList(Element whereToAppend, Collection<Tag> tagElements) {
            Element table = whereToAppend.addElement("table").addAttribute("style","display:inline-table;");
            tagElements.forEach(x->{
                Element cell = table.addElement("tr").addElement("td");
                tagToElement(x,cell);
            });
        }

        /**
         * Reconscruct the original Folk tag into string (original XML format)
         * @param x
         * @return
         */
        public String  reconstructPointer(ResourceIDStructured x) {
            StringBuilder reconstruct = new StringBuilder();
            reconstruct.append(x.refName);
            for (String y : x.path) {
                reconstruct.append('/').append(y);
            }
            return reconstruct.toString();
        }

        /**
         * Returns the semantics associated to the tag from the JSON file
         * @param tag
         * @return
         */
        private String getSemanticsFromJSOJ(String tag) {
            reloadJSON();
            return !jsonObject.has(tag) ? "Bogus on tag " + tag + ":" : jsonObject.get(tag).getAsJsonObject().get("a").getAsString();
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            ((ComponentHTML) Transformers.t.transformations.get("component")).reset();
            try {
                Map<String, String> map = Requests.queryPOSTToMap(httpExchange);
                //Getting all the tags that have to be retrieved from the text
                List<List<PerformQuery.QueryType>> q = map
                        .keySet()
                        .stream()
                        .map(x -> Arrays.asList(new PerformQuery.QueryType(new ResourceIDStructured(x), PerformQuery.QueryClass.Folk)))
                        .collect(Collectors.toList());

                int id = 0;

                Document doc = DocumentHelper.createDocument();
                Element results = doc.addElement("blitz");

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
                    }
                }

                Document docR = DocumentHelper.createDocument();
                Element resultsR = docR.addElement("div").addAttribute("id", "rIntegrate");



                // if we have some doc here, it means that all the tags were satisfied
                FileSystem.getAllTags().forEach(tag->{
                    String xpath = "//tag[@folk='"+tag+"']";

                    //Creating unique tags from data - not so sure of the Element implementation of dom4j
                    Set<Tag> tagSet = new HashSet<>();
                    {
                        List nodes = doc.selectNodes(xpath);
                        for (Object n : nodes) {
                            Element source = (Element) n;
                            Tag t = new Tag(null, null, null);
                            t.text = source.getText();
                            String folk = source.attributeValue("folk", "No Folksonomy");
                            if (!folk.equals("No Folksonomy")) t.folkPointer = new ResourceIDStructured(folk);
                            String grap = source.attributeValue("graph", "No Graph pointer");
                            if (!grap.equals("No Graph pointer")) t.graphPointer = new ResourceIDStructured(grap);
                            t.source = source;
                            tagSet.add(t);
                        }
                    }

                    if (! tagSet.isEmpty()) {
                        // Starting with some description
                        resultsR.addText(" " + getSemanticsFromJSOJ(tag) + " ");
                        // Generate the elment from the aggregation
                        generateTableFromList(resultsR, tagSet);
                    }

                });


                //Component t = Transformers.dataTransformer(Transformers.readFromFile("CORTONA.xml").get());
                Replies.writeResponse(httpExchange, docR.asXML());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
