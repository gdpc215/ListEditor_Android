package com.gdpapps.listeditor.ListObject.ListComps;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Date;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.EmailConfig;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;
import com.gdpapps.listeditor.Utils.StringUtilities;
import android.content.Context;
import java.security.PrivateKey;
import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities;

public class ListItem implements /*Parcelable, */ Constants {

	private Info info;
	private ArrayList<String> list;
	private int index;
	private Context context;
	
	public ListItem(Context context, int index, Info info, ArrayList<String> list) {
		this.index = index;
		this.info = info;
		this.list = list;
		this.context = context;
	}

	public ListItem(Context context, int index, Info info) {
		this.index = index;
		this.info = info;
		this.list = new ArrayList<String>() ;
		this.context = context;
	}
	
	public Info getInfo() {return info;}
	public void setInfo(Info info) {this.info = info;}
	
	public ArrayList<String> getList() {return list;}
	public void setList(ArrayList<String> list) {this.list = list;}
	public String getFlatList() {return StringUtilities.ConvertArrayListToString(list);}
/*	private String[] getArrayList() {
		int size = this.list.size();
		String[] list = new String[size];
		for(int i = 0; i < size; i++){list[i] = this.list.get(i);}
		return list;}
	private void setArrayList(String[] list){
		int size = list.length;
		this.list = new ArrayList<String>();
		for(int i = 0; i < size; i++){this.list.add(list[i]);}
	}	*/
	
	public void add(String[] items, boolean formalName){
		list.addAll(fixName(items, formalName));
	}
	public void add(ArrayList<String> items, boolean formalName){
		list.addAll(fixName(items, formalName));
	}
	public void add(String item, boolean formalName){
		list.addAll(fixName(new String[]{item}, formalName));
	}
	
	public void format() {ArrayList<String> end = fixName(list, false); list = end;}
	
	public int getIndex() {return index;}
	public void setIndex(int index) {this.index = index;}
/*
	@Override
	public int describeContents() {return 0;}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
		dest.writeStringArray(getArrayList());
        dest.writeString(info.getName());
        dest.writeString(info.getLocation());
        dest.writeString(info.getFileName());
        dest.writeInt(info.getDayOfEvent().getHour());
		dest.writeInt(info.getDayOfEvent().getDay());
		dest.writeInt(info.getDayOfEvent().getMonth());
		dest.writeInt(info.getDayOfEvent().getYear());
        dest.writeInt(info.getDayOfEvent().getDayOfWeek());
        dest.writeInt(info.getDayOfListClose().getHour());
		dest.writeInt(info.getDayOfListClose().getDay());
		dest.writeInt(info.getDayOfListClose().getMonth());
		dest.writeInt(info.getDayOfListClose().getYear());
        dest.writeInt(info.getDayOfListClose().getDayOfWeek());
        dest.writeString(info.getEmailConfig().getMailTitle());
        dest.writeString(info.getEmailConfig().getMailSender());
        dest.writeString(info.getEmailConfig().getMailDestString());
	}

	public ListItem(Parcel pIn) {
		Info inf = Info.getEmptyInstance(context);
		String[] lst = null;
		
		this.index = pIn.readInt();
		pIn.readStringArray(lst);
		this.setArrayList(lst);

		inf.setName(pIn.readString());
		inf.setLocation(pIn.readString());
		inf.setFileName(pIn.readString());
		inf.getDayOfEvent().setHour(pIn.readInt());
		inf.getDayOfEvent().setDay(pIn.readInt());
		inf.getDayOfEvent().setMonth(pIn.readInt());
		inf.getDayOfEvent().setYear(pIn.readInt());
		inf.getDayOfEvent().setDayOfWeek(pIn.readInt());
		inf.getDayOfListClose().setHour(pIn.readInt());
		inf.getDayOfListClose().setDay(pIn.readInt());
		inf.getDayOfListClose().setMonth(pIn.readInt());
		inf.getDayOfListClose().setYear(pIn.readInt());
		inf.getDayOfListClose().setDayOfWeek(pIn.readInt());
		inf.getEmailConfig().setMailTitle(pIn.readString());
		inf.getEmailConfig().setMailSender(pIn.readString());
		inf.getEmailConfig().addMailDestString(pIn.readString());
		this.info = inf;
	}

   	@SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListItem createFromParcel(Parcel in) {return new ListItem(in);}
        public ListItem[] newArray(int size) {return new ListItem[size];}
    };
	*/
	//String modification methods
	private ArrayList<String> fixName(String[] items, boolean formalName) {
        int len = items.length;
        String[] rawData = items;
        ArrayList<String> result = new ArrayList<String>();
        if (len > 0) {
            for (int l = 0; l < len; l++) {
                if (rawData[l].indexOf(commaChar) != -1 && !formalName) {
                    ArrayList<String> commaNames = Utilities.convertArrayToList(rawData[l].split(comma));
                    if (commaNames.size() > 0) {result = merge(fixName(commaNames, formalName), result);}
                } else {
                    if (formalName) {rawData[l] = fixFormalName(rawData[l]);}
                    char chrItems[] = rawData[l].toCharArray();
                    int arrLen = chrItems.length;
                    int location = 0;
                    String FullName = nullString;
                    for (int a = 0; a < arrLen; a++) {
                        boolean exists = false;
                        for (int comp1 = 0; comp1 < charListLenght; comp1++) {if ((int) chrItems[a] == charList[comp1]) {exists = true;}}
                        if (!exists) {chrItems[a] = noneChar;} 
						else {
                            if ((int) chrItems[a] == spaceChar) {chrItems[a] = noneChar;}
							else if (chrItems[a] != noneChar) {chrItems[a] = (String.valueOf(chrItems[a]).toUpperCase()).charAt(0); location = a; break;}
                        }
                    }
                    for (int a = location + 1; a < arrLen; a++) {
                        boolean exists = false;
                        for (int comp2 = 0; comp2 < Constants.charListLenght; comp2++) {if ((int) chrItems[a] == Constants.charList[comp2]) {exists = true;}}
                        if (!exists) {chrItems[a] = Constants.noneChar;} 
						else {
                            if ((int) chrItems[a - 1] == spaceChar) {chrItems[a] = (String.valueOf(chrItems[a]).toUpperCase()).charAt(0);}
                            if ((int) chrItems[a - 1] == spaceChar && (int) chrItems[a] == spaceChar) {chrItems[a - 1] = Constants.noneChar;}
                        }
                    }
                    for (int m = 0; m < arrLen; m++) {if (chrItems[m] != Constants.noneChar) {FullName += chrItems[m];}}
                    if (result.indexOf(FullName.trim()) == -1) {result.add(FullName.trim());}
                }
            }
        }
        return result;
    }
	private ArrayList<String> fixName(ArrayList<String> items, boolean formalName) {return fixName(Utilities.convertListToArray(items), formalName);}

    private String fixFormalName(String formalName) {
        int temp = formalName.indexOf(Constants.commaChar);
        if (temp != -1) {
            String lastName = formalName.substring(0, temp);
            String name = formalName.substring(temp + 1);
            return name + " " + lastName;
        } else {return formalName;}
    }
	
	private ArrayList<String> merge (ArrayList<String> vector1, ArrayList<String> vector2){
		ArrayList<String> result = new ArrayList<String>();
		int len1 = vector1.size(); int len2 = vector2.size();
		for (int i = 0; i < len1; i++) {String item = vector1.get(i); if (!result.contains(item)) {result.add(item);}}
		for (int i = 0; i < len2; i++) {String item = vector2.get(i); if (!result.contains(item)) {result.add(item);}}
		return result;
	}
}
