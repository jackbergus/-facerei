package it.giacomobergami.facerei.utils.iterators;

import it.giacomobergami.facerei.JavaXSLT.transformers.Transformers;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import it.giacomobergami.facerei.utils.literature.concrete.Tag;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Iterates over a collection
 */
public class FilterIterator<S,D> implements Iterator<D> {

    private Iterator<S> priv;
    private Predicate<S> prop;
    private final Function<S, D> function;
    private S current;
    private boolean hasNext;

    public FilterIterator(Iterator<S> priv, Predicate<S> prop, Function<S,D> function) {
        this.priv = priv;
        this.prop = prop;
        this.function = function;
        hasNext = false;
        boolean found = false;
        while (priv.hasNext() && (!found)) {
            current = priv.next();
            if (prop.test(current)) {
                found = true;
                hasNext = true;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public D next() {
        hasNext = false;
        boolean found = false;
        D toret = function.apply(current);
        while (priv.hasNext() && (!found)) {
            current = priv.next();
            if (prop.test(current)) {
                found = true;
                hasNext = true;
            }
        }
        return toret;
    }

    /*
    public static void main(String args[]) {
        Component t = Transformers.dataTransformer(Transformers.readFromFile("/Users/vasistas/CORTONA.xml").get());
        Iterator<ResourceIDStructured> it = t.getFolkPointers();
        while (it.hasNext()) {
            System.out.println(it.next().refName);
        }
        it = t.getGraphPointers();
        while (it.hasNext()) {
            System.out.println(it.next().refName);
        }
    }*/


}
