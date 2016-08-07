package it.giacomobergami.facerei.utils.literature.DomBased;

import it.giacomobergami.facerei.JavaXSLT.transformers.NodeIterator;
import it.giacomobergami.facerei.utils.iterators.FilterIterator;
import it.giacomobergami.facerei.utils.URI.ResourceIDStructured;
import it.giacomobergami.facerei.utils.literature.Contents;
import it.giacomobergami.facerei.utils.literature.IComponent;
import it.giacomobergami.facerei.utils.literature.ITag;
import it.giacomobergami.facerei.utils.literature.IText;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultText;

import java.util.Iterator;

/**
 * Created by vasistas on 29/07/16.
 */
public class DomMapperBuilder {

    public static Contents fromNode(Node dom4j) {
        String name = dom4j.getName();
        boolean isText = (dom4j instanceof DefaultText);
        if (isText) {
            return new IText() {
                @Override
                public String getTextualContent() {
                    return dom4j.getText();
                }

                @Override
                public Iterator<Contents> getSibling() {
                    return new Iterator<Contents>() {
                        @Override
                        public boolean hasNext() {
                            return false;
                        }

                        @Override
                        public Contents next() {
                            return null;
                        }
                    };
                }

                @Override
                public void addText(String toadd) {
                    dom4j.setText(dom4j.getText()+" "+toadd);
                }

                @Override
                public Node hasUnderlyingNode() {
                    return dom4j;
                }

                @Override
                public Contents addElement(String name) {
                    return null;
                }
            };
        } else {
            Element e = (Element)dom4j;
            if (name.equals("component")) {
                return new IComponent() {
                    @Override
                    public String getName() {
                        return ((Element) dom4j).attributeValue("name");
                    }

                    @Override
                    public String getDescription() {
                        return ((Element) dom4j).attributeValue("abstract");
                    }

                    @Override
                    public String getID() {
                        return ((Element) dom4j).attributeValue("id");
                    }

                    @Override
                    public String getTextualContent() {
                        StringBuilder sb = new StringBuilder();
                        Iterator<Contents> it = getSibling();
                        while (it.hasNext()) {
                            sb.append(it.next().getTextualContent());
                        }
                        return sb.toString();
                    }

                    @Override
                    public Iterator<Contents> getSibling() {
                        return new FilterIterator<>(new NodeIterator((Element)dom4j),x->true,DomMapperBuilder::fromNode);
                    }

                    @Override
                    public void addText(String toadd) {
                        ((Element) dom4j).addText(toadd);
                    }

                    @Override
                    public Node hasUnderlyingNode() {
                        return dom4j;
                    }

                    @Override
                    public Contents addElement(String name) {
                        return null;
                    }
                };
            } else if (name.equals("tag")) {
                return new ITag() {
                    @Override
                    public ResourceIDStructured getFolkPointer() {
                        String url = ((Element) dom4j).attributeValue("folk");
                        return url==null ? null : new ResourceIDStructured(url);
                    }

                    @Override
                    public ResourceIDStructured getGraphPointer() {
                        String url = ((Element) dom4j).attributeValue("graph");
                        return url==null ? null : new ResourceIDStructured(url);
                    }


                    @Override
                    public String getTextualContent() {
                        return dom4j.getText();
                    }

                    @Override
                    public Iterator<Contents> getSibling() {
                        return new Iterator<Contents>() {
                            @Override
                            public boolean hasNext() {
                                return false;
                            }
                            @Override
                            public Contents next() {
                                return null;
                            }
                        };
                    }

                    @Override
                    public void addText(String toadd) {
                        ((Element) dom4j).addText(toadd);
                    }

                    @Override
                    public Node hasUnderlyingNode() {
                        return dom4j;
                    }

                    @Override
                    public Contents addElement(String name) {
                        return null;
                    }
                };
            }
        }
        return null;
    }

}
