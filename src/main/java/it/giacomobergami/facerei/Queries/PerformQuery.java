package it.giacomobergami.facerei.Queries;


import it.giacomobergami.facerei.JavaXSLT.transformers.Transformers;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.DomBased.DomMapperBuilder;
import it.giacomobergami.facerei.utils.literature.IComponent;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultText;

import java.util.*;

public class PerformQuery {

    public enum QueryClass {
        Graph,
        Folk
    }

    public static class QueryType {
        public ResourceIDStructured path;
        public QueryClass qc;

        public QueryType(ResourceIDStructured path, QueryClass qc) {
            this.path = path;
            this.qc = qc;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof QueryType)) return false;

            QueryType queryType = (QueryType) o;

            if (path != null ? !path.equals(queryType.path) : queryType.path != null) return false;
            return qc == queryType.qc;

        }

        @Override
        public int hashCode() {
            int result = path != null ? path.hashCode() : 0;
            result = 31 * result + (qc != null ? qc.hashCode() : 0);
            return result;
        }
    }


    /**
     * Analogous of the XPath, but it searches for the tags along the contents of the text.
     * @param query         Conjunctive query
     * @param pos           Position
     * @param document      Element over which perform the query
     * @param result        Partial intermediate results
     * @return
     */
    public static List<List<Contents>> performQuery(List<List<QueryType>> query, int pos, IComponent document, List<Contents> result) {
        int qs = query.size();
        if (qs<=pos) { // If i reached all the query
            if (result.size()>0){
                ArrayList<List<Contents>> toret = new ArrayList<>(1);
                toret.add(result);
                return toret;
            } else return new ArrayList<>();
        } else {
            List<QueryType> first = query.get(pos);
            Set<QueryType> qt = new HashSet<>();
            for (QueryType q : first) {
                switch (q.qc) {
                    case Graph: {
                        Iterator<ResourceIDStructured> it = document.getGraphPointers();
                        while (it.hasNext()) {
                            if (it.next().equals(q.path)) qt.add(q);
                        }
                    }
                    break;
                    case Folk: {
                        Iterator<ResourceIDStructured> it = document.getFolkPointers();
                        while (it.hasNext()) {
                            if (it.next().equals(q.path)) qt.add(q);
                        }
                    }
                    break;
                }
            }
            int countMatches = qt.size();

            List<List<Contents>> resultToRet = new ArrayList<>();

            if (countMatches==first.size()&&query.size()==pos+1) {
                result.add(document);
                resultToRet.add(result);
            } else {
                Iterator<Contents> it = (document.getSibling());

                while (it.hasNext()) {
                    Contents e = it.next();
                    if (e instanceof IComponent) {
                        // If the number of the matches corresponds to the tags at the first level then continue to visit the data
                        if (countMatches == first.size()) {
                            List<Contents> resultToPass = new ArrayList<>(result);
                            if (countMatches == first.size()) resultToPass.add(document);
                            resultToRet.addAll(performQuery(query, pos + 1, ((IComponent) e), resultToPass));

                        } else
                        // If there are no partial results so far to pass and there are no matches, continue to surf the tree
                        if (countMatches != first.size() && result.size() == 0){
                            resultToRet.addAll(performQuery(query, 0, ((IComponent) e), new ArrayList<>()));
                        }
                    }
                }
            }
            return resultToRet;
        }
    }

    public static List<List<Contents>> rankResults(List<List<Contents>> results) {
        if (results.isEmpty()) return results; else {
            ArrayList<List<Contents>> toret = new ArrayList<>(1);
            toret.add(results.get(0));
            return toret;
        }
    }

    /*
    public static void main(String[] args) {
        List<List<QueryType>> q = new ArrayList<>(2);
        {
            q.add(Arrays.asList(new QueryType(new ResourceIDStructured("quam/si"),QueryClass.Folk)));
            q.add(Arrays.asList(new QueryType(new ResourceIDStructured("dumb"),QueryClass.Folk)));
        }
        Component t = Transformers.dataTransformer(Transformers.readFromFile("CORTONA.xml").get());
        List<Contents> l = new ArrayList<>();
        List<List<Contents>> result = performQuery(q,0, t, l);


        Document doc = DocumentHelper.createDocument();
        Element results = doc.addElement("results");
        Document docR = DocumentHelper.createDocument();
        Element resultsR = docR.addElement("results");

        int id = 0;
        for (List<Contents> x : result) {
            Element elem = results.addElement("component").addAttribute("id",(id++)+"");
            x.stream().filter(y->y.hasUnderlyingNode()!=null).forEach(y->{
                Element element = (Element) y.hasUnderlyingNode();
                for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
                    Node node = element.node(i);
                    if ( node instanceof Element ) {
                        String name = node.getName();
                        if (name.equals("tag")) {
                            Element tag = elem.addElement("tag");
                            if (((Element)node).attributeValue("folk")!=null)
                                tag.addAttribute("folk", ((Element)node).attributeValue("folk"));
                            if (((Element)node).attributeValue("graph")!=null)
                                tag.addAttribute("graph",((Element)node).attributeValue("graph"));
                            tag.addText(node.getText());
                        }
                    } else if (node instanceof DefaultText){
                        elem.addText(node.getText());
                    }
                }
            });

            System.out.println(Transformers.htmlTransformer(elem).asXML());

        }




    }
*/

}
