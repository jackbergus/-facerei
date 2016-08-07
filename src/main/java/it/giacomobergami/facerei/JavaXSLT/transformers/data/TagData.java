package it.giacomobergami.facerei.JavaXSLT.transformers.data;

import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.concrete.Tag;
import org.dom4j.Element;

/**
 * Created by vasistas on 29/07/16.
 */
public class TagData extends DataTransformer.DataTransform{

    public TagData(DataTransformer chief) {
        super(chief);
    }

    @Override
    public String getTargetElementName() {
        return "tag";
    }

    @Override
    public void map(Element source, Contents dest) {
        Tag t = (Tag)dest;
        t.text = source.getText();
        String folk = source.attributeValue("folk","No Folksonomy");
        if (!folk.equals("No Folksonomy")) t.folkPointer = new ResourceIDStructured(folk);
        String grap = source.attributeValue("graph","No Graph pointer");
        if (!grap.equals("No Graph pointer")) t.graphPointer = new ResourceIDStructured(grap);
        t.source = source;
    }
}
