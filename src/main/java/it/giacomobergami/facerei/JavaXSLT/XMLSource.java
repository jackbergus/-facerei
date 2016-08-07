package it.giacomobergami.facerei.JavaXSLT;

import it.giacomobergami.facerei.JavaXSLT.transformers.Transformers;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Optional;

/**
 * Created by vasistas on 29/07/16.
 */
public class XMLSource {

    public static final SAXReader xmlReader = new SAXReader();


    public static void main(String args[]) throws DocumentException {
        Document get = Transformers.readFromFile("/Users/vasistas/CORTONA.xml").get();
        Document dst = Transformers.htmlTransformer(get);
        //System.out.println(dst.asXML());

        Component t = Transformers.dataTransformer(get);
        System.out.println(t.getTextualContent());

    }

}
