package it.giacomobergami.facerei.utils.graph;

import java.util.HashSet;
import java.util.Objects;

/**
 * Vertex, which is value-identified
 */
public class Vertex<V,L> {

    public HashSet<OutgoingEdge<L,Integer>> out;
    public V value;
    public int id;

    public Vertex(V value) {
        out = new HashSet<>();
        int id = -1;
        this.value = value;
    }

    public Vertex() {
        this(null);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vertex)) return false;
        return value.equals(((Vertex)o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
