package it.giacomobergami.facerei.utils.iterators;

import java.util.Iterator;

/**
 * Created by vasistas on 28/07/16.
 */
public class ArrayIterator implements Iterator<String> {

    private int i;
    private String[] elems;

    public ArrayIterator(String[] elems) {
        this.i = 0;
        this.elems = elems;
    }

    @Override
    public boolean hasNext() {
        return i<elems.length;
    }

    @Override
    public String next() {
        return elems[i++];
    }
}
