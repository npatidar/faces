package com.faceoffaerie.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.PlistInfo;

import java.io.InputStream;
import java.util.ArrayList;

public class SavedFaerieListAdapter extends BaseAdapter {
	private ListView listView;
	private LayoutInflater inflater = null;
	private ArrayList<PlistInfo> faerieList = null;
	private Activity activity = null;

	public SavedFaerieListAdapter(Activity context, ArrayList<PlistInfo> itemList,
								  ListView listView) {
		this.listView = listView;
		this.faerieList = itemList;
		this.activity = context;

	}

	@Override
	public int getCount() {
		return faerieList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final CellViewCache cache;
		if (view == null) {
			inflater = LayoutInflater.from(activity);
			view = inflater.inflate(R.layout.activity_faerie_item, parent, false);
			cache = new CellViewCache(view);
			view.setTag(cache);
		} else {
			cache = (CellViewCache) view.getTag();
		}

		PlistInfo info = faerieList.get(position);
		cache.getNameTextView().setText(info.name);
		cache.getDateTextView().setText(info.date);
		Log.e("TAAG", info.name);
		try {
			Log.e("TAAG1", info.name);
			InputStream ims = activity.getAssets().open(String.format("thmbx%s.png", info.name));
			Log.e("TAAG2", info.image);
			Drawable d = Drawable.createFromStream(ims, null);
			Log.e("SFLA", info.image);
			cache.getFaerieImageView().setImageDrawable(d);

		} catch (Exception e) {}
		return view;
	}

	class CellViewCache {
		private View view;
		private ImageView faerieImageView = null;
		private TextView nameTextView = null;
		private TextView dateTextView = null;
		
		public CellViewCache(View view) {
			this.view = view;
		}
		
		public ImageView getFaerieImageView() {
			if (faerieImageView == null) {
				faerieImageView = (ImageView) view.findViewById(R.id.faerieImageView);
			}
			return faerieImageView;
		}
		public TextView getNameTextView() {
			if (nameTextView == null) {
				nameTextView = (TextView) view.findViewById(R.id.nameTextView);
			}
			return nameTextView;
		}
		public TextView getDateTextView() {
			if (dateTextView == null) {
				dateTextView = (TextView) view.findViewById(R.id.dateTextView);
			}
			return dateTextView;
		}
	}
}