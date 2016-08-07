package it.giacomobergami.facerei.JavaXSLT.transformers.data;

import it.giacomobergami.facerei.utils.literature.concrete.Component;
import it.giacomobergami.facerei.utils.literature.Contents;
import org.dom4j.Element;

/**
 * Created by vasistas on 29/07/16.
 */
public class ComponentData extends DataTransformer.DataTransform {

    private int counter;

    public ComponentData(DataTransformer transformer) {
        super(transformer);
        counter = 0;
    }

    @Override
    public String getTargetElementName() {
        return "component";
    }

    @Override
    public void map(Element source, Contents dest) {
        Component t = (Component)dest;
        t.name = source.attributeValue("name");
        t.description = source.attributeValue("abstract");
        t.id = source.attributeValue("id");
        counter++;
        t.source = source;
        chief.treeWalk(source,t);
    }
}
