package com.example.rssnews;

import java.util.ArrayList;
import java.util.List;

import android.R.array;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssAdapter extends BaseAdapter {
	Context context;
	List<Rss> arrayslist = new ArrayList<Rss>();

	public RssAdapter(Context context, List<Rss> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arrayslist = list;

	}

	static class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayslist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return (Rss) arrayslist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertview, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertview == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertview = inflater.inflate(R.layout.list_content, null);
		}
		final TextView rsstitle = (TextView) convertview
				.findViewById(R.id.rsstitle);
		rsstitle.setText(arrayslist.get(arg0).getTitle().toString());
		final TextView rssdate = (TextView) convertview
				.findViewById(R.id.rssdate);
		rssdate.setText(arrayslist.get(arg0).getDate().toString());
		final TextView rsscontent = (TextView) convertview
				.findViewById(R.id.rsscontent);
		rsscontent.setText(arrayslist.get(arg0).getContent().toString());
		return convertview;
	}
}
