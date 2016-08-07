package it.giacomobergami.facerei.utils.URI;

import java.util.LinkedList;

/**
 * Created by vasistas on 28/07/16.
 */
public class ResourceIDStructured extends ResourceID<String> {

    public ResourceIDStructured(String path) {
        String[] elems = path.split("/");
        refName = elems[0];
        this.path = new LinkedList<>();
        for (int i=1; i<elems.length; i++) this.path.add(elems[i]);
    }

}
