package it.giacomobergami.facerei.JavaXSLT.transformers;

import it.giacomobergami.facerei.JavaXSLT.transformers.data.ComponentData;
import it.giacomobergami.facerei.JavaXSLT.transformers.data.DataTransformer;
import it.giacomobergami.facerei.JavaXSLT.transformers.data.TagData;
import it.giacomobergami.facerei.JavaXSLT.transformers.html.ComponentHTML;
import it.giacomobergami.facerei.JavaXSLT.transformers.html.HTMLTransformer;
import it.giacomobergami.facerei.JavaXSLT.transformers.html.TagHTML;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Optional;

/**
 * Created by vasistas on 29/07/16.
 */
public class Transformers {

    public static Component dataTransformer(Document get) {
        DataTransformer dt = new DataTransformer();
        dt.transformations.put("component",new ComponentData(dt));
        dt.transformations.put("tag",new TagData(dt));
        Component root = new Component();
        dt.transform(get.getRootElement(),root);
        return (Component)root.getSibling().next();
    }


    public static final HTMLTransformer t = new HTMLTransformer();
    static {
        t.transformations.put("component",new ComponentHTML(t));
        t.transformations.put("tag",new TagHTML(t));
    }

    public static Document htmlTransformer(Element getE) {
        //HTMLTransformer t = new HTMLTransformer();
        Document dst = DocumentHelper.createDocument();
        t.transform(getE,dst.addElement("div").addAttribute("class","result"));
        return dst;
    }

    public static Document htmlTransformer(Document get) {
        return htmlTransformer(get.getRootElement());
    }

    public static Document xmlView(Element getE) {
        Document dst = null;
        try {
            String text = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\n" +
                    "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"> </script>\n" +
                    "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js\"> </script>\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css\" /><style>\n" +
                    ".newboxes{\n" +
                    "  display:none;\n" +
                    "  border: 5px solid;\n" +
                    "  padding-left : 20px;\n" +
                    "}\n" +
                    "  </style>\n" +
                    "          <script language=\"javascript\">\n" +
                    "          function toggler(divId) {\n" +
                    "              $(\"#\" + divId).toggle();\n" +
                    "\n" +
                    "          }\n" +
                    "          $(function() {\n" +
                    "              $(document).popover({\n" +
                    "                  html : true,\n" +
                    "                    selector: '[data-toggle=popover]',\n" +
                    "                    trigger: 'focus',\n" +
                    "                    content: function() {\n" +
                    "                      var content = $(this).attr(\"data-popover-content\");\n" +
                    "                      return $(content).children(\".popover-body\").html();\n" +
                    "                    }" +
                    "              });\n" +
                    "          });\n" +
                    "          </script>\n" +
                    "\n" +
                    "\n" +
                    "  </head>\n" +
                    "<body>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "      </body>\n" +
                    "</html>\n";
            dst = DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        t.transform(getE, (Branch) dst.selectSingleNode("/html/body"));
        return dst;
    }

    public static final SAXReader xmlReader = new SAXReader();

    public static Optional<Document> readFromFile(String file) {
        try {
            return Optional.of(xmlReader.read(new File(file)));
        } catch (DocumentException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



}
