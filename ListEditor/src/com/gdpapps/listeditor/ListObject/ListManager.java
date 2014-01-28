package com.gdpapps.listeditor.ListObject;

import android.content.Context;
import android.widget.ListView;
import com.gdpapps.listeditor.Managers.ListAdapter;
import com.gdpapps.listeditor.ListInfoActivity;
import com.gdpapps.listeditor.EditionActivity;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;
import com.gdpapps.listeditor.ListObject.ListComps.ListItem;
import com.gdpapps.listeditor.ListObject.ListComps.Preferences;
import com.gdpapps.listeditor.Managers.IOManager;
import com.gdpapps.listeditor.R;
import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities;
import java.util.ArrayList;

public class ListManager implements Constants {
	ArrayList<ListItem> listFiles;
	IOManager ioManager;
	Context context;
	ListView listView;
	Preferences prefs;

	public ListManager(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
		
		ioManager = new IOManager(context);
		ioManager.readFile();
		this.prefs = ioManager.getPrefs();
		this.listFiles = ioManager.getList();
		
		refresh();
	}

	public int getNextFreeIndex() {return listFiles.size();}
	
	public void add(ListItem list) {
		listFiles.add(list); refresh(); Utilities.showToast(context, R.string.msg_ListItemCreated);}
	public void clear(int index) {
		listFiles.get(index).setList(new ArrayList<String>());}
	public void duplicate(int index) {
		listFiles.add(listFiles.get(index)); refresh();}
	public void edit(int index) {
		replace(index, EditionActivity.showEditionActivity(context, listFiles.get(index), true)); refresh();}
	public void editInfo(int index) {
		listFiles.get(index).setInfo(ListInfoActivity.showListInfoActivity(context, listFiles.get(index).getInfo(), index)); refresh();}
	public void editInfo(Info info, int index) {
		listFiles.get(index).setInfo(info); refresh();}
	public void editTitle() {
		
	}
	public void format(int index) {
		listFiles.get(index).format();}
	public void newList() {
		listFiles.add(EditionActivity.showEditionActivity(context, new ListItem(context, getNextFreeIndex() - 1, Info.getEmptyInstance(context)), false)); refresh();}
	public void refresh() {
		listView.setAdapter(new ListAdapter(context, listFiles));}
	public void remove(ListItem list) {
		listFiles.remove(list); refresh(); Utilities.showToast(context, R.string.msg_ListItemDeleted);}
	public void remove(int index) {
		listFiles.remove(index); refresh(); Utilities.showToast(context, R.string.msg_ListItemDeleted);}
	public void replace(int index, ListItem item) {
		listFiles.set(index, item); refresh();}
	public void write() {
		ioManager.writeFile();}

}
