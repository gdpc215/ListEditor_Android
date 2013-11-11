package com.gdpapps.listeditor.Utils;

import org.w3c.dom.NodeList;

import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;

public class Utilities {
	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
		System.out.println(message);
	}

	public static void showToast(Context context, String message, View parentView) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setView(parentView);
		toast.show();
		System.out.println(message);
	}

	public static void logPrint(String message) {
		System.out.println(message);
	}

	public static int convertString(String text) {
		int result = 0;
		if (text != "") {
			try {
				result = Integer.parseInt(text);
			} catch (NumberFormatException e) {
				Utilities.logPrint(e.getMessage());
			}
		}
		return result;
	}
	
	public static boolean checkNodeExistance(NodeList list){
		if(list.getLength() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public static int randomGen(int low, int high){
		Random r = new Random();
		return (r.nextInt(high - low) + low)
		;
	}

}
