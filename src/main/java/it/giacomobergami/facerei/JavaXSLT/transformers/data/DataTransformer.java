package it.giacomobergami.facerei.JavaXSLT.transformers.data;

import it.giacomobergami.facerei.JavaXSLT.IXMLTransformer;
import it.giacomobergami.facerei.utils.literature.Contents;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.HashMap;

/**
 * Created by vasistas on 29/07/16.
 */
public class DataTransformer extends IXMLTransformer<Contents,Contents> {

    public static abstract class DataTransform extends ITransform<Contents> {
        public DataTransform(DataTransformer chief) {
            super(chief);
        }
    }

    public HashMap<String,DataTransform> transformations;

    public DataTransformer() {
        transformations = new HashMap<>();
    }

    @Override
    public void transform(Element source, Contents destination) {
        DataTransform t = transformations.get(source.getName());
        t.map(source,destination.addElement(t.getTargetElementName()));
    }

    @Override
    public void addToHdest(Node original, Contents whereToAdd) {
        whereToAdd.addText(original.getText());
    }

}
