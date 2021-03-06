package forest.rice.field.k.zonama.ui;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import forest.rice.field.k.amazon.response.Item;
import forest.rice.field.k.amazon.response.Item.CalendarDataException;
import forest.rice.field.k.zonama.async.ItemLookupAsyncTask;
import forest.rice.field.k.zonama.async.ItemLookupAsyncTask.ItemLookupAsyncTaskCallBack;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ItemListFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ItemListFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class ItemListFragment extends ListFragment implements
		ItemLookupAsyncTaskCallBack {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	List<Item> itemList = null;

	SearchView searchView = null;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ItemListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ItemListFragment newInstance(String param1, String param2) {
		ItemListFragment fragment = new ItemListFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ItemListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("Keywords", mParam1);
		params.put("SearchIndex", "All");

		ItemLookupAsyncTask asyncTask = new ItemLookupAsyncTask(this);
		asyncTask.execute(params);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		try {
			Item item = itemList.get(position);
			startActivity(item.createCalendarIntent());
		} catch (CalendarDataException e) {
		}
	}

	@Override
	public void itemLookupAsyncTaskCallBack(List<Item> result) {
		itemList = result;
		ItemListAdapter adapter = new ItemListAdapter(getActivity(), result);
		setListAdapter(adapter);
	}
}
