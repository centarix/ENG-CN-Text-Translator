package net.centarix.translationpackage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;
import org.w3c.dom.*;

/*Reference: http://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html */

public class XMLParser {
	public String result = "";

	public XMLParser(InputStream is) {
		try {
			//Get the DOM Builder Factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			//Get the DOM Builder
			DocumentBuilder builder;
			
			builder = factory.newDocumentBuilder();
			//Load and Parse the XML document
			//document contains the complete XML as a Tree.
			Document document =	builder.parse(is);

			//Iterating through the nodes and extracting the data.
			Node PNode = document.getDocumentElement().getParentNode();
			Node node = null;
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				      //We have encountered an <employee> tag.
				      node = nodeList.item(i);
			}
			
			//Obtain translated text from node
			result = node == null ? "" : node.getNodeValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
