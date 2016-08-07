package it.giacomobergami.facerei.JavaXSLT.transformers;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public class NodeIterator implements Iterator<Node> {

    Element main;
    int max, count;

    public NodeIterator(Element main) {
        this.main = main;
        max = main.nodeCount();
        count=0;
    }

    @Override
    public boolean hasNext() {
        return count<max;
    }

    @Override
    public Node next() {
        return main.node(count++);
    }

}
