package com.gdpapps.listeditor.ListObject.ListComps;

import com.gdpapps.listeditor.Utils.Constants;
import com.gdpapps.listeditor.Utils.Utilities.Encryptor;

public class Preferences {
	String userEmail, userPass, userEncryptedPass;

	public String getUserEmail() {return userEmail;}
	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
	
	public String getUserPass() {return userPass;}
	public String getUserEncryptedPass() {return userEncryptedPass;}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
		try {this.userEncryptedPass =  Encryptor.encrypt(Constants.passSeed, userPass);} 
		catch (Exception e) {e.printStackTrace(); this.userEncryptedPass =  "";}}
	public void setUserEncryptedPass(String userEncryptedPass){
		this.userEncryptedPass = userEncryptedPass;
		try {this.userPass = Encryptor.decrypt(Constants.passSeed, userEncryptedPass);}
		catch (Exception e) {e.printStackTrace(); this.userPass = "";}} 
}