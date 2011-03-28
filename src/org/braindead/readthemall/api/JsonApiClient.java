package org.braindead.readthemall.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public class JsonApiClient {
	private static String convertStreamToString(InputStream is) {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
				is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			while ((line = bufferReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	private static JSONArray connect(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		JSONArray json = null;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				InputStream inputStream = entity.getContent();
				String result = JsonApiClient.convertStreamToString(inputStream);
				json = new JSONArray(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private static HttpResponse send(String url, List<NameValuePair> paramsList) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept", "application/json");
		HttpResponse httpResponse = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
			httpResponse = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpResponse;
	}
	
	public static List<Link> getLinks(String apiUrl) {
		List<Link> listLinks = new ArrayList<Link>();
		JSONArray result = JsonApiClient.connect(apiUrl);
		if(result != null && result.length() > 0) {
			for(int i=0; i<result.length(); i++) {
				try {
					JSONObject ololz = result.getJSONObject(i);
					listLinks.add(new Link(ololz.getString("link"), ololz.getString("title")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return listLinks;
	}
	
	public static HttpResponse setLinks(String apiUrl, Link link) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("link", link.getLink()));
		paramList.add(new BasicNameValuePair("title", link.getTitle()));
		return JsonApiClient.send(apiUrl, paramList);
	}
}