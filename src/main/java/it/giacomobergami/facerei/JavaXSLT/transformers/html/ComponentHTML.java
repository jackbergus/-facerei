package it.giacomobergami.facerei.JavaXSLT.transformers.html;

import org.dom4j.Element;

/**
 * Created by vasistas on 29/07/16.
 */
public class ComponentHTML extends HTMLTransformer.Transform{

    private int counter;

    public ComponentHTML(HTMLTransformer transformer) {
        super(transformer);
        counter = 0;
    }

    public void reset() {
        counter = 0;
    }

    @Override
    public String getTargetElementName() {
        return "p";
    }

    @Override
    public void map(Element source, Element dest) {
        String id = source.attributeValue("id");
        String name = source.attributeValue("name");
        String abstr = source.attributeValue("abstract");

        dest.addElement("a")
                .addAttribute("href","#")
                .addAttribute("onclick","toggler('newboxes-"+counter+"');")
                .addText("{"+id+"}"+name+": "+abstr);

        Element content = dest.addElement("div")
                .addAttribute("class","newboxes")
                .addAttribute("id","newboxes-"+counter)
                .addAttribute("style","display: none;");
        counter++;
        chief.treeWalk(source,content);
    }
}
