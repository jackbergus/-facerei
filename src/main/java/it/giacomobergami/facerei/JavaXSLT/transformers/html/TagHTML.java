package it.giacomobergami.facerei.JavaXSLT.transformers.html;

import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by vasistas on 29/07/16.
 */
public class TagHTML extends HTMLTransformer.Transform{

    private int counter;

    public TagHTML(HTMLTransformer chief) {
        super(chief);
        counter = 0;
    }

    @Override
    public String getTargetElementName() {
        return "a";
    }

    @Override
    public void map(Element source, Element dest) {
        String folk = source.attributeValue("folk","No Folksonomy");
        if (!folk.equals("No Folksonomy")) folk = "Folksonomy: " +  folk;
        String grap = source.attributeValue("graph","No Graph pointer");
        if (!grap.equals("No Graph pointer")) {
            try {
                grap = URLEncoder.encode(grap, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                grap = "<b>No Graph pointer</b>";
            }
            grap = "<a href=\"/visit/?file=" + grap + "\">Graph Pointer</a>";
        }
        dest.addAttribute("tabindex","0")
                .addAttribute("class","btn btn-lg btn-danger")
                .addAttribute("role","button")
                .addAttribute("data-toggle","popover")
                .addAttribute("title",folk)
                .addAttribute("data-content",grap)
                .addAttribute("data-trigger","hover")
                .addText(source.getText());
    }
}
