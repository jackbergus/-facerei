package it.giacomobergami.facerei.utils.literature;

import it.giacomobergami.facerei.IResults;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public interface Contents extends IResults<Contents> {
    String getTextualContent();
    Iterator<Contents> getSibling();
    void addText(String toadd);
    Node hasUnderlyingNode();
}
