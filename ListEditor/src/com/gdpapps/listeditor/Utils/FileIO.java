package com.gdpapps.listeditor.Utils;

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

import android.content.Context;
import android.os.Environment;

import com.gdpapps.listeditor.Objects.ListComponents.Date.*;
import com.gdpapps.listeditor.Objects.ListComponents.*;
import org.w3c.dom.*;

public class FileIO extends Utilities implements Constants{
    Context context;
	String fileName;

	public FileIO(Context context, String filePath, String fileName)
	{
		this.context = context;
	    this.fileName = filePath + fileName;
		if (isAvailable()){	
			if (isFileExisting()){
				logPrint("File handle achieved: " + fileName);
			} else {
				resetFileHandle(filePath); 
				logPrint("File reset forced: " + fileName);
			}
		} else {
			String message =  "The device is not ready. Couldn't load data file";
			logPrint(message);
		    showToast(context, message);
		}
	}
	public ArrayList<List> readFile()
	{
		ArrayList<List> list = new ArrayList<List>();
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(fileName));
			doc.getDocumentElement().normalize();
			
            NodeList nParentNodes = doc.getFirstChild().getChildNodes();
            NodeList nPrefs = nParentNodes.item(0).getChildNodes(), nListItems = nParentNodes.item(1).getChildNodes();
			
			int len = nPrefs.getLength();
			for (int temp = 0; temp < len; temp++)
			{
			//	Element eElement = (Element) nPrefs.item(temp);
			}
			Utilities.logPrint("-------------Start read---------------");
 
			len = nListItems.getLength();
			Utilities.logPrint("Main nodes acquired");
			for (int temp = 0; temp < len; temp++)
			{
				Utilities.logPrint("Starting read - " + temp);
				Node nNode = nListItems.item(temp);
				Element eElement = (Element) nNode;
				Utilities.logPrint("Child node acquired - " + temp);
				int index = convertString(eElement.getAttribute("index"));
				Utilities.logPrint("index get - " + temp);
				String name = eElement.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
				Utilities.logPrint("name get - " + temp);
				String location = eElement.getElementsByTagName("location").item(0).getFirstChild().getNodeValue();
				Utilities.logPrint("location get - " + temp);
			//	String listFileName = eElement.getElementsByTagName("listFileName").item(0).getFirstChild().getNodeValue();
				  
			    Utilities.logPrint("Obtaining date nodes - " + temp);
				  
			    Date[] dates = new Date[2];
				Utilities.logPrint("Creating data handle - " + temp);
				NodeList nDateList = eElement.getElementsByTagName("date");
				for (int tmp = 0; tmp < nDateList.getLength(); tmp++){
					Node nDateNode = nDateList.item(tmp);
	    			Utilities.logPrint("Date nodes acquired - " + temp);
					if (nDateNode.getNodeType() == Node.ELEMENT_NODE){
						Element eDateElement = (Element) nDateNode;
						Utilities.logPrint("reading node data - " + temp);
						try {
							int type = convertString(eDateElement.getAttribute("type"));
							Utilities.logPrint("date type get - " + temp + "-" + tmp);
							int hourTemp = convertString(checkNodeExistance(eDateElement.getElementsByTagName("hour")) ? eDateElement.getElementsByTagName("hour").item(0).getFirstChild().getNodeValue(): "");
							Utilities.logPrint("date hour get - " + temp + "-" + tmp);
							int dayOfWeekTemp = convertString(checkNodeExistance(eDateElement.getElementsByTagName("dayOfWeek")) ? eDateElement.getElementsByTagName("dayOfWeek").item(0).getFirstChild().getNodeValue(): "");
							Utilities.logPrint("date dayOfWeek get - " + temp + "-" + tmp);
							dates[tmp] = new Date(hourTemp, dayOfWeekTemp, type);
						} catch (Exception e) {
							Utilities.logPrint("Exception date! \n" + e.getMessage());
						}
						Utilities.logPrint("date gathered - " + temp + "-" + tmp);
					}
				}
				Utilities.logPrint("Filling data - " + temp);
				Date _dayOfEvent = dates[0], _dayListClose = dates[1];
				if (_dayOfEvent.getType() != Date.DateType.EVENT_DATE){
					Date tDate = _dayOfEvent;
					_dayOfEvent = _dayListClose;
					_dayListClose = tDate;
				}
		     	Utilities.logPrint("Filling list item - " + temp);
				list.add(new List(index, new Info(name, location, _dayOfEvent, _dayListClose, ""), null));//listFileName), readListFile(listFileName)));
			}
		}
		catch (Exception e) {Utilities.logPrint("Exception main! \n" + e.getMessage());}
		Utilities.showToast(context, "Files successfully retrieved.");
		return list;
	}

	
	private ArrayList<String> readListFile(String filePath)
	{
		ArrayList<String> fileData = new ArrayList<String>();
		try
		{
			File file = new File(filePath);
			if (file.exists())
			{
				BufferedReader bReader = new BufferedReader(new FileReader(file));  
				while ((bReader.readLine()) != null)
				{fileData.add(bReader.readLine());}
				bReader.close();
			}
		}
		catch (Exception e)
		{e.printStackTrace();}
		return fileData;
	}

	public void writeFile(ArrayList<List> list)
	{
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element data = doc.createElement("ListEditorData");
			doc.appendChild(data);
			
			Element ePrefs = doc.createElement("Preferences");
			data.appendChild(ePrefs);
			{
				Element eFontSize = doc.createElement("fontSize");
				eFontSize.setAttribute("value", String.valueOf("10"));
				ePrefs.appendChild(eFontSize);
			
				Element eFont = doc.createElement("font");
				eFont.appendChild(doc.createTextNode("font name"));
				ePrefs.appendChild(eFont);
			}
			
			Element eListItems = doc.createElement("ListItems");
			data.appendChild(eListItems);
			for (int i = 0; i < list.size(); i++)
			{
				List item = list.get(i);
				Element eList = doc.createElement("list");
				eListItems.appendChild(eList);

				eList.setAttribute("index", String.valueOf(item.getIndex()));

				Element eName = doc.createElement("name");
				eName.appendChild(doc.createTextNode(item.getInfo().getName()));
				eList.appendChild(eName);

				Element eLocation = doc.createElement("location");
				eLocation.appendChild(doc.createTextNode(String.valueOf(item.getInfo().getLocation())));
				eList.appendChild(eLocation);

				Element eDayOfEvent = doc.createElement("date");
				eList.appendChild(eDayOfEvent);

				for (int p = 0; p < 2; p++)
				{
					Date currentDate = item.getInfo().getDatesArray()[p];
					Element eDate = doc.createElement("date");
					eList.appendChild(eDate);

					eDate.setAttribute("type", String.valueOf(currentDate.getType()));

					Element eDateHour = doc.createElement("hour");
					eDateHour.appendChild(doc.createTextNode(String.valueOf(currentDate.getHour())));
					eDate.appendChild(eDateHour);

					Element eDateDayOfWeek = doc.createElement("dayOfWeek");
					eDateDayOfWeek.appendChild(doc.createTextNode(String.valueOf(currentDate.getDayOfWeekIndex())));
					eDate.appendChild(eDateDayOfWeek);
				}
				
				String listFilePath = item.getInfo().getFileName();

				Element eListFileName = doc.createElement("listFileName");
				eListFileName.appendChild(doc.createTextNode(listFilePath));
				eList.appendChild(eListFileName);

				writeListFile(listFilePath, item.getList());
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fileName);
			transformer.transform(source, result);
			Utilities.showToast(context,"File succesfully written");
		}
		catch (Exception e) {e.printStackTrace();}
	}
    
	private void writeListFile(String filePath, ArrayList<String> fileData)
	{
		try
		{
			File file = new File(filePath);
			if (file.exists()) {file.delete();}
			file.createNewFile();
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
			int count = fileData.size();
			for (int i = 0; i < count; i++) {bWriter.write(fileData.get(i) + "\n");}
			bWriter.close();
		}
		catch (Exception e)
		{e.printStackTrace();}
	}

	private boolean isAvailable()
	{
		boolean mESAvailable = false; 
		boolean mESWriteable= false;
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) 
			{mESAvailable = mESWriteable = true;}
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		    {mESAvailable = true; 
			mESWriteable = false;}
		else
		    {mESAvailable = mESWriteable = false;}
			
		if (mESAvailable && mESWriteable)
			{return true;}
		else 
		    {return false;}
	}
	
	private void resetFileHandle(String filePath)
	{
		File file = new File(fileName);
		File path = new File(filePath);
		if (!path.isDirectory() || !path.exists()){
			path.mkdir();
			logPrint("Folder created: " + filePath);
		} 
		if (file.exists()){
			file.delete();
			logPrint("Deleting previous file: " + fileName); 
		}
		try{
			logPrint("Attempting to recreate handle on " + fileName); 
			file.createNewFile();
			logPrint("Recreating handle successful"); 
		} 
		catch (IOException e){
			logPrint("Recreating handle failed: \n" + e.getMessage()); 
			e.printStackTrace();
		}
	}
	
	private boolean isFileExisting(){
		File file = new File(fileName);
		if (file.exists()) {return true;}
		return false;
	}
}
