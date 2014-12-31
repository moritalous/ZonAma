package forest.rice.field.k.amazon.response;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Item {

	public String detailPageURL = "";
	public String largeImage = "";
	public String title = "";
	public String releaseDate = "";

	public Item(Node item) {

		NodeList childNodes = item.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element element = (Element) childNodes.item(i);

				System.out.println(element.getTagName());
				
				switch (element.getTagName()) {
				case "DetailPageURL":
					detailPageURL = element.getFirstChild().getNodeValue();
					break;
				case "LargeImage":
					parseLargeImage(element);
					break;
				case "ItemAttributes":
					parseItemAttributes(element);
					break;
				}

			}
		}
	}

	private void parseLargeImage(Element element) {
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element childElement = (Element) childNodes.item(i);

				switch (childElement.getTagName()) {
				case "URL":
					largeImage = childElement.getFirstChild().getNodeValue();
					
					break;
				}
			}
		}
	}

	private void parseItemAttributes(Element element) {
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element childElement = (Element) childNodes.item(i);

				switch (childElement.getTagName()) {
				case "ReleaseDate":
					releaseDate = childElement.getFirstChild().getNodeValue();
					break;
				case "Title":
					title = childElement.getFirstChild().getNodeValue();
					break;
					
				}
			}
		}
	}

}
