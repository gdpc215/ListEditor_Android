package com.gdpapps.listeditor.Managers;

import java.util.Date; 
import java.util.Properties; 
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.activation.MailcapCommandMap; 
import javax.mail.BodyPart; 
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart;

import com.gdpapps.listeditor.ListObject.ListComps.ListInfoComps.EmailConfig;
import com.gdpapps.listeditor.Utils.Utilities;

import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;



public class EmailManager extends javax.mail.Authenticator { 
	private String _user, _pass; 
	private String _port, _sport, _host; 
	private EmailConfig emailConfig;
	private boolean _auth, _debuggable; 
	private Multipart _multipart; 

	private EmailManager() { 
		_host = "smtp.gmail.com"; // default smtp server 
		_port = "465"; // default smtp port 
		_sport = "465"; // default socketfactory port
		_debuggable = false; // debug mode on or off - default off 
		_auth = true; // smtp authentication - default on 
		_multipart = new MimeMultipart(); 

		// There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		CommandMap.setDefaultCommandMap(mc); 
	} 

	public EmailManager(String user, String pass){
		this();
		_user = user; 
		_pass = pass; 
	}
		
	public static boolean checkInternetConnection(Context ctx) {
		ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) return false;
		else if (!i.isConnected()) return false;
		else if (!i.isAvailable()) return false;
		return true;
	}
	
	public boolean send() { 
		new Thread(
		    new Runnable() {
				public void run() {
					try {
						Properties props = _setProperties(); 

						if (!_user.equals("") && !_pass.equals("")){ 
							Session session = Session.getInstance(props, EmailManager.this); 

							MimeMessage msg = new MimeMessage(session); 
							msg.setFrom(new InternetAddress(emailConfig.getMailSender())); 
							String[] _to = emailConfig.getMailDest();
							InternetAddress[] addressTo = new InternetAddress[_to.length]; 
							for (int i = 0; i < _to.length; i++) {addressTo[i] = new InternetAddress(_to[i]);} 
							msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 

							msg.setSubject(emailConfig.getMailTitle()); 
							msg.setSentDate(new Date()); 

							// setup message body 
							BodyPart messageBodyPart = new MimeBodyPart(); 
							messageBodyPart.setText(emailConfig.getMailBody()); 
							_multipart.addBodyPart(messageBodyPart); 

							// Put parts in message 
							msg.setContent(_multipart); 

							// send email 
							Transport.send(msg);
						}
					}
					catch (Exception e)
					{Utilities.logPrint("Exception main! \n" + e.getMessage());}
				}
			}
		).start();
		return true;
	} 

	public void addAttachment(String filename) throws Exception { 
		BodyPart messageBodyPart = new MimeBodyPart(); 
		DataSource source = new FileDataSource(filename); 
		messageBodyPart.setDataHandler(new DataHandler(source)); 
		messageBodyPart.setFileName(filename); 

		_multipart.addBodyPart(messageBodyPart); 
	} 

	@Override 
	public PasswordAuthentication getPasswordAuthentication()
	{return new PasswordAuthentication(_user, _pass);} 

	private Properties _setProperties() { 
		Properties props = new Properties(); 
		props.put("mail.smtp.host", _host); 

		if (_debuggable) {props.put("mail.debug", "true");} 
		if (_auth) {props.put("mail.smtp.auth", "true");} 

		props.put("mail.smtp.port", _port); 
		props.put("mail.smtp.socketFactory.port", _sport); 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 

		return props; 
	} 

	// the getters and setters 
	public void setUser(String user) {_user = user;}
	public String getUser() {return _user;}

	public void setPass(String pass) {_pass = pass;}
	public String getPass() {return _pass;}
	
	public void setEmailConfig(EmailConfig data) {this.emailConfig = data;}
	public EmailConfig getEmailConfig() {return this.emailConfig;}
} 
