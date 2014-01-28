package com.gdpapps.listeditor.Managers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gdpapps.listeditor.R;
import com.gdpapps.listeditor.ListObject.ListComps.ListItem;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListItem> {
	private final Context context;
	private ArrayList<ListItem> values;

	public ListAdapter(Context context, ArrayList<ListItem> values) {
		super(context, R.layout.list_row, values);
		this.context = context;
		this.values = values;
	}

	public void setListData(ArrayList<ListItem> data){
		this.values = data;
	}

	@Override
	public int getCount(){
		return values.size();
	}


	@Override
	public ListItem getItem(int position){
		return values.get(position);
	}

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_row, parent, false);

		TextView tvListName = (TextView) rowView.findViewById(R.id.list_tvListName);
		TextView tvLocation = (TextView) rowView.findViewById(R.id.list_tvLocation);
		TextView tvDateStartEvent = (TextView) rowView.findViewById(R.id.list_tvDateStartEvent);
		TextView tvDateListClose = (TextView) rowView.findViewById(R.id.list_tvDateListClose);
		Button cmdArrow = (Button) rowView.findViewById(R.id.list_cmdArrow);

		Info info = values.get(position).getInfo();
		tvListName.setText(info.getName());
		tvLocation.setText(info.getLocation());
		tvDateStartEvent.setText("Fecha: " + info.getDayOfEvent().getFullDateString());
		tvDateListClose.setText("Cierre Lista: " + info.getDayOfListClose().getFullDateString());
		cmdArrow.setTag(position);
		return rowView;
	}

}
