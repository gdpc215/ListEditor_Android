package com.gdpapps.listeditor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.gdpapps.listeditor.Objects.List;
import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities;
import com.gdpapps.listeditor.Objects.Info;

public class EditionActivity extends Activity implements Constants{

    List item;
	EditText input, output;
	TextView tvListNameData, tvListLocationData, tvListDayEventData, tvListDayListCloseData;

	private void readBundle(Bundle bundle){
		if (!bundle.isEmpty()){
			item = bundle.getParcelable(BundleKey);
			Info info = item.getInfo();
			tvListNameData.setText(info.getName());
			tvListLocationData.setText(info.getLocation());
			tvListDayEventData.setText(info.getDayOfEvent().getFullDateString());
			tvListDayListCloseData.setText(info.getDayOfListClose().getFullDateString());
		}
	}
	private void assignInstances(){
		Button cmdAddList, cmdSaveList, cmdClear, cmdExport, cmdEditListInfo;
		
		input = (EditText) findViewById(R.id.edition_etInputBox);
		output = (EditText) findViewById(R.id.edition_etOutputBox);
		
		cmdAddList = (Button) findViewById(R.id.edition_cmdAddList);
	    cmdSaveList = (Button) findViewById(R.id.edition_cmdSaveList);
		cmdClear = (Button) findViewById(R.id.edition_cmdClear);
	    cmdExport = (Button) findViewById(R.id.edition_cmdExport);
	    cmdEditListInfo = (Button) findViewById(R.id.edition_cmdEditListInfo);
		
	    tvListNameData = (TextView) findViewById(R.id.edition_tvListNameData);
	    tvListLocationData = (TextView) findViewById(R.id.edition_tvListLocationData);
	    tvListDayEventData = (TextView) findViewById(R.id.edition_tvListDayEventData);
	    tvListDayListCloseData = (TextView) findViewById(R.id.edition_tvListDayListCloseData);
		
		cmdAddList.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {cmdAddList();}});
		cmdSaveList.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {cmdSaveList();}});
		cmdClear.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {cmdClear();}});
		cmdExport.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {cmdExport();}});
		cmdEditListInfo.setOnClickListener(new View.OnClickListener() 
			{@Override public void onClick(View v) {cmdEditListInfo();}});
	}
	public void cmdAddList(){}
	public void cmdSaveList(){}
	public void cmdClear(){} 
	public void cmdExport(){} 
	public void cmdEditListInfo(){}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edition);
		InflatePopUpMenus();
		assignInstances();
		readBundle(getIntent().getExtras());
	}
	
	private void InflatePopUpMenus() {
		final Button 
			file_cmd = (Button) findViewById(R.id.edition_mnuFile), 
			edit_cmd = (Button) findViewById(R.id.edition_mnuEdit),
			options_cmd = (Button) findViewById(R.id.edition_mnuOptions);

		final int 
			file_menuXml = R.menu.editionmenu_file, 
			edit_menuXml = R.menu.editionmenu_edit,  
			options_menuXml = R.menu.editionmenu_options;

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
		/*
		file_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.mnuFileNew:
					EditionActivity.this.mnuFileNew(); break;
				case R.id.mnuFileSaveList:
					EditionActivity.this.mnuFileSave(); break;
				case R.id.mnuFileEdit:
					EditionActivity.this.mnuFileEdit(); break;
				case R.id.mnuFileDelete:
					EditionActivity.this.mnuFileDelete(); break;
				case R.id.mnuFileExit:
					EditionActivity.this.mnuFileExit(); break;
				}
				return true;
			}
		});
		edit_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.mnuEditTitle:
					EditionActivity.this.mnuEditTitle(); break;
				case R.id.mnuEditInfo:
					EditionActivity.this.mnuEditInfo(); break;
				case R.id.mnuEditDuplicate:
					EditionActivity.this.mnuEditDuplicate(); break;
				case R.id.mnuEditEraseContent:
					EditionActivity.this.mnuEditEraseContent(); break;
				case R.id.mnuEditForceFormat:
					EditionActivity.this.mnuEditForceFormat(); break;
				}
				return true;
			}
		});
		options_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.mnuOptionsFormatOptions:
					EditionActivity.this.mnuOptionsFormatOptions(); break;
				case R.id.mnuOptionsConfig:
					EditionActivity.this.mnuOptionsConfig(); break;
				}
				return true;
			}
		});*/
	}

//	private void close(){
//		Intent intent = new Intent();
//        Bundle extras = new Bundle();
//        extras.putParcelable("list", item);
//		intent.putExtras(extras);
//        setResult(RESULT_OK, intent);        
//        finish();
//	}
}
