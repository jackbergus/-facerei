package it.giacomobergami.facerei.JavaXSLT.transformers.html;

import it.giacomobergami.facerei.JavaXSLT.IXMLTransformer;
import org.dom4j.*;
import org.dom4j.tree.DefaultText;

import java.util.HashMap;

/**
 * Created by vasistas on 29/07/16.
 */
public class HTMLTransformer extends IXMLTransformer<Branch,Element> {

    public static abstract class Transform extends ITransform<Element> {
        public Transform(HTMLTransformer chief) {
            super(chief);
        }
    }

    public HashMap<String,Transform> transformations;

    public HTMLTransformer() {
        transformations = new HashMap<>();
    }

    /**
     *
     * @param source
     * @param destination: could be either a Document or an Element
     */
    @Override
    public void transform(Element source, Branch destination) {
        Transform t = transformations.get(source.getName());
        t.map(source,destination.addElement(t.getTargetElementName()));
    }

    @Override
    public void addToHdest(Node original, Branch whereToAdd) {
        whereToAdd.add(new DefaultText(original.getText()));
    }


}
