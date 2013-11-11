package com.gdpapps.listeditor.Managers;
import android.os.*;
import com.gdpapps.listeditor.Objects.*;

public class AsyncTaskManager
{
	class RetreiveFeedTask extends AsyncTask<String, Void, ListManager>
	{




		//	new RetreiveFeedTask().execute(urlToRssFeed);


		private Exception exception;

		protected ListManager doInBackground(String[] items) {
			try {
				/*	URL url= new URL(urls[0]);
				 SAXParserFactory factory =SAXParserFactory.newInstance();
				 SAXParser parser=factory.newSAXParser();
				 XMLReader xmlreader=parser.getXMLReader();
				 RssHandler theRSSHandler=new RssHandler();
				 xmlreader.setContentHandler(theRSSHandler);
				 InputSource is=new InputSource(url.openStream());
				 xmlreader.parse(is);
				 return theRSSHandler.getFeed();*/
			} catch (Exception e) {
				this.exception = e; }
			return null;

		}

		protected void onPostExecute(ListManager feed) {
			// TODO: check this.exception 
			// TODO: do something with the fee
		}
	}
}
