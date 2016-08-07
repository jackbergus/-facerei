package it.giacomobergami.facerei.utils.URI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by vasistas on 28/07/16.
 */
public class ResourceIDPointer extends ResourceID<Integer> {

    public ResourceIDPointer(String path) {
        String[] elems = path.split("/");
        refName = elems[0];
        this.path = new LinkedList<>();
        for (int i=1; i<elems.length; i++) this.path.add(Integer.valueOf(elems[i]));
    }

    public ResourceIDPointer(String resource, Queue<Integer> toBuildUp) {
        this.refName = resource;
        this.path = toBuildUp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(refName).append("/");
        Iterator<Integer> it = path.iterator();
        while (it.hasNext()) {
            int next = it.next();
            sb.append(next);
            if (it.hasNext()) sb.append('/');
        }
        return sb.toString();
    }

}
