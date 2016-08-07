package it.giacomobergami.facerei.utils.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by vasistas on 29/07/16.
 */
public class Graph<V,L> {

    public ArrayList<Vertex<V,L>> vertices;

    public Graph(int n) {
        vertices = new ArrayList<>(n);
    }

    public Graph() {
        vertices = new ArrayList<>();
    }

    public Vertex<V,L> getVertexById(int id) {
        return vertices.get(id);
    }

    public Vertex<V,L> getVertexByValue(V value) {
        int count = 0;
        for (Vertex<V,L> v : vertices) {
            if (!v.value.equals(value)) count++;
            else return v;
        }
        return null;
    }

    public Vertex<V,L> addVertex(V value) {
        Vertex<V,L> elem = new Vertex<V, L>(value);
        vertices.add(elem);
        elem.id = vertices.size()-1;
        return elem;
    }

    public Vertex<V,L> addEdge(Vertex<V,L> left, L label, Vertex<V,L> right) {
        left.out.add(new OutgoingEdge<>(label, right.id));
        return left;
    }

    public Vertex<V,L> addEdge(V left, L label, V right) {
        return addEdge(getVertexByValue(left),label,getVertexByValue(right));
    }

    public Vertex<V,L> addEdge(int left, L label, int right) {
        return addEdge(getVertexById(left),label,getVertexById(right));
    }

    public Iterator<OutgoingEdge<L,Vertex<V,L>>> getOutgoingByVertex(Vertex<V,L> left) {
        return new Iterator<OutgoingEdge<L, Vertex<V, L>>>() {
            Iterator<OutgoingEdge<L, Integer>> it = left.out.iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
            @Override
            public OutgoingEdge<L, Vertex<V, L>> next() {
                OutgoingEdge<L, Integer> n = it.next();
                return new OutgoingEdge<>(n.label,getVertexById(n.pointer));
            }
        };
    }
    
    public Iterator<OutgoingEdge<L,Vertex<V,L>>> getOutgoingByVertexId(int id) {
        return getOutgoingByVertex(getVertexById(id));
    }

    public Iterator<OutgoingEdge<L,Vertex<V,L>>> getOutgoingByVertexValue(V id) {
        return getOutgoingByVertex(getVertexByValue(id));
    }

    public static void main(String[] args) {
        Graph<String,String> elems = new Graph<>();
        Vertex<String, String> v1 = elems.addVertex("Ciao");
        Vertex<String, String> v2 = elems.addVertex("Bella");
        elems.addEdge(v1,"complimento",v2);
        Iterator<OutgoingEdge<String, Vertex<String, String>>> it = elems.getOutgoingByVertexValue("Ciao");
        while (it.hasNext()) {
            System.out.println(it.next().pointer.value);
        }
    }

}
