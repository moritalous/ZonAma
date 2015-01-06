package forest.rice.field.k.amazon;

import static forest.rice.field.k.amazon.AmazonConstants.AWS_ACCESS_KEY_ID;
import static forest.rice.field.k.amazon.AmazonConstants.AWS_ASSOCIATE_TAG;
import static forest.rice.field.k.amazon.AmazonConstants.AWS_SECRET_KEY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amazon.advertising.api.SignedRequestsHelper;

import forest.rice.field.k.amazon.response.Item;

public class ItemLookupRequest {

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	private static final String ENDPOINT = "ecs.amazonaws.jp";

	public List<Item> request(Map<String, String> params) {
		/*
		 * Set up the signed requests helper
		 */
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			return null;
		}

		String requestUrl = null;
		requestUrl = helper.sign(addAmazonKeys(params));

		List<Item> itemList = new ArrayList<Item>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xml = dBuilder.parse(requestUrl);

			NodeList itemNodeList = xml.getElementsByTagName("Item");
			for (int i = 0; i < itemNodeList.getLength(); i++) {
				Item item = new Item(itemNodeList.item(i));
				itemList.add(item);
			}
		} catch (Exception e) {
		}

		return itemList;
	}

	private Map<String, String> addAmazonKeys(Map<String, String> params) {
		Map<String, String> returnMap = new HashMap<String, String>(params);

		returnMap.put("Service", "AWSECommerceService");
		returnMap.put("Operation", "ItemSearch");
		returnMap.put("ResponseGroup", "Small, Images, ItemAttributes");
		returnMap.put("AssociateTag", AWS_ASSOCIATE_TAG);

		return returnMap;
	}

}
