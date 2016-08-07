package it.giacomobergami.facerei.utils.literature.concrete;

import it.giacomobergami.facerei.utils.URI.ResourceIDPointer;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.ITag;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public class Tag implements ITag {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (graphPointer != null ? !graphPointer.equals(tag.graphPointer) : tag.graphPointer != null) return false;
        if (folkPointer != null ? !folkPointer.equals(tag.folkPointer) : tag.folkPointer != null) return false;
        return text != null ? text.equals(tag.text) : tag.text == null;

    }

    @Override
    public int hashCode() {
        int result = graphPointer != null ? graphPointer.hashCode() : 0;
        result = 31 * result + (folkPointer != null ? folkPointer.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public Element source;

    public Tag(ResourceIDStructured graphPointer, ResourceIDStructured folkPointer, String text) {
        this.graphPointer = graphPointer;
        this.folkPointer = folkPointer;
        this.text = text;
    }

    public ResourceIDStructured graphPointer;
    public ResourceIDStructured folkPointer;
    public String text;


    @Override
    public String getTextualContent() {
        return text;
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
    public void addText(String toadd) {
        text += " "+toadd;
    }

    @Override
    public Node hasUnderlyingNode() {
        return source;
    }

    @Override
    public Contents addElement(String name) {
        return null;
    }

    @Override
    public ResourceIDStructured getFolkPointer() {
        return folkPointer;
    }

    @Override
    public ResourceIDStructured getGraphPointer() {
        return graphPointer;
    }
}
