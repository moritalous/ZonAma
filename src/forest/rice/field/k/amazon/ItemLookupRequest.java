package forest.rice.field.k.amazon;

import java.util.HashMap;
import java.util.Map;

import com.amazon.advertising.api.SignedRequestsHelper;

import forest.rice.field.k.net.DownloadManager;

public class ItemLookupRequest {
	/*
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "YOUR_ACCESS_KEY_ID_HERE";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "YOUR_SECRET_KEY_HERE";

	private static final String AWS_ASSOCIATE_TAG = "YOUR_ASSOCIATE_TAG_HERE";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	private static final String ENDPOINT = "ecs.amazonaws.jp";

	public void request() {
		/*
		 * Set up the signed requests helper
		 */
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String requestUrl = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemSearch");
		params.put("Keywords", "妖怪ウォッチ");
		params.put("ResponseGroup", "Small");
		params.put("AssociateTag", AWS_ASSOCIATE_TAG);
		params.put("SearchIndex", "Toys");

		requestUrl = helper.sign(params);

		StringBuilder json = new StringBuilder();

		DownloadManager.getJsonString(requestUrl, json);

		System.out.println(json.toString());
	}

}
