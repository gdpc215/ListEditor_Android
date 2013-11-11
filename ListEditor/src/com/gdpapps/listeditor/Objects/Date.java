package com.gdpapps.listeditor.Objects;

public class Date {

	private int hour, dayOfWeek, type;
	
	public Date(int hour, int dayOfWeek, int type) {
		this.hour = hour;
		this.dayOfWeek = dayOfWeek;
		this.type = type;
	}

	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getDayOfWeekIndex() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || 
			dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY || 
			dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || 
			dayOfWeek == DayOfWeek.SUNDAY)
		{this.dayOfWeek = dayOfWeek;}
	}
	
	private String getDayOfWeekString(){
		if (dayOfWeek == DayOfWeek.MONDAY) {return "Lunes";} 
		else if (dayOfWeek == DayOfWeek.TUESDAY) {return "Martes";} 
		else if (dayOfWeek == DayOfWeek.WEDNESDAY) {return "Miercoles";} 
		else if (dayOfWeek == DayOfWeek.THURSDAY) {return "Jueves";} 
		else if (dayOfWeek == DayOfWeek.FRIDAY) {return "Viernes";} 
		else if (dayOfWeek == DayOfWeek.SATURDAY) {return "Sabado";} 
		else if (dayOfWeek == DayOfWeek.SUNDAY) {return "Domingo";}
		else {return "";}
	}
	
	private static int getDayOfWeekInt(String dayOfWeek){
		if (dayOfWeek == "Lunes") {return DayOfWeek.MONDAY;} 
		else if (dayOfWeek == "Martes" ) {return DayOfWeek.TUESDAY ;} 
		else if (dayOfWeek == "Miercoles" ) {return DayOfWeek.WEDNESDAY ;} 
		else if (dayOfWeek == "Jueves" ) {return DayOfWeek.THURSDAY ;} 
		else if (dayOfWeek == "Viernes" ) {return DayOfWeek.FRIDAY ;} 
		else if (dayOfWeek == "Sabado" ) {return DayOfWeek.SATURDAY ;} 
		else if (dayOfWeek == "Domingo" ) {return DayOfWeek.SUNDAY ;}
		else {return -1;}
	}
	
	private String getFormattedHour(){
		if(hour > 12)
			{return (hour - 12) + ":00 p.m.";} 
		else 
			{return hour + ":00 a.m.";}
	}
	public String getFullDateString(){
		return "  " + getDayOfWeekString() + " " + getFormattedHour();
	}
	
	@Override
	public String toString(){
		return getFullDateString();
	}
	
	
	public static Date readFromString(String date, int type){
		date.trim();
		String[] items = date.split(" ");
		String[] hours = items[1].split(" ");
		String[] numbs = hours[0].split(":");
		int hourInt = Integer.parseInt(numbs[0]);
		if(hours[1].equals("p.m.")){
			hourInt += 12;
		}
		return new Date(hourInt, getDayOfWeekInt(items[0]), type);
	}
	
public interface DayOfWeek{
	public static final int 
			SUNDAY = 0x00,
			MONDAY = 0x01,
			TUESDAY = 0x02,
			WEDNESDAY = 0x03,
			THURSDAY = 0x04, 
			FRIDAY = 0x05,
			SATURDAY = 0x06;
	}
public interface DateType{
	public static final int
	        EVENT_DATE = 0x10, 
			LIST_CLOSURE = 0x11;
	}
}
