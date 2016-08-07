package it.giacomobergami.facerei.utils.trees;

import java.util.Iterator;
import java.util.Queue;

/**
 * Created by vasistas on 28/07/16.
 */
public interface ITree<T> {

    /**
     * Returns the value belonging to the node
     * @return
     */
    T getValue();
    T setValue(T newValue);

    Iterable<ITree<T>> getSiblings();
    int getSiblingsSize();

    ITree<T> addSibling(ITree<T> elem);
    default ITree<T> addSibling(T elem) {
        return addSibling(Tree.leaf(elem));
    }

    boolean addPath(T[] array);

    default ITree<T> getFromPath(Iterator<Integer> elems) {
        if (!elems.hasNext()) return this;
        else {
            int val = elems.next();
            if (getSiblingsSize()<val) return null;
            else {
                Iterator<ITree<T>> it = getSiblings().iterator();
                for (int i=0; i<val; i++) it.next();
                return (it.next().getFromPath(elems));
            }
        }
    }


}
