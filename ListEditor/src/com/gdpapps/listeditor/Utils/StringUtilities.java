package com.gdpapps.listeditor.Utils;

import java.util.ArrayList;

public class StringUtilities {

	public static ArrayList<String> SplitString (String str, String divider){
    	ArrayList<String> result= new ArrayList<String>();
	    boolean flag = false;
	    do {
    		int temp = str.indexOf(divider, 0);
    		if(temp != -1){
	    		String res = str.substring(0, temp);
	    		str = str.substring(temp + 1);
	    		if (!(str.length() == 0)){result.add(res); }
    		} else {
    			result.add(str);
    			flag = true;}
    	} while (!flag);
	    return result;
    }
	
	public static String BetweenComillas(String string){
		return Constants.comilla + string + Constants.comilla;
	}
	
	public static ArrayList<String> Merge (ArrayList<String> vector1, ArrayList<String> vector2){
		ArrayList<String> result = new ArrayList<String>();
		int len1 = vector1.size(); int len2 = vector2.size();
		for (int i = 0; i < len1; i++){
			String item = vector1.get(i);
			if (getIndexInArrayList(result, item) == -1) {result.add(item);}
		}
		for (int i = 0; i < len2; i++){
			String item = vector2.get(i);
			if (getIndexInArrayList(result, item) == -1) {result.add(item);}
		}
		return result;
	}
	public static int getIndexInArrayList (ArrayList<String> list, String item){
		int size = list.size(); 
		for (int i = 0; i < size; i++){
			if ((list.get(i)).compareTo(item) == 0){
				return i;
			}
		}
		return -1;
	}
	
	public static String ConvertArrayListToString(ArrayList<String> vector){
		String result = "";
		int len = vector.size();
		for (int i = 0; i < len; i++){
			if (i != len-1){result += vector.get(i) + Constants.lineBreak;}
			else {result += vector.get(i);}
		}
		return result;		
	}
	
    public static ArrayList<String> FixName(ArrayList<String> items, boolean formalName) {
        int len = items.size();
        String[] rawData = new String[len];
        for (int i = 0; i < len; i++) {
            rawData[i] = items.get(i);
        }
        ArrayList<String> result = new ArrayList<String>();
        if (len > 0) {
            for (int l = 0; l < len; l++) {
                if (rawData[l].indexOf(Constants.commaChar) != -1 && !formalName) {
                    ArrayList<String> commaNames = SplitString(rawData[l], ",");
                    if (commaNames.size() > 0) {
                    	result = Merge(FixName(commaNames, formalName), result);
                    }
                } else {
                    if (formalName) {
                        rawData[l] = FixFormalName(rawData[l]);
                    }
                    char chrItems[] = rawData[l].toCharArray();
                    int arrLen = chrItems.length;
                    int location = 0;
                    String FullName = "";
                    for (int a = 0; a < arrLen; a++) {
                        boolean exists = false;
                        for (int comp1 = 0; comp1 < Constants.charListLenght; comp1++) {
                            if ((int) chrItems[a] == Constants.charList[comp1]) {
                                exists = true;
                            }
                        }
                        if (!exists) {
                            chrItems[a] = Constants.noneChar;
                        } else {
                            if ((int) chrItems[a] == (int) ' ') {
                                chrItems[a] = Constants.noneChar;
                            } else if (chrItems[a] != Constants.noneChar) {
                            	String upper = String.valueOf(chrItems[a]);
                            	upper.toUpperCase();
                                chrItems[a] = upper.charAt(0);
                                location = a;
                                break;
                            }
                        }
                    }
                    for (int a = location + 1; a < arrLen; a++) {
                        boolean exists = false;
                        for (int comp2 = 0; comp2 < Constants.charListLenght; comp2++) {
                            if ((int) chrItems[a] == Constants.charList[comp2]) {
                                exists = true;
                            }
                        }
                        if (!exists) {
                            chrItems[a] = Constants.noneChar;
                        } else {
                            if ((int) chrItems[a - 1] == (int) ' ') {
                            	String upper = String.valueOf(chrItems[a]);
                            	upper.toUpperCase();
                                chrItems[a] = upper.charAt(0);
                            }
                            if ((int) chrItems[a - 1] == (int) ' ' && (int) chrItems[a] == (int) ' ') {
                                chrItems[a - 1] = Constants.noneChar;
                            }
                        }
                    }
                    for (int m = 0; m < arrLen; m++) {
                        if (chrItems[m] != Constants.noneChar) {
                            FullName += chrItems[m];
                        }
                    }
                    if (result.indexOf(FullName.trim()) == -1) {
                        result.add(FullName.trim());
                    }
                }
            }
        }
        return result;
    }

    private static String FixFormalName(String formalName) {
        int temp = formalName.indexOf(Constants.commaChar);
        if (temp != -1) {
            String lastName = formalName.substring(0, temp);
            String name = formalName.substring(temp + 1);
            return name + " " + lastName;
        } else {
            return formalName;
        }
    }
}
