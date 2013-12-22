package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JSonReader {
	
	// ham chuyen tu url sang json object
	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			
			System.out.println(jsonText);
			
			JSONObject json = new JSONObject(jsonText);
			
			return json;
		} finally {
			is.close();
		}
	}
	
	public static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		while(true){
			if(sb.charAt(0)!='{'){
				sb.replace(0, 1, "");
			}else{
				break;
			}
		}
		while(true){
			if(sb.charAt(sb.length()-1)!='}'){
				sb.replace(sb.length()-1, sb.length(), "");
			}else{
				break;
			}
		}
		
		return sb.toString();
	}
	
	public static String readContent(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}

		
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		new JSonReader().readJsonFromUrl("http://interactions.vnexpress.net/index/get?userid=0&objectid=2925795&objecttype=1&siteid=1000000&limit=5");
	}
}
