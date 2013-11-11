package com.gdpapps.listeditor.Objects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.gdpapps.listeditor.EditionActivity;
import com.gdpapps.listeditor.Objects.ListComponents.List;
import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.FileIO;
import com.gdpapps.listeditor.Utils.ListAdapter;
import java.util.ArrayList;

public class ListManager implements Constants
{
	ArrayList<List> listFiles;
	FileIO ioManager;
	Context context;
	ListView listView;

	public ListManager(Context context, ListView listView)
	{
		ioManager = new FileIO(context, ListsFolderPath, ListsDataFileName);
		this.context = context;
		listFiles = ioManager.readFile();
	}

	public void addListItem(List list)
	{
		listFiles.add(list);
		resetListAdapter();
	}


	private void readFromFile()
	{
	    listFiles = ioManager.readFile();	
	}
	private void writeToFile()
	{
		ioManager.writeFile(listFiles);
	}
	
	public void resetListAdapter(){
		listView.setAdapter(new ListAdapter(context, listFiles));
	}


	//runEditionWindow(listFiles.get((int) id));
	public void runEditionWindow(List item) {
		Intent intent = new Intent(context, EditionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(BundleKey, item);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
}
