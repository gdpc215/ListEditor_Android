package com.gdpapps.listeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities;
import com.gdpapps.listeditor.ListInfoActivity;
import com.gdpapps.listeditor.ListObject.ListManager;
import com.gdpapps.listeditor.ListObject.ListComps.ListItem;
import com.gdpapps.listeditor.Managers.EmailManager;
import android.app.Dialog;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;
import com.gdpapps.listeditor.Managers.IOManager;

public class MainActivity extends Activity implements Constants {

	ListManager listManager;
	
	ListView lstList;
	
	long idSelected = ID_Null;
	boolean longClick = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		InflatePopUpMenus();	
		initComponents();
	}

	private void initComponents() {
		lstList = (ListView) findViewById(R.id.main_lstList);
		listManager = new ListManager(this, lstList);
		
		lstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
					MainActivity.this.listClick(childView, position, id);
				}
		});
		lstList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parentView, View childView, int position, long id) {
					MainActivity.this.listLongClick(childView, position, id);
					return false;
				}
		});
		lstList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView, View childView, int position, long id) {
					MainActivity.this.listSelected(childView, position, id);
				}
				@Override
				public void onNothingSelected(AdapterView<?> parentView) {
					MainActivity.this.listNothingSelected();
				}
	    });
	}

	private void InflatePopUpMenus() {
		final Button 
			file_cmd = (Button) findViewById(R.id.main_mnuFile), 
			edit_cmd = (Button) findViewById(R.id.main_mnuEdit),
			options_cmd = (Button) findViewById(R.id.main_mnuOptions);

		final int 
			file_menuXml = R.menu.mainmenu_file, 
			edit_menuXml = R.menu.mainmenu_edit,  
			options_menuXml = R.menu.mainmenu_options;

		final PopupMenu
			file_popup = new PopupMenu(this, file_cmd), 
			edit_popup = new PopupMenu(this, edit_cmd),  
			options_popup = new PopupMenu(this, options_cmd);

		file_popup.getMenuInflater().inflate(file_menuXml, file_popup.getMenu());
		edit_popup.getMenuInflater().inflate(edit_menuXml, edit_popup.getMenu());
		options_popup.getMenuInflater().inflate(options_menuXml, options_popup.getMenu());

		file_cmd.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {file_popup.show();}});
		edit_cmd.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {edit_popup.show();}});
		options_cmd.setOnClickListener(new View.OnClickListener()
			{@Override public void onClick(View v) {options_popup.show();}});
		
		file_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.mnuFileNew: MainActivity.this.mnuFileNew(); break;
					case R.id.mnuFileSaveAll: MainActivity.this.mnuFileSave(); break;
					case R.id.mnuFileEdit: MainActivity.this.mnuFileEdit(); break;
					case R.id.mnuFileDelete: MainActivity.this.mnuFileDelete(); break;
					case R.id.mnuFileExit: MainActivity.this.mnuFileExit(); break; 
				}
				return true;
			}
		});
		edit_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.mnuEditTitle: MainActivity.this.mnuEditTitle(); break;
					case R.id.mnuEditInfo: MainActivity.this.mnuEditInfo(); break;
					case R.id.mnuEditDuplicate: MainActivity.this.mnuEditDuplicate(); break;
					case R.id.mnuEditEraseContent: MainActivity.this.mnuEditEraseContent(); break;
					case R.id.mnuEditForceFormat: MainActivity.this.mnuEditForceFormat(); break;
				}
				return true;
			}
		});
		options_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.mnuOptionsFormatOptions: MainActivity.this.mnuOptionsFormatOptions(); break;
					case R.id.mnuOptionsConfig: MainActivity.this.mnuOptionsConfig(); break;
				}
				return true;}
		});
	}
	
	public void listArrowButtonClick(View v) {listManager.edit(Integer.parseInt(v.getTag().toString()));}
	
	private void listClick(View child, int pos, long id) {
		if(MainActivity.this.isSelectionValid() && MainActivity.this.getIdSelected() == id){
			MainActivity.this.setIdSelected(ID_Null);
			child.setSelected(false);
			Utilities.logPrint("Click: Cleared selection");
		} else {
			MainActivity.this.setIdSelected(id);
			child.setSelected(true);
			Utilities.logPrint("Click: Selected new item: " + id);
		}
		if (longClick) {child.setSelected(false); longClick = false;}
	}
	private void listLongClick(View child, int pos, long id){
    	//true action: show popup menu/contextual menu
        longClick = true;
	}
	private void listSelected(View child, int pos, long id) {}
	private void listNothingSelected() {}

	private void setIdSelected(long idSelected) {this.idSelected = idSelected; Utilities.logPrint("ID selected: " + idSelected);}
	private long getIdSelected() {return idSelected;} 
	private boolean isSelectionValid() {if(idSelected == ID_Null) {return false;} else {return true;}}
	
	private void mnuFileNew() {listManager.newList();}
	private void mnuFileSave() {}
	private void mnuFileEdit() {if (isSelectionValid()) {listManager.edit((int) idSelected);}}
	private void mnuFileDelete() {listManager.remove((int) idSelected);}
	private void mnuFileExit() {mnuFileSave();}	
	private void mnuEditTitle() {}
	private void mnuEditInfo() {listManager.editInfo((int) idSelected);}
	private void mnuEditDuplicate() {listManager.duplicate((int) idSelected);}
	private void mnuEditEraseContent() {listManager.clear((int) idSelected);}
	private void mnuEditForceFormat() {listManager.format((int) idSelected);}
	private void mnuOptionsFormatOptions() {}
	private void mnuOptionsConfig() {}
	private void mnuOptionsItem2() {}
	
	public void retrieveInfo(Info info, int index) {listManager.editInfo(info, index);}
}
