package com.gdpapps.listeditor.Objects.ListComponents;


public class Info {

	private String name, location, fileName;
	private Date dayOfEvent, dayOfListClose;

	public Info(String name, String location, Date dayOfEvent, Date dayOfListClose) {
		this.name = name;
		this.location = location;
		this.dayOfEvent = dayOfEvent;
		this.dayOfListClose = dayOfListClose;
		this.fileName = "";
	}
	public Info(String name, String location, Date dayOfEvent, Date dayOfListClose, String listFileName) {
		this(name, location, dayOfEvent, dayOfListClose);
		setFileName(listFileName);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getLocation()
	{
		return location;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public Date[] getDatesArray(){
		return new Date[]{this.dayOfEvent, this.dayOfListClose};
	}
	
	public Date getDayOfEvent() {
		return dayOfEvent;
	}
	
	public void setDayOfEvent(Date dayOfEvent) {
		this.dayOfEvent = dayOfEvent;
	}

	public Date getDayOfListClose() {
		return dayOfListClose;
	}
	
	public void setDayOfListClose(Date dayOfListClose) {
		this.dayOfListClose = dayOfListClose;
	}

}
