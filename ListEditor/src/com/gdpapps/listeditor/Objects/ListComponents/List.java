package com.gdpapps.listeditor.Objects.ListComponents;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.gdpapps.listeditor.Utils.StringUtils;

public class List implements Parcelable {

	private Info info;
	private ArrayList<String> list;
	private int index;
	
	public List(int index, Info info, ArrayList<String> list) {
		this.index = index;
		this.info = info;
		this.list = list;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public ArrayList<String> getList() {
		return list;
	}
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	public String getFlatList(){
	    return StringUtils.ConvertArrayListToString(list);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int describeContents() {return 0;}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
       // dest.writeStringList(list);
        dest.writeString(info.getName());
        dest.writeString(info.getLocation());
        dest.writeString(info.getFileName());
        dest.writeInt(info.getDayOfEvent().getType());
        dest.writeInt(info.getDayOfEvent().getHour());
        dest.writeInt(info.getDayOfEvent().getDayOfWeekIndex());
        dest.writeInt(info.getDayOfListClose().getType());
        dest.writeInt(info.getDayOfListClose().getHour());
        dest.writeInt(info.getDayOfListClose().getDayOfWeekIndex());
	}
	
	
	public List(Parcel pIn) {
		this.index = pIn.readInt();
	//	pIn.readStringList(this.list);
		
		Date dayOfEvent = new Date(0, 0, 0), dayOfListClose = new Date(0, 0, 0);
		Info inf = new Info("", "", dayOfEvent, dayOfListClose, "");
		inf.setName(pIn.readString());
		inf.setLocation(pIn.readString());
		inf.setFileName(pIn.readString());
		dayOfEvent.setType(pIn.readInt());
		dayOfEvent.setHour(pIn.readInt());
		dayOfEvent.setDayOfWeek(pIn.readInt());
		dayOfListClose.setType(pIn.readInt());
		dayOfListClose.setHour(pIn.readInt());
		dayOfListClose.setDayOfWeek(pIn.readInt());
		this.info = inf;
	}
	
   	@SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public List createFromParcel(Parcel in) {return new List(in);}
        public List[] newArray(int size) {return new List[size];}
    };
}
