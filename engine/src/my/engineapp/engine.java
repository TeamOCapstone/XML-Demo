//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//rand.nextInt(501)		// 0 to 500
package my.engineapp;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import java.util.Random;

public class engine {

  public static void main(String argv[]) {
      String inputpath = argv[0];
      String outputpath = argv[1];
//Input
    try {
	File fXmlFile = new File(inputpath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
			
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
	NodeList nList = doc.getElementsByTagName("input");
			
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;

	//		System.out.println("Category : " + eElement.getAttribute("category"));
	//		System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
	//		System.out.println("Value : " + eElement.getElementsByTagName("value").item(0).getTextContent());
		}
	}
    } catch (Exception e) { e.printStackTrace();}
//Output
	try {
		File fXmlFile = new File(outputpath);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(fXmlFile);

		
		NodeList outputs = doc.getElementsByTagName("output");
		int reportnumber = 1;
		Random rand = new Random();
		
		for (int j = 0; j < outputs.getLength(); j++){
			Node output = doc.getElementsByTagName("output").item(j);
			NodeList outputElements = output.getChildNodes();	//gets all elements of one input

			for (int i = 0; i < outputElements.getLength(); i++) {
			
                            Node node = outputElements.item(i);

				if ("title".equals(node.getNodeName())) {
					node.setTextContent("Report 000"+reportnumber);
					reportnumber++;
		   		}
		   		
				if ("value".equals(node.getNodeName())) {
					node.setTextContent(""+rand.nextInt(501));
		   		}
			}		
		}


		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(fXmlFile);
		transformer.transform(source, result);

		System.out.println("Done");

	} 
	catch (ParserConfigurationException pce) {pce.printStackTrace();} 
	catch (TransformerException tfe) {tfe.printStackTrace();} 
	catch (IOException ioe) {ioe.printStackTrace();} 
	catch (SAXException sae) {sae.printStackTrace();}
  }
//Other Functions
}