package Main;

import java.io.File;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
    public static void readXmlFile(String filePath, Map<String, String> dictionary) throws Exception {
        File file = new File(filePath);
        Document document = null;
        try {
            
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Use the factory to create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Use the builder to parse the input file and create a Document
            document = builder.parse(file);

            // Get the root element of the document
            Element root = document.getDocumentElement();
            // Get all child elements of the root element
            NodeList records = root.getElementsByTagName("record");

            // Iterate over the record elements
            for (int i = 0; i < records.getLength(); i++) {
                Node recordNode = records.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element record = (Element) recordNode;

                    // Get the word element
                    Element wordElement = (Element) record.getElementsByTagName("word").item(0);
                    String word = wordElement.getTextContent();

                    // Get the meaning element
                    Element meaningElement = (Element) record.getElementsByTagName("meaning").item(0);
                    String meaning = meaningElement.getTextContent();

                    // Do something with the word and meaning data
                    dictionary.put(word, meaning);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to read XML file: " + e.getMessage());
            throw e;
        }
    }
}
