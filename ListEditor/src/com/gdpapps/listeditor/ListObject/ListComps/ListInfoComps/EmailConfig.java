package com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps;
import java.util.ArrayList;

import com.gdpapps.listeditor.Utils.Constants;

public class EmailConfig implements Constants{
	
	private String mailTitle, mailSender, mailBody;
	private ArrayList<String> mailDest = new ArrayList<String>();
    
	public EmailConfig(String mailTitle, String mailSender, String[] mailDest){
		this.mailTitle = mailTitle;
		this.mailSender = mailSender;
		for(int i = 0; i < mailDest.length; i++) {this.mailDest.add(mailDest[i]);}
	}
	public EmailConfig(String mailTitle, String mailSender, String mailDest){
		this.mailTitle = mailTitle;
		this.mailSender = mailSender;
		addMailDestString(mailDest);
	}
	
	public void setMailTitle(String mailTitle) {this.mailTitle = mailTitle;}
	public String getMailTitle() {return mailTitle;}

	public void setMailDest(String[] mailDest) {
		for (int i = 0; i < mailDest.length; i++) {this.mailDest.add(mailDest[i]);}}
	public String[] getMailDest() {
		String[] ret = new String[mailDest.size()];
		for (int i = 0; i < mailDest.size(); i++) {ret[i] = mailDest.get(i);} 
		return ret;}
	public void addMailDest(String newDest) {
		this.mailDest.add(newDest);}
	public void addMailDest(String[] newDest) {
		for (int i = 0; i < newDest.length; i++) {this.mailDest.add(newDest[i]);}}
	public void delMailDest(String deleteDest) {
		this.mailDest.remove(deleteDest);}
	public void delMailDest(int deleteDest) {
		this.mailDest.remove(deleteDest);}
	public void delMailDest(String[] deleteDest) {
		for (int i = 0; i < deleteDest.length; i++) {this.mailDest.remove(deleteDest[i]);}}
	public void editMailDest(String oldDest, String newDest) {
		this.mailDest.set(this.mailDest.indexOf(oldDest), newDest);}
	public void editMailDest(int location, String newDest) {
		this.mailDest.set(location, newDest);}
	public int getMailDestIndex(String itemDest){
		return this.mailDest.indexOf(itemDest);}
	public void addMailDestString(String dest){
		addMailDest(dest.split(fileMailDestSplit));}
	public String getMailDestString(){
		String ret = nullString;
		int len  = mailDest.size();
		for (int i = 0; i < len - 1; i++) {ret += mailDest.get(i) + fileMailDestSplit;} 
		ret += mailDest.get(len - 1);
		return ret;}
	
	public void setMailSender(String mailSender) {this.mailSender = mailSender;}
	public String getMailSender() {return mailSender;}	
	
	public void setMailBody(ArrayList<String> list){
		int len = list.size();
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < len; i++) {str.append(list.get(i)); if (i != len-1){str.append(Constants.lineBreak);}}
		mailBody = str.toString();}
	public String getMailBody(){return this.mailBody;}
	
	public static EmailConfig getEmptyInstance(){return new EmailConfig("", "", "");}
}
