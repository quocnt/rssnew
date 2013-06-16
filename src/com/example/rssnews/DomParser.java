package com.example.rssnews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

public class DomParser {
	// XML node keys
	static final String KEY_ITEM = "item"; // root node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";

	public InputStream is = null;
	public String xml = "";
	public Context mContext;
	public ArrayList<HashMap<String, String>> menuItems;

	// constructor
	public DomParser(Context mContext) {
		this.mContext = mContext;
	}

	public String getXmlFromAssert() {
		xml = "";
		try {
			is = mContext.getAssets().open("foods.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			System.out.print(sb);
			xml = sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return XML
		return xml;
	}

	public ArrayList<HashMap<String, String>> getMenus() {
		String xml = this.getXmlFromAssert(); // getting XML
		Document doc = this.getDomElement(xml); // getting DOM element
		menuItems = new ArrayList<HashMap<String, String>>();
		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, this.getValue(e, KEY_ID));
			map.put(KEY_NAME, this.getValue(e, KEY_NAME));
			map.put(KEY_COST, "Rs." + this.getValue(e, KEY_COST));
			map.put(KEY_DESC, this.getValue(e, KEY_DESC));

			// adding HashList to ArrayList
			menuItems.add(map);
		}
		return menuItems;
	}

	/**
	 * Getting XML DOM element
	 * 
	 * @param XML
	 *            string
	 * */
	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}

	/**
	 * Getting node value
	 * 
	 * @param elem
	 *            element
	 */
	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild();child != null;child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Getting node value
	 * 
	 * @param Element
	 *            node
	 * @param key
	 *            string
	 * */
	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
}
