package forest.rice.field.k.zonama.ui;

import java.util.List;

import LruCacheSample.VolleyLruCache;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

import forest.rice.field.k.amazon.response.Item;
import forest.rice.field.k.zonama.R;

public class ItemListAdapter extends ArrayAdapter<Item> {
	private LayoutInflater layoutInflater_;

	private ImageLoader mImageLoader;
	private RequestQueue mQueue;

	public ItemListAdapter(Context context, List<Item> objects) {
		this(context, 0, 0, objects);

		mQueue = Volley.newRequestQueue(getContext());
		mImageLoader = new ImageLoader(mQueue, new VolleyLruCache());
	}

	private ItemListAdapter(Context context, int resource,
			int textViewResourceId, List<Item> objects) {
		super(context, resource, textViewResourceId, objects);

		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater_.inflate(R.layout.fragment_item_list,
					null);
			holder = new ViewHolder();

			holder.imageview = (ImageView) convertView.findViewById(R.id.image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.releaseDate = (TextView) convertView
					.findViewById(R.id.release_date);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Item item = getItem(position);

		ImageListener listener = ImageLoader
				.getImageListener(holder.imageview,
						R.drawable.ic_launcher /* 表示待ち時の画像 */,
						R.drawable.ic_launcher /* エラー時の画像 */);
		mImageLoader.get(item.largeImage, listener); /* URLから画像を取得する */

		holder.name.setText(item.title);
		holder.releaseDate.setText(item.releaseDate);

		return convertView;
	}

	public class ViewHolder {
		ImageView imageview;
		TextView name;
		TextView releaseDate;
	}
}
