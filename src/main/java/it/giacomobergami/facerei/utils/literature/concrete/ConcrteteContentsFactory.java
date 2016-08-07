package it.giacomobergami.facerei.utils.literature.concrete;

import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.concrete.Component;
import it.giacomobergami.facerei.utils.literature.concrete.Tag;
import it.giacomobergami.facerei.utils.literature.concrete.Text;

/**
 * Created by vasistas on 29/07/16.
 */
public class ConcrteteContentsFactory {

    public static Contents factory(String name) {
        if (name.equals("tag")) {
            return new Tag(null,null,null);
        } else if (name.equals("text")) {
            return new Text(null);
        } else if (name.equals("component")) {
            return new Component(null,null);
        } else return null;
    }

}
