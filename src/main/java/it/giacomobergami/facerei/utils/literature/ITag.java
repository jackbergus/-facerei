package it.giacomobergami.facerei.utils.literature;

import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.IText;

/**
 * Created by vasistas on 29/07/16.
 */
public interface ITag extends IText{
    ResourceIDStructured getFolkPointer();
    ResourceIDStructured getGraphPointer();
}
