package com.gdpapps.listeditor.Managers;

import android.content.Context;
import android.os.Environment;

import com.gdpapps.listeditor.ListObject.ListComps.ListItem;
import com.gdpapps.listeditor.ListObject.ListComps.Preferences;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Date;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.EmailConfig;
import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.Info;
import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.gdpapps.listeditor.R;

public class IOManager extends Utilities implements Constants {
    Context context;
    Preferences prefs = new Preferences();
	ArrayList<ListItem> list = new ArrayList<ListItem>();		
    
	public IOManager(Context context) {
		this.context = context;
		if (isAvailable()){	
			if (isFileExisting()) {logPrint("File handle achieved: " + DataFilePath);} 
			else {
				resetFileHandle(DataFilePath); 
				logPrint("File handle reset forced: " + DataFilePath);
				showToast(context, R.string.msg_DataFileResetted);
			}} 
		else {showToast(context, R.string.msg_DeviceNotReady);}
	}
	
	public ArrayList<ListItem> getList() {return list;}
	public void setList(ArrayList<ListItem> list) {this.list = list;}

	public Preferences getPrefs() {return prefs;}
	public void setPrefs(Preferences prefs) {this.prefs = prefs;}

	public void readFile(){
	    new Thread(new Runnable() {public void run(){
			try{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(DataFilePath));
				doc.getDocumentElement().normalize();

				NodeList nParentNodes = doc.getFirstChild().getChildNodes();
				NodeList nPrefs = nParentNodes.item(0).getChildNodes(), 
						 nListItems = nParentNodes.item(1).getChildNodes();
				
				Node nPrefsNode = nPrefs.item(0);
				Element ePrefsElement = (Element) nPrefsNode;
				prefs.setUserEmail(ePrefsElement.getAttribute(tagXMLEmailConfigEmail));
				prefs.setUserEncryptedPass(ePrefsElement.getAttribute(tagXMLEmailConfigPass));
				
				int len = nListItems.getLength();
				for (int temp = 0; temp < len; temp++){
					Node nListNode = nListItems.item(temp);
					Element eListElement = (Element) nListNode;
					int index = convertString(eListElement.getAttribute(tagXMLIndex));
					String fileName = eListElement.getAttribute(tagXMLFileName);
					ListItem item = readListFile(fileName);
					item.setIndex(index);
					list.add(item);
				}
			} 
			catch (Exception e) {Utilities.logPrint("Exception main!"); e.printStackTrace();}
		}}).start();	
	}
	private ListItem readListFile(final String filePath) {
		final ArrayList<String> fileData = new ArrayList<String>();
		final Info info = new Info(nullString, nullString, nullString,  null, null, false, null);
		new Thread(new Runnable() {public void run() {
			try {
				File file = new File(filePath);
				if (file.exists()) {
					StringBuilder sb = new StringBuilder();
					BufferedReader bReader = new BufferedReader(new FileReader(file));  
					while ((bReader.readLine()) != null) {sb.append(bReader.readLine() + lineBreak);}
					bReader.close();
					String temp = sb.toString();
					String[] topLevel = temp.split(fileSectionSplit);
					String[] information = topLevel[0].split(lineBreak);
					info.setName(information[1].split(fileSectionSplit)[1]);
					info.setLocation(information[2].split(fileSectionSplit)[1]);
					info.setFileName(filePath);
					info.setDayOfEvent(new Date(context,
							convertString(information[4].split(fileSectionSplit)[1]), //hour
							convertString(information[5].split(fileSectionSplit)[1]), //day
							convertString(information[6].split(fileSectionSplit)[1]), //month
							convertString(information[7].split(fileSectionSplit)[1]), //year
							convertString(information[8].split(fileSectionSplit)[1]))); //dayOfWeek
					info.setDayOfListClose(new Date(context,
							convertString(information[10].split(fileSectionSplit)[1]),
							convertString(information[11].split(fileSectionSplit)[1]),
							convertString(information[12].split(fileSectionSplit)[1]),
							convertString(information[13].split(fileSectionSplit)[1]),
							convertString(information[14].split(fileSectionSplit)[1])));
					info.setRepeat(information[15].split(fileSectionSplit)[1] == "true" ? true : false);
					info.setEmailConfig(new EmailConfig(
							information[16].split(fileSectionSplit)[1],
							information[18].split(fileSectionSplit)[1],
							(information[19].split(fileSectionSplit)[1]).split(fileMailDestSplit)));
					String[] listItems = topLevel[1].split(Constants.lineBreak);
					for (int i = 0; i < listItems.length; i++) {fileData.add(listItems[i]);}
				} else {
					
				}
			}
			catch (Exception e) {e.printStackTrace();}
		}}).start();
		return new ListItem(context, 0, info, fileData);
	}
	
	public void writeFile() {
		new Thread(new Runnable() {public void run() {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element data = doc.createElement(tagXMLMain);
				doc.appendChild(data);

				Element ePrefs = doc.createElement(tagXMLPreferences);
				data.appendChild(ePrefs);
				
				Element eEmailConfigPrefs = doc.createElement(tagXMLEmailConfigPrefs);
				ePrefs.appendChild(eEmailConfigPrefs);
				eEmailConfigPrefs.setAttribute(tagXMLEmailConfigEmail, String.valueOf(prefs.getUserEmail()));
				eEmailConfigPrefs.setAttribute(tagXMLEmailConfigPass, String.valueOf(prefs.getUserEncryptedPass()));

				Element eListItems = doc.createElement(tagXMLListItems);
				data.appendChild(eListItems);
				for (int i = 0; i < list.size(); i++) {
					ListItem item = list.get(i);
					Element eList = doc.createElement(tagXMLList);
					eListItems.appendChild(eList);

					eList.setAttribute(tagXMLIndex, String.valueOf(item.getIndex()));
					eList.setAttribute(tagXMLFileName, String.valueOf(item.getInfo().getFileName()));
					
					writeListFile(item.getInfo().getFileName(), item);
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(DataFilePath);
				transformer.transform(source, result);

				Utilities.showToast(context, R.string.msg_DataFileWriteSuccess);
			}
			catch (Exception e) {e.printStackTrace();}
		}}).start();
	}
	private void writeListFile(final String fileName, final ListItem fileData) {
		new Thread(new Runnable() {public void run() {
			try {
				File file = new File(FolderPath + fileName);
				if (file.exists()) {file.delete();}
				file.createNewFile();
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
				StringBuilder sb = new StringBuilder()
				.append(InfoSectionHeader + lineBreak)
				.append(tagName + fileLineDataSplit + fileData.getInfo().getName() + lineBreak)
				.append(tagLocation + fileLineDataSplit + fileData.getInfo().getLocation() + lineBreak)
				.append(tagDayOfEvent + lineBreak)
				.append(fileSecondLevelMark + tagDateHour + fileLineDataSplit + fileData.getInfo().getDayOfEvent().getHour() + lineBreak)
				.append(fileSecondLevelMark + tagDateDay + fileLineDataSplit + fileData.getInfo().getDayOfEvent().getDay() + lineBreak)
				.append(fileSecondLevelMark + tagDateMonth + fileLineDataSplit + fileData.getInfo().getDayOfEvent().getMonth() + lineBreak)
				.append(fileSecondLevelMark + tagDateYear + fileLineDataSplit + fileData.getInfo().getDayOfEvent().getYear() + lineBreak)
				.append(fileSecondLevelMark + tagDateDayOfWeek + fileLineDataSplit + fileData.getInfo().getDayOfEvent().getDayOfWeek() + lineBreak)
				.append(tagDayOfListClose + lineBreak)
				.append(fileSecondLevelMark + tagDateHour + fileLineDataSplit + fileData.getInfo().getDayOfListClose().getHour() + lineBreak)
				.append(fileSecondLevelMark + tagDateDay + fileLineDataSplit + fileData.getInfo().getDayOfListClose().getDay() + lineBreak)
				.append(fileSecondLevelMark + tagDateMonth + fileLineDataSplit + fileData.getInfo().getDayOfListClose().getMonth() + lineBreak)
				.append(fileSecondLevelMark + tagDateYear + fileLineDataSplit + fileData.getInfo().getDayOfListClose().getYear() + lineBreak)
				.append(fileSecondLevelMark + tagDateDayOfWeek + fileLineDataSplit + fileData.getInfo().getDayOfListClose().getDayOfWeek() + lineBreak)
				.append(tagEmailConfig + lineBreak)
				.append(fileSecondLevelMark + tagEmailConfigTitle + fileLineDataSplit + fileData.getInfo().getEmailConfig().getMailTitle() + lineBreak)
				.append(fileSecondLevelMark + tagEmailConfigSender + fileLineDataSplit + fileData.getInfo().getEmailConfig().getMailSender() + lineBreak)
				.append(fileSecondLevelMark + tagEmailConfigDest + fileLineDataSplit + fileData.getInfo().getEmailConfig().getMailDestString() + lineBreak)
				
				.append(fileSectionSplit + lineBreak)
				.append(ListItemsSectionHeader + lineBreak)
				.append(fileData.getFlatList());
				
				bWriter.write(sb.toString());
				bWriter.close();
			}
			catch (Exception e) {e.printStackTrace();}
		}}).start();
		Utilities.showToast(context, R.string.msg_ListFileWriteSuccess);
	}
	
	private boolean isAvailable() {
		boolean mESAvailable = false; 
		boolean mESWriteable= false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {mESAvailable = mESWriteable = true;}
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {mESAvailable = true; mESWriteable = false;}
		else {mESAvailable = mESWriteable = false;}
		if (mESAvailable && mESWriteable) {return true;}
		else {return false;}
	}
	
	private void resetFileHandle(String filePath){
		File file = new File(DataFilePath);
		File path = new File(filePath);
		if (!path.isDirectory() || !path.exists()){path.mkdir(); logPrint("Folder created: " + filePath);} 
		if (file.exists()){file.delete(); logPrint("Deleting previous file: " + DataFilePath);}
		logPrint("Attempting to recreate handle on " + DataFilePath); 
		try{file.createNewFile();logPrint("Recreating handle successful");} 
		catch (IOException e){logPrint("Recreating handle failed:" + lineBreak); e.printStackTrace();}
	}
	
	private boolean isFileExisting(){
		File file = new File(DataFilePath);
		if (file.exists()) {return true;}
		return false;
	}
}
