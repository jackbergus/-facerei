package it.giacomobergami.facerei.JavaXSLT.transformers;

import it.giacomobergami.facerei.utils.literature.concrete.*;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import org.dom4j.Element;

import java.awt.*;
import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public class IteratorFactory {

    public static Iterator getIterator(Object o) {
        if (o instanceof Element) {
            return new NodeIterator((Element)o);
        } else if (o instanceof Component) {
            return ((Component)o).getSibling();
        } else return null;
    }

}
