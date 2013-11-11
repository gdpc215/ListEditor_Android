package com.gdpapps.listeditor.Objects;
import java.util.*;
import com.gdpapps.listeditor.Utils.*;
import android.content.*;

public class ListManager implements Constants
{
	ArrayList<List> listFiles;
	FileIO ioManager;
	
	public ListManager(Context context){
		ioManager = new FileIO(context, ListsFolderPath, ListsDataFileName);

		listFiles = ioManager.readFile();
	}
	
}
