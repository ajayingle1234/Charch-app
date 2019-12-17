package hardcastle.com.churchapplication.Utils;

import android.net.Uri;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkCalls {
	OkHttpClient client;
	MediaType JSON;

	public NetworkCalls() {
		client = new OkHttpClient();

		client = new OkHttpClient.Builder()
				.connectTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.build();

		JSON = MediaType.parse("application/json; charset=utf-8");
	}


	public String sendPost(String url, ArrayList<Pair> keyValue) {
		String stringResponse = null;
		FormBody.Builder builder = new FormBody.Builder();

		for (Pair value : keyValue) {
			builder.add(value.first.toString(), value.second.toString());
		}

		RequestBody body = builder.build();
		stringResponse = sendValue(url, body);

		return stringResponse;
	}

	public String sendJson(String url, JSONObject object) {
		String stringResponse = null;
		RequestBody body = RequestBody.create(JSON, object.toString());
		stringResponse = sendValue(url, body);
		return stringResponse;
	}

	public String sendJsonArray(String url, JSONArray jsonArray) {
		String stringResponse = null;
		RequestBody body = RequestBody.create(JSON, jsonArray.toString());
		stringResponse = sendValue(url, body);
		return stringResponse;
	}

	public String sendGet(String url, ArrayList<Pair> keyValue) {
		String stringResponse = null;

		Uri builtUri = Uri.parse(url);
		Uri.Builder builder = builtUri.buildUpon();

		for (Pair value : keyValue) {
			builder.appendQueryParameter(value.first.toString(), value.second.toString());
		}
		builtUri = builder.build();

		stringResponse = sendValue(builtUri.getPath());

		return stringResponse;
	}

	public String sendGet(String url) {
		String stringResponse = null;
		stringResponse = sendValue(url);

		return stringResponse;
	}

	private String sendValue(String url) {

		String stringResponse = null;

		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = null;

		try {
			response = client.newCall(request).execute();

			stringResponse = response.body().string();

		} catch (IOException e) {

			stringResponse = e.getMessage();
		}

		return stringResponse;
	}


	private String sendValue(String url, RequestBody body) {

		String stringResponse = null;

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = null;

		try {
			response = client.newCall(request).execute();

			stringResponse = response.body().string();

		} catch (IOException e) {

			stringResponse = e.getMessage();
		}

		return stringResponse;
	}
}
