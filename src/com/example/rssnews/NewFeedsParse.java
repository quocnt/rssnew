package com.example.rssnews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class NewFeedsParse {
	private InputStream urlStream;
	private XmlPullParserFactory factory;
	private XmlPullParser parser;

	private List<Rss> rssFeedList;
	private Rss rssFeed;

	private String urlString;
	private String tagName;

	private String title;
	private String link;
	private String description;
	private String category;
	private String pubDate;
	private String guid;
	private String feedburner;
	private String image;

	public static final String ITEM = "item";
	public static final String CHANNEL = "channel";

	public static final String TITLE = "title";
	public static final String LINK = "link";
	public static final String DESCRIPTION = "description";
	public static final String CATEGORY = "category";
	public static final String PUBLISHEDDATE = "pubdate";
	public static final String GUID = "guid";

	public NewFeedsParse(String urlString) {
		this.urlString = urlString;
	}

	public static InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		InputStream stream = null;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		int responseCode = conn.getResponseCode();
		Log.d("RESPONCODE", "" + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			stream = conn.getInputStream();
		}
		return stream;
	}

	public List<Rss> parse() {
		try {
			int count = 0;
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			factory.setNamespaceAware(true);
			urlStream = downloadUrl(urlString);
			parser.setInput(urlStream, "UTF-8");
			
			int eventType = parser.getEventType();

			boolean done = false;
			rssFeed = new Rss();
			rssFeedList = new ArrayList<Rss>();
			while (eventType != XmlPullParser.END_DOCUMENT && !done) {
				tagName = parser.getName();

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					Log.e("OK", "Start document");
					break;
				case XmlPullParser.START_TAG:
					if (tagName.equals(ITEM)) {
						rssFeed = new Rss();
					}
					if (tagName.equals(TITLE)) {
						title = parser.nextText().toString();
						Log.e("title", title);
					}
					if (tagName.equals(LINK)) {
						link = parser.nextText().toString();
						Log.e("link", link);
					}	
					if (tagName.equals(DESCRIPTION)) {
						description = parser.nextText().toString();
						// image = description.substring(
						// description.indexOf("src='") + 5,
						// description.indexOf("'/>"));
						image = "";
					}
					if (tagName.equals(PUBLISHEDDATE)) {
						pubDate = parser.nextText().toString();
						Log.e("pubDate", pubDate);
					}
					break;
				case XmlPullParser.END_TAG:
					if (tagName.equals(CHANNEL)) {
						done = true;
					} else if (tagName.equals(ITEM)) {
						rssFeed = new Rss(title, description, pubDate, image);
						rssFeedList.add(rssFeed);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			Log.e("LOI", "LOI CHO NAY");
			e.printStackTrace();
		}
		return rssFeedList;
	}
}
