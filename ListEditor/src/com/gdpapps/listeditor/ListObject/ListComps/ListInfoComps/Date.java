package com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps;

import java.util.Calendar;

import com.gdpapps.listeditor.R;
import android.content.Context;

public class Date {

	private int hour, day, month, dayOfWeek, year;
	private Context context;

	public Date(Context context, int hour, int day, int month, int year, int dayOfWeek) {
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.year = year;
		this.context = context;
	}

	public void setYear(int year) {this.year = year;}
	public int getYear() {return year;}

	public int getHour() {return hour;}
	public void setHour(int hour) {this.hour = hour;}
	
	public void setDay(int day) {this.day = day;}
	public int getDay() {return day;}

	public void setMonth(int month) {this.month = month;}
	public int getMonth() {return month;}

	public int getDayOfWeek() {return dayOfWeek;}
	public void setDayOfWeek(int dayOfWeek) {this.dayOfWeek = dayOfWeek;}
	
	private String getDayOfWeekString() {
		if (dayOfWeek == Calendar.MONDAY) {return context.getString(R.string.ds_dayOfWeekMonday);} 
		else if (dayOfWeek == Calendar.TUESDAY) {return context.getString(R.string.ds_dayOfWeekTuesday);}
		else if (dayOfWeek == Calendar.WEDNESDAY) {return context.getString(R.string.ds_dayOfWeekWednesday);}
		else if (dayOfWeek == Calendar.THURSDAY) {return context.getString(R.string.ds_dayOfWeekThursday);}
		else if (dayOfWeek == Calendar.FRIDAY) {return context.getString(R.string.ds_dayOfWeekFriday);}
		else if (dayOfWeek == Calendar.SATURDAY) {return context.getString(R.string.ds_dayOfWeekSaturday);}
		else if (dayOfWeek == Calendar.SUNDAY) {return context.getString(R.string.ds_dayOfWeekSunday);}
		else {return "";}
	}
	
	private String getMonthString() {
		if (month == Calendar.JANUARY) {return context.getString(R.string.ds_monthJanuary);} 
		else if (month == Calendar.FEBRUARY) {return context.getString(R.string.ds_monthFebruary);}
		else if (month == Calendar.MARCH) {return context.getString(R.string.ds_monthMarch);}
		else if (month == Calendar.APRIL) {return context.getString(R.string.ds_monthApril);}
		else if (month == Calendar.MAY) {return context.getString(R.string.ds_monthMay);}
		else if (month == Calendar.JUNE) {return context.getString(R.string.ds_monthJune);}
		else if (month == Calendar.JULY) {return context.getString(R.string.ds_monthJuly);}
		else if (month == Calendar.AUGUST) {return context.getString(R.string.ds_monthAugust);}
		else if (month == Calendar.SEPTEMBER) {return context.getString(R.string.ds_monthSeptember);}
		else if (month == Calendar.OCTOBER) {return context.getString(R.string.ds_monthOctober);}
		else if (month == Calendar.NOVEMBER) {return context.getString(R.string.ds_monthNovember);}
		else if (month == Calendar.DECEMBER) {return context.getString(R.string.ds_monthDecemeber);}
		else {return "";}
	}
	
	private String getFormattedHour() {
		if (hour > 12) {return (hour - 12) + ":00 p.m.";}
		else {return hour + ":00 a.m.";}
	}

	public String getFullDateString() {
		return "  " + getDayOfWeekString() + " " + getDay() + " " + getMonthString() + " - " + getFormattedHour();
	}

	@Override
	public String toString() {
		return getFullDateString();
	}

	public static Date getEmptyInstance(){
		Calendar d = Calendar.getInstance();
		return new Date(null, d.get(Calendar.HOUR), d.get(Calendar.DAY_OF_MONTH), d.get(Calendar.MONTH) + 1, d.get(Calendar.YEAR), d.get(Calendar.DAY_OF_WEEK));
	}
	
	public static Date getEmptyInstance(Context ctx){
		Calendar d = Calendar.getInstance();
		return new Date(ctx, d.get(Calendar.HOUR), d.get(Calendar.DAY_OF_MONTH), d.get(Calendar.MONTH) + 1, d.get(Calendar.YEAR), d.get(Calendar.DAY_OF_WEEK));
	}
	
	public interface DateType {
		public static final int 
		    EVENT_DATE = 0x10, 
			LIST_CLOSURE = 0x11;
	}
}
