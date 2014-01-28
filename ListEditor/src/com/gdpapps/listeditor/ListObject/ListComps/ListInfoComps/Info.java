package com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps;
import android.content.Context;


public class Info {

	private String name, location, fileName;
	private Date dayOfEvent, dayOfListClose;
	private boolean repeat;
	private EmailConfig emailConfig;

	public Info(String name, String location, String fileName, Date dayOfEvent, Date dayOfListClose, boolean repeat, EmailConfig emailConfig){
		this.name = name;
		this.location = location;
		this.fileName = fileName;
		this.dayOfEvent = dayOfEvent;
		this.dayOfListClose = dayOfListClose;
		this.repeat = repeat;
		this.emailConfig = emailConfig;
	}

	public void setEmailConfig(EmailConfig emailConfig) {this.emailConfig = emailConfig;}
	public EmailConfig getEmailConfig() {return emailConfig;}

	public void setRepeat(boolean repeat) {this.repeat = repeat;}
	public boolean isRepeat() {return repeat;}

	public void setName(String name) {this.name = name;}
	public String getName() {return name;}

	public void setLocation(String location) {this.location = location;}
	public String getLocation() {return location;}

	public void setFileName(String fileName) {this.fileName = fileName;}
	public String getFileName() {return fileName;}

	public Date getDayOfEvent() {return dayOfEvent;}
	public void setDayOfEvent(Date dayOfEvent) {this.dayOfEvent = dayOfEvent;}

	public Date getDayOfListClose() {return dayOfListClose;}
	public void setDayOfListClose(Date dayOfListClose) {this.dayOfListClose = dayOfListClose;}

	public static Info getEmptyInstance() 
	    {return new Info("", "", "", Date.getEmptyInstance(), Date.getEmptyInstance(), false, EmailConfig.getEmptyInstance());}
	public static Info getEmptyInstance(Context ctx)
	    {return new Info("", "", "", Date.getEmptyInstance(ctx), Date.getEmptyInstance(ctx), false, EmailConfig.getEmptyInstance());}
	
	}
