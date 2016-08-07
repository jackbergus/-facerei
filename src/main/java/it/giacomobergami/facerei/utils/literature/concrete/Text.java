package it.giacomobergami.facerei.utils.literature.concrete;

import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.IText;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public class Text implements IText {

    public String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public String getTextualContent() {
        return content;
    }

    @Override
    public Iterator<Contents> getSibling() {
        return new Iterator<Contents>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Contents next() {
                return null;
            }
        };
    }

    @Override
    public void addText(String toAdd) {
        content += " "+toAdd;
    }

    @Override
    public Node hasUnderlyingNode() {
        return null;
    }

    @Override
    public Contents addElement(String name) {
        return null;
    }
}
