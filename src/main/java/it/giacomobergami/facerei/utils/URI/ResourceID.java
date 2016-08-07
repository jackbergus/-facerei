package it.giacomobergami.facerei.utils.URI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by vasistas on 28/07/16.
 */
public class ResourceID<T> {

    public String refName;
    public Queue<T> path;

    @Override
    public boolean equals(Object o) {
        return o instanceof ResourceID && refName.equals(((ResourceID) o).refName) && path.equals(((ResourceID) o).path);
    }

    @Override
    public int hashCode() {
        int result = refName != null ? refName.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
