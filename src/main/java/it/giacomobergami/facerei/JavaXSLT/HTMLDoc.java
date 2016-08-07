package it.giacomobergami.facerei.JavaXSLT;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * Created by vasistas on 29/07/16.
 */
public class HTMLDoc {

    public static Document generateHead() {
        Document document = DocumentHelper.createDocument();
        return document;
    }

}
