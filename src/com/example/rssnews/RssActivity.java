package com.example.rssnews;

import java.util.ArrayList;
import java.util.List;

import org.zeroxlab.widget.AnimationLayout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RssActivity extends Activity implements AnimationLayout.Listener {
	public final static String TAG = "Demo";

	protected ListView mList;
	protected AnimationLayout mLayout;
	public List<Rss> list = new ArrayList<Rss>();
	public RssAdapter adapter;
	public NewFeedsParse newFeed;
	public String link = "http://dantri.com.vn/trangchu.rss";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss);
		mLayout = (AnimationLayout) findViewById(R.id.animation_layout);
		mLayout.setListener(this);
		newFeed = new NewFeedsParse(link);
		list = newFeed.parse();
		mList = (ListView) findViewById(R.id.sidebar_list);
		/*
		 * for (int i = 0; i <= 10; i++) { Rss rss = new Rss();
		 * rss.setContent("content " + i); rss.setDate("12/6/2013 " + i);
		 * rss.setTitle("title " + i); list.add(rss); }
		 */
		adapter = new RssAdapter(this, list);
		//mList.setAdapter(adapter);
	}

	public void onClickContentButton(View v) {
		mLayout.toggleSidebar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rss, menu);
		return true;
	}

	@Override
	public void onSidebarOpened() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSidebarClosed() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onContentTouchedWhenOpening() {
		// TODO Auto-generated method stub
		mLayout.closeSidebar();
		return false;
	}

}
