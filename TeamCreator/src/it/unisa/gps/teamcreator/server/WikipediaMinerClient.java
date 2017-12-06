package it.unisa.gps.teamcreator.server;

import it.unisa.gps.teamcreator.shared.Topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WikipediaMinerClient {
	
	private static final String URL = "http://wikipedia-miner.cms.waikato.ac.nz/services/wikify?responseFormat=json&source=";
	
	public List<Topic> getTopics(String text){
		try {
			String encodedText = URLEncoder.encode(text, "UTF-8");
			JSONObject json = readJsonFromUrl(URL+encodedText);
			JSONArray topics = json.getJSONArray("detectedTopics");
			List<Topic> toReturn = new ArrayList<Topic>();
			for(int i=0; i < topics.length(); i++){
				JSONObject topic = topics.getJSONObject(i);
				Topic t = new Topic();
				t.setId(topic.getInt("id"));
				t.setTitle(topic.getString("title"));
				t.setWeight(topic.getDouble("weight"));
				toReturn.add(t);
			}
			return toReturn;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Topic>();
	}
	
	private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		URL oracle = new URL(url);
		URLConnection yc = oracle.openConnection();
		BufferedReader rd = new BufferedReader(new InputStreamReader(yc.getInputStream(),Charset.forName("UTF-8")));
		try {
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			rd.close();
		}
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
