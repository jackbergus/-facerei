package it.giacomobergami.facerei.utils.HandleXMLS;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by vasistas on 06/08/16.
 */
public class FileSystem {

    public static List<File> getXMLFiles() {
        LinkedList<File> fileLists ;
        //if (fileLists == null) {
            fileLists = new LinkedList<>();
            File folder = new File("data/");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".xml")) {
                    fileLists.add(listOfFiles[i]);
                } else if (listOfFiles[i].isDirectory()) {
                }
            }
        //}
        return fileLists;
    }

    public static Set<File> filterTagByValue(Map<String,String> keyValueMap) {
        //if (tags==null) {
        HashSet<File> tags = new HashSet<>();
        for (File file : getXMLFiles()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("tag");
                boolean exitFile = false;
                boolean matched = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    // Get element
                    Element element = (Element) nodeList.item(i);
                    if (element.hasAttribute("folk")) {
                        String folk = element.getAttribute("folk");
                        String text = element.getTextContent();
                        if (keyValueMap.containsKey(folk)) {
                            if (keyValueMap.get(folk).length() != 0 &&
                                    !text.equals(keyValueMap.get(folk)) && (!matched)) {
                                exitFile = true;
                            } else { exitFile = false; matched = true; }
                        }
                    }
                }
                if (!exitFile) {
                    tags.add(file);
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //}
        return tags;
    }

    public static Collection<String> getAllTags() {
        //if (tags==null) {
        Set<String> tags = new HashSet<>();
            for (File file : getXMLFiles()) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = null;
                try {
                    db = dbf.newDocumentBuilder();
                    Document doc = db.parse(file);
                    doc.getDocumentElement().normalize();
                    NodeList nodeList = doc.getElementsByTagName("tag");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        // Get element
                        Element element = (Element) nodeList.item(i);
                        if (element.hasAttribute("folk")) {
                            tags.add(element.getAttribute("folk"));
                        }
                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        //}
        return tags;
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
