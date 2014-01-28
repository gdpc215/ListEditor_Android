package com.gdpapps.listeditor.Utils;

public interface Constants {
	public static final int[] charList = new int[]{(int)' ', (int)'a', (int)'b', (int)'c', (int)'d', 
		  (int)'e', (int)'f', (int)'g', (int)'h', (int)'i', (int)'j', (int)'k', (int)'l', (int)'m', 
		  (int)'n', (int)'n', (int)'o', (int)'p', (int)'q', (int)'r', (int)'s', (int)'t', (int)'u', 
		  (int)'v', (int)'w', (int)'x', (int)'y', (int)'z', (int)'A', (int)'B', (int)'C', (int)'D', 
		  (int)'E', (int)'F', (int)'G', (int)'H', (int)'I', (int)'J', (int)'K', (int)'L', (int)'M', 
		  (int)'N', (int)'N', (int)'O', (int)'P', (int)'Q', (int)'R', (int)'S', (int)'T', (int)'U', 
		  (int)'V', (int)'W', (int)'X', (int)'Y', (int)'Z'};/*, (int)'�', (int)'�', (int)'�', (int)'�',
		  (int)'�', (int)'�', (int)'�', (int)'�', (int)'�', (int)'�'};*/
	public static final int charListLenght = 55,
	                        commaChar = (int)',',
							spaceChar = (int)' ';
	
	public static final char noneChar = (char)0,
	                         comilla = (char)34;
	
	public static final long ID_Null = -1;
	
	public static final String nullString = "",
							   lineBreak = "\n",
							   comma = ",",
							   fileSectionSplit = "-/-",
							   fileLineDataSplit = "=",
							   fileMailDestSplit = ";",
							   fileSecondLevelMark = "-";
	
	public static final String InfoSectionHeader = "[Information]",
							   ListItemsSectionHeader = "[ListItem items]";
	
	public static final String BundleKey = "listBundleKey";
	
	public static final String FolderPath = "/storage/sdcard0/data/ListEditor_files/",
							   DataFilePath = "/storage/sdcard0/data/ListEditor_files/data.dat",
							   DeprecatedFilePath = "/storage/sdcard0/data/ListEditor_files/deprecated/";
	
	public static final String passSeed = "ListEditor";
	
	public static final String 
			tagXMLMain = "ListEditorData", // Main tag
			
			tagXMLPreferences = "Preferences", //Preferences area
				
				tagXMLEmailConfigPrefs = "email_config",
				tagXMLEmailConfigEmail = "email", 
				tagXMLEmailConfigPass = "pass",
			
			tagXMLListItems = "ListItems", //ListItem Items area
			
				tagXMLList = "list",
				tagXMLIndex = "index", 
				tagXMLFileName = "fileName";
	
	public static final String
			tagName = "Name", 
			tagLocation = "Location", 
			tagFileName = "File name", 
			tagRepeated = "Is repeated", 
			
			tagDayOfEvent = "Day of Event", 
			tagDayOfListClose = "Day of List Close", 
			tagDateHour = "Hour", 
			tagDateDay = "Day", 
			tagDateMonth = "Month", 
			tagDateYear = "Year",
			tagDateDayOfWeek = "Day of week", 
			
			tagEmailConfig = "Email configuration",
			tagEmailConfigTitle = "Title", 
			tagEmailConfigSender = "Sender", 
			tagEmailConfigDest = "Destinataries";
}
