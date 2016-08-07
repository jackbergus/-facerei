package it.giacomobergami.facerei.utils.graph;

/**
 * Created by vasistas on 29/07/16.
 */
public class OutgoingEdge<L,VRepresentation> {

    public  L label; // The semantics associated to the outgoing edge
    public  VRepresentation pointer; // The id pointing to the next vertex

    public OutgoingEdge(L label, VRepresentation pointer) {
        this.label = label;
        this.pointer = pointer;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OutgoingEdge)) return false;
        OutgoingEdge right = (OutgoingEdge<L,VRepresentation>)o;
        return label.equals(right.label) && pointer == (right.pointer);
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + pointer.hashCode();
        return result;
    }
}
