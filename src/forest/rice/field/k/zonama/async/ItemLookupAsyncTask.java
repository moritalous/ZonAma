package forest.rice.field.k.zonama.async;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import forest.rice.field.k.amazon.ItemLookupRequest;
import forest.rice.field.k.amazon.response.Item;

public class ItemLookupAsyncTask extends
		AsyncTask<Map<String, String>, Void, List<Item>> {

	private ItemLookupAsyncTaskCallBack callBack;

	public ItemLookupAsyncTask(ItemLookupAsyncTaskCallBack callBack) {
		super();
		this.callBack = callBack;
	}

	@Override
	protected List<Item> doInBackground(
			@SuppressWarnings("unchecked") Map<String, String>... params) {

		ItemLookupRequest request = new ItemLookupRequest();

		return request.request(params[0]);
	}

	@Override
	protected void onPostExecute(List<Item> result) {
		super.onPostExecute(result);
		callBack.itemLookupAsyncTaskCallBack(result);
	}

	public interface ItemLookupAsyncTaskCallBack {
		void itemLookupAsyncTaskCallBack(List<Item> result);
	}
}
