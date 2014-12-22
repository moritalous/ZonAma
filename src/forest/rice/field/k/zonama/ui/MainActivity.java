package forest.rice.field.k.zonama.ui;

import android.app.Activity;
import android.os.Bundle;
import forest.rice.field.k.amazon.ItemLookupRequest;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ItemLookupRequest request = new ItemLookupRequest();
				request.request();

			}

		});
		t.start();

	}

}
