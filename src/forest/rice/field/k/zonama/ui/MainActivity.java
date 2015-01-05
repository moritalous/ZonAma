package forest.rice.field.k.zonama.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import forest.rice.field.k.zonama.R;

public class MainActivity extends Activity implements OnQueryTextListener{

	SearchView searchView = null;
	
	Fragment displayFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_view, menu);

		searchView = (SearchView) menu.findItem(R.id.searchView)
				.getActionView();

		searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		if(query == null || query.equals("")) {
			getFragmentManager().beginTransaction()
			.remove(displayFragment)
			.commit();
			
			return true;
		}
		searchView.clearFocus();
		
		displayFragment = ItemListFragment.newInstance(query, "");
		
		 getFragmentManager().beginTransaction()
		 .replace(R.id.container, displayFragment)
		 .commit();
		
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

}
