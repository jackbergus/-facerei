package it.giacomobergami.facerei.JavaXSLT;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultText;

import java.util.HashMap;

/**
 * Created by vasistas on 29/07/16.
 */
public abstract class IXMLTransformer<HDest,Dest> {

    public static abstract class ITransform<Dest> {
        protected IXMLTransformer chief;
        public ITransform(IXMLTransformer chief) {
            this.chief = chief;
        }
        public abstract String getTargetElementName();
        public abstract void map(Element source, Dest dest);
    }

    /**
     *
     * @param source
     * @param destination: could be either a Document or an Element
     */
    public  abstract  void transform(Element source, HDest destination) ;
    public abstract void addToHdest(Node original, HDest whereToAdd);

    public void treeWalk(Element element, HDest target) {
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof Element ) {
                transform( (Element) node , target);
            } else if (node instanceof DefaultText){
                addToHdest(node, target);
                // target.add(new DefaultText(node.getText())); // Normal text undergoes no transformations
            }
        }
    }

}
