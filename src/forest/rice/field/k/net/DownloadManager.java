package forest.rice.field.k.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadManager {

	/**
	 * URLの取得結果をjsonで返却
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static int getJsonString(String url, StringBuilder json) {
		HttpResponse response;
		try {
			response = getResponse(url);
		} catch (Exception e) {
			return HttpStatus.SC_INTERNAL_SERVER_ERROR;
		}

		int status = response.getStatusLine().getStatusCode();

		if (status != HttpStatus.SC_OK) {
			return status;
		}

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outputStream);
			String data = outputStream.toString();
			json.append(data);
		} catch (IOException e) {
			return HttpStatus.SC_INTERNAL_SERVER_ERROR;
		}

		return status;
	}

	/**
	 * URLの取得結果をBitmapで返却
	 * 
	 * @param url
	 * @param bitmap
	 * @return
	 */
	public static int getBitmap(String url, Bitmap bitmap) {
		HttpResponse response;
		try {
			response = getResponse(url);
		} catch (Exception e) {
			return HttpStatus.SC_INTERNAL_SERVER_ERROR;
		}

		int status = response.getStatusLine().getStatusCode();

		if (status != HttpStatus.SC_OK) {
			return status;
		}

		try {
			bitmap = BitmapFactory.decodeStream(response.getEntity()
					.getContent());
		} catch (Exception e) {
			return HttpStatus.SC_INTERNAL_SERVER_ERROR;
		}
		return status;
	}

	/**
	 * HttpResponse取得
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static HttpResponse getResponse(String url)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;

		response = client.execute(httpGet);

		return response;
	}
}
