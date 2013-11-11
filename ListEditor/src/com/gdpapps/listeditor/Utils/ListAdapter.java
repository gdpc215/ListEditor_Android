package com.gdpapps.listeditor.Utils;

import java.util.ArrayList;
import com.gdpapps.listeditor.R;
import com.gdpapps.listeditor.Objects.List;
import android.content.Context;
import android.view.*;
import android.widget.*;
import com.gdpapps.listeditor.Utils.*;
import com.gdpapps.listeditor.Objects.*;

public class ListAdapter extends ArrayAdapter<List> {
	private final Context context;
	private ArrayList<List> values;

	public ListAdapter(Context context, ArrayList<List> values) {
		super(context, R.layout.list_row, values);
		this.context = context;
		this.values = values;
	}

	public void setListData(ArrayList<List> data){
		this.values = data;
	}

	@Override
	public int getCount(){
		return values.size();
	}


	@Override
	public List getItem(int position){
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
		cmdArrow.setTag(values.get(position));
		return rowView;
	}

}
