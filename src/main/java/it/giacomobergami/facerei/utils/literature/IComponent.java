package it.giacomobergami.facerei.utils.literature;

import it.giacomobergami.facerei.utils.iterators.FilterIterator;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.concrete.Tag;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public interface IComponent extends Contents {
    String getName();
    String getDescription();
    String getID();
    default Iterator<ResourceIDStructured> getFolkPointers() {
        return new FilterIterator<>(getSibling(), x -> x instanceof Tag && ((Tag) x).folkPointer != null, x -> ((Tag) x).folkPointer);
    }
    default Iterator<ResourceIDStructured> getGraphPointers() {
        return new FilterIterator<>(getSibling(), x -> x instanceof Tag && ((Tag) x).graphPointer != null, x -> ((Tag) x).graphPointer);
    }
    default boolean matchesFolk(ResourceIDStructured ptr) {
        return  getFolkPointers().hasNext();
    }
    default boolean matchesGraph(ResourceIDStructured ptr) {
        return getGraphPointers().hasNext();
    }
}
