package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.KComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CommentFetcher {
	// vne link
	public static Vector<KComment> vnefetchComments(String url) {
		Vector<KComment> comments = new Vector<>();
		url = vneGetLinkJson(url);
		System.out.println(url);
		JSONObject jsonComment;
		try {
			jsonComment = JSonReader.readJsonFromUrl(url);
			JSONObject data = jsonComment.getJSONObject("data");
			
			JSONArray items = data.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				JSONObject commentJson = (JSONObject) items.get(i); 
				
				KComment comment = new KComment();
				comment.setContent(commentJson.getString("content"));
				comment.setUser(commentJson.getString("full_name"));
				comments.add(comment);
				
				
				JSONArray subComments = commentJson.getJSONObject("replys").getJSONArray("items");
//			System.out.println(subComments);
				for (int j = 0; j < subComments.length(); j++) {
					JSONObject subCommentJson = (JSONObject) subComments.get(j);
					KComment subComment = new KComment();
					subComment.setContent(subCommentJson.getString("content"));
					subComment.setUser("-"+subCommentJson.getString("full_name"));
					comments.add(subComment);
//					comment.addSubComment(subComment);
				}
				
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	
	// rss link
	public static Vector<KComment> vneRssToComments(String vnUrl){
		Vector<String> links;
		Vector<KComment> comments = new Vector<>();
		try {
			links = rsstolink(vnUrl);
			for (String string : links) {
//				String jsonUrl = getLinkJson(string);
				Vector<KComment> newComments;
				newComments = vnefetchComments(string);
				comments.addAll(newComments);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comments;
	}

	private static String vneGetLinkJson(String vnUrl) {
		// example :vnurl =
		// http://thethao.vnexpress.net/tin-tuc/sea-games-27/boi-thu-hc-vang-viet-nam-vung-vang-thu-hai-2925795.html
		// split : tach cac string duoc phan cach boi dau "-"
		String[] linkElement = vnUrl.split("-");

		// indexcontent = 2925795.html
		String indexContent = linkElement[linkElement.length - 1];
		// index = 2925795.html
		String[] indexHtml = indexContent.split("\\.");
		String index = indexHtml[0];

		String result = "http://interactions.vnexpress.net/index/get?userid=01&objecttype=1&siteid=1000000&limit=10000&objectid="
				+ index;
		return result;
	}
	

	private static Vector<String> rsstolink(String rssUrl)
			throws MalformedURLException, IOException {
		Vector<String> links = new Vector<>();
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(rssUrl);
			doc.getDocumentElement().normalize();

			
			System.out.println("Root element: "
					+ doc.getDocumentElement().getNodeName());

			// loop through each item
			NodeList items = doc.getElementsByTagName("item");
			for (int i = 0; i < items.getLength(); i++) {
				Node n = items.item(i);
				if (n.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element e = (Element) n;
				// get the "title elem" in this item (only one)
				NodeList titleList = e.getElementsByTagName("link");
				Element titleElem = (Element) titleList.item(0);
				// get the "text node" in the title (only one)
				Node titleNode = titleElem.getChildNodes().item(0);
				System.out.println(titleNode.getNodeValue());
				links.add(titleNode.getNodeValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return links;
	}
	/*
	 * zing
	 */
	private static String zingGetIndex(String originUrl){
		String[] linkElement = originUrl.split("-");
		String[] indexHtml = linkElement[linkElement.length - 1].split("\\.");
		String index = indexHtml[0];
		return index+".html";
	}
	
	public static Vector<KComment> zingGetCommentsFromUrl(String originUrl){
		String baseZing = "http://news.zing.vn/zingnews-";
		String zingIndex = zingGetIndex(originUrl);
		Vector<KComment> comments = new Vector<>();
		// ids content
		String rootObject = baseZing+zingIndex;
		
		comments = getFacebookComment(rootObject);
	
		return comments;
	}
	
	public static Vector<KComment> zingGetCommentsFromRSS(String rssUrl){
		Vector<KComment> kComments = new Vector<>();
		try {
			Vector<String> links = rsstolink(rssUrl);
			for (String string : links) {
				
				kComments.addAll(zingGetCommentsFromUrl(string));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return kComments;
	}
	
	//haivl
	
	public static Vector<KComment> haivlGetCommentsFromUrl(String originUrl){
		Vector<KComment> comments = new Vector<>();
		comments = getFacebookComment(originUrl);
		return comments;
	}
	private static Vector<KComment> getFacebookComment(String rootObject){
		String baseFb = "https://graph.facebook.com/comments/?limit=1000&ids=";
	
		Vector<KComment> kComments = new Vector<>();
		String jsonUrl =baseFb+rootObject;
		
		JSONObject jsonComment;
		try {
			jsonComment = JSonReader.readJsonFromUrl(jsonUrl);
			
			JSONObject jsonRoot = jsonComment.getJSONObject(rootObject);
			JSONObject comments = jsonRoot.getJSONObject("comments");
			
			JSONArray items = comments.getJSONArray("data");
			for (int i = 0; i < items.length(); i++) {
				JSONObject commentJson = (JSONObject) items.get(i); 
				
				KComment comment = new KComment();
				comment.setContent(commentJson.getString("message"));
				JSONObject from = commentJson.getJSONObject("from");
				comment.setUser(from.getString("name"));
				kComments.add(comment);
				

				
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kComments;
	}
	
	public static void main(String[] args) throws IOException, JSONException {
//		zingGetCommentsFromUrl("http://news.zing.vn/Dan-truoc-2-ban-U19-Viet-Nam-thua-nguoc-HAGL-46-post380744.html");
		zingGetCommentsFromRSS("http://news.zing.vn/rss/tin-moi.rss");
	}

	
}
