package com.gdpapps.listeditor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.gdpapps.listeditor.EditionActivity;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Date;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.EmailConfig;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;
import com.gdpapps.listeditor.MainActivity;
import com.gdpapps.listeditor.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.Bundle;
import android.view.Window;


public class ListInfoActivity extends Activity
{
	private int edYear, edMonth, edDay, edHour, edDayOfWeek, 
	            lcYear, lcMonth, lcDay, lcHour, lcDayOfWeek;
	private EditText fieldName, fieldLocation, fieldDateOfEvent, fieldDateOfListClose, fieldMailTitle, fieldMailDestinataries;
	private Button fieldDateOfEventDate, fieldDateOfEventHour, fieldDateOfListCloseDate, fieldDateOfListCloseHour, fieldCommit;
	private CheckBox fieldRepeated;

	private Context context;
	private int index;
	private Info initial;
	public Info result;

	public ListInfoActivity(){}
	
	public ListInfoActivity(Context context)
	{
		setTitle(context.getText(R.string.diag_lblTitleNew));
		Calendar calendar = Calendar.getInstance();
		edYear = lcYear = calendar.get(Calendar.YEAR);
		edMonth = lcMonth = calendar.get(Calendar.MONTH);
		edDay = lcDay =  calendar.get(Calendar.DAY_OF_MONTH);
		edHour = lcHour = calendar.get(Calendar.HOUR_OF_DAY);
		updateDisplay();
	}
	
	public ListInfoActivity(Context context, Info item, int index)
	{
		setTitle(context.getText(R.string.diag_lblTitleEdit));
		this.index = index;
		this.result = this.initial = item;

		fieldName.setText(item.getName());
		fieldLocation.setText(item.getLocation());
		fieldMailTitle.setText(item.getEmailConfig().getMailTitle());
		fieldMailDestinataries.setText(item.getEmailConfig().getMailDestString());
		fieldRepeated.setChecked(item.isRepeat());

		edYear = item.getDayOfEvent().getYear();
		edMonth = item.getDayOfEvent().getMonth();
		edDay = item.getDayOfEvent().getDay();
		edHour = item.getDayOfEvent().getHour();
		lcYear = item.getDayOfListClose().getYear();
		lcMonth = item.getDayOfListClose().getMonth();	
		lcDay = item.getDayOfListClose().getDay();
		lcHour = item.getDayOfListClose().getHour();
		updateDisplay();
	}

	public static Info showListInfoActivity(Context context){
		ListInfoActivity lfAct = new ListInfoActivity(context);
		return lfAct.result;
	}
	public static Info showListInfoActivity(Context context, Info listItem, int index){
		ListInfoActivity lfAct = new ListInfoActivity(context, listItem, index);
		return lfAct.result;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edition);
		assignInstances();
		if (edition) {readInfo(item.getInfo()); output.setText(item.getFlatList());}
		else {ListInformationDiag.showDialog(this);}
	}
	
	
	private void assignInstances()
	{
		this.context = ctx;
		this.result = Info.getEmptyInstance(ctx);

		dialog.setContentView(R.layout.dialog_listinfo);

		fieldName = (EditText) dialog.findViewById(R.id.dialog_listInfo_evName);
		fieldLocation = (EditText) dialog.findViewById(R.id.dialog_listInfo_evLocation);
		fieldDateOfEvent = (EditText) dialog.findViewById(R.id.dialog_listInfo_evDateOfEvent);
		fieldDateOfListClose = (EditText) dialog.findViewById(R.id.dialog_listInfo_evDateOfListClose);
		fieldMailTitle = (EditText) dialog.findViewById(R.id.dialog_listInfo_evMailTitle);
		fieldMailDestinataries = (EditText) dialog.findViewById(R.id.dialog_listInfo_evMailDestinataries);

		fieldDateOfEventDate = (Button) dialog.findViewById(R.id.dialog_listInfo_cmdDateOfEventDate);
		fieldDateOfEventHour = (Button) dialog.findViewById(R.id.dialog_listInfo_cmdDateOfEventHour);
		fieldDateOfListCloseDate = (Button) dialog.findViewById(R.id.dialog_listInfo_cmdDateOfListCloseDate);
		fieldDateOfListCloseHour = (Button) dialog.findViewById(R.id.dialog_listInfo_cmdDateOfListCloseHour);
		fieldCommit = (Button) dialog.findViewById(R.id.dialog_listInfo_cmdOk);

		fieldRepeated = (CheckBox) dialog.findViewById(R.id.dialog_listInfo_checkRepeated);

		fieldDateOfEventDate.setOnClickListener(new View.OnClickListener() 
			{public void onClick(View v)
				{
					DatePickerDialog dpDiag = new DatePickerDialog(
						dialog.getContext(), 
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
							{lcYear = year; lcMonth = monthOfYear; lcMonth = dayOfMonth;}}, 
						lcYear, lcMonth, lcDay); 
					dpDiag.setTitle("Select date");
					dpDiag.show();
					updateDisplay();}});
		fieldDateOfListCloseDate.setOnClickListener(new View.OnClickListener() 
			{public void onClick(View v)
				{
					DatePickerDialog dpDiag = new DatePickerDialog(
						dialog.getContext(), 
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
							{edYear = year; edMonth = monthOfYear; edDay = dayOfMonth;}}, 
						lcYear, lcMonth, lcDay); 
					dpDiag.setTitle("Select date");
					dpDiag.show();
					updateDisplay();}});

		fieldDateOfEventHour.setOnClickListener(new View.OnClickListener() 
			{public void onClick(View v)
				{
					TimePickerDialog tpDiag = new TimePickerDialog(
						dialog.getContext(), 
						new TimePickerDialog.OnTimeSetListener(){
							public void onTimeSet(TimePicker view, int hour, int minute)
							{edHour = hour;}}, 
						edHour, 0, false);
					tpDiag.setTitle("Select time (ignore minutes)");
					tpDiag.show();
					updateDisplay();}});
		fieldDateOfListCloseHour.setOnClickListener(new View.OnClickListener() 
			{public void onClick(View v)
				{
					TimePickerDialog tpDiag = new TimePickerDialog(
						dialog.getContext(), 
						new TimePickerDialog.OnTimeSetListener(){
							public void onTimeSet(TimePicker view, int hour, int minute)
							{lcHour = hour;}}, 
						lcHour, 0, false);
					tpDiag.setTitle("Select time (ignore minutes)");
					tpDiag.show();
					updateDisplay();}});

		fieldCommit.setOnClickListener(new View.OnClickListener() 
			{public void onClick(View v)
				{
					updateDisplay();
					result.setName(fieldName.getText().toString());
					result.setLocation(fieldLocation.getText().toString());
					result.setRepeat(fieldRepeated.isChecked());
					result.setDayOfEvent(new Date(context, edHour, edDay, edMonth, edYear, edDayOfWeek));
					result.setDayOfListClose(new Date(context, lcHour, lcDay, lcMonth, lcYear, lcDayOfWeek));
					result.setEmailConfig(new EmailConfig(fieldMailTitle.getText().toString(), "", fieldMailDestinataries.getText().toString()));
					close();}});
	}

	private void close()
	{
		dialog.hide();
		if (context instanceof EditionActivity)
		{((EditionActivity) context).readInfo(result);} 
		else
		{((MainActivity) context).retrieveInfo(result, index);}

	}
	private void updateDisplay()
	{
		SimpleDateFormat sdf =  new SimpleDateFormat(context.getString(R.string.timeFormat), Locale.getDefault());
		Calendar edCal = Calendar.getInstance(), lcCal = Calendar.getInstance();

		edCal.setTime(new java.util.Date());
		edCal.set(edYear, edMonth, edDay, edHour, 00);				
		edDayOfWeek = edCal.get(Calendar.DAY_OF_WEEK);
		fieldDateOfEvent.setText(sdf.format(edCal.getTime()));

		lcCal.setTime(new java.util.Date());
		lcCal.set(lcYear, lcMonth, lcDay, lcHour, 00);
		lcDayOfWeek = lcCal.get(Calendar.DAY_OF_WEEK);
		fieldDateOfListClose.setText(sdf.format(lcCal.getTime()));
	}
}
