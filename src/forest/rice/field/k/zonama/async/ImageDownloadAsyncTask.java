package forest.rice.field.k.zonama.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imageView;
	
	public ImageDownloadAsyncTask(ImageView imageView) {
		this.imageView = imageView;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {

		Bitmap bitmap = null;
		URL url;
        InputStream istream;
        try {
            //画像のURLを直うち
            url = new URL(params[0]);
            //インプットストリームで画像を読み込む
            istream = url.openStream();
            //読み込んだファイルをビットマップに変換
            bitmap = BitmapFactory.decodeStream(istream);
            //インプットストリームを閉じる
            istream.close();
        } catch (IOException e) {
        }
		
        return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		
		imageView.setImageBitmap(result);
	}

}
