package it.giacomobergami.facerei.utils.literature.concrete;

import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.IComponent;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by vasistas on 29/07/16.
 */
public class Component implements IComponent {

    public Set<Contents> siblings;
    public String name;
    public String description;
    public String id;
    public Element source;

    public Component() {
        this(null,null);
    }

    public Component(String name, String description) {
        this.siblings = new LinkedHashSet<>();
        this.name = name;
        this.id = null;
        this.description = description;
    }

    @Override
    public String getTextualContent() {
        StringBuilder sb = new StringBuilder();
        for (Contents c : siblings) sb.append(c.getTextualContent()).append(' ');
        return sb.toString();
    }

    @Override
    public Iterator<Contents> getSibling() {
        return siblings.iterator();
    }

    @Override
    public void addText(String toadd) {
        siblings.add(new Text(toadd));
    }

    @Override
    public Node hasUnderlyingNode() {
        return source;
    }


    @Override
    public Contents addElement(String name) {
        Contents toadd = ConcrteteContentsFactory.factory(name);
        siblings.add(toadd);
        return toadd;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getID() {
        return id;
    }
}
