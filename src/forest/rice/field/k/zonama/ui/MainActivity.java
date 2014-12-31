package forest.rice.field.k.zonama.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import forest.rice.field.k.zonama.R;
import forest.rice.field.k.zonama.ui.ItemListFragment.OnFragmentInteractionListener;

public class MainActivity extends Activity implements
		OnFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, ItemListFragment.newInstance("", ""))
					.commit();
		}

	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub

	}

}
