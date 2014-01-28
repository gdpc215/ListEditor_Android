//package com.gdpapps.listeditor.Managers;
//
//import com.gdpapps.listeditor.ListObject.ListManager;
//import android.os.AsyncTask;
//
//public class AsyncTaskManager
//{
//	static class ParamList{
//		
//		int opType;
//		public ParamList(int opType, ListManager man){
//			this.opType = opType;
//		}
//		
//		
//		public interface OpType{
//			public static final int 
//			        writeDataFile = 0x120, 
//					readDataFile = 0x121, 
//					writeTextFile = 0x122, 
//			        readTextFile = 0x123;
//		}
//	}
//	
//	static class AsyncFileIO extends AsyncTask<ParamList, Void, ListManager>{
//		private Exception exception;
//
//		protected ListManager doInBackground(ParamList[] items) {
//			try {
//				
//			}
//			catch (Exception e) {this.exception = e;}
//			return null;
//		}
//
//		protected void onPostExecute(ListManager listMgr)
//		{
//			
//		}
//		
//		protected void onPreExecute() {}
//
//		protected void onProgressUpdate() {}
//
//		protected void onCancelled(ListManager result) {}
//
//		protected void onCancelled() {}
//	}
//	
//}

//static class AsyncFileIO extends AsyncTask<String, Void, Void>
//{
//	private Exception exception;
//
//	protected void doInBackground(String[] items)
//	{
//		try
//		{
//			String fileName = items[ 0 ];
//
//			/*	URL url= new URL(urls[0]);
//			 SAXParserFactory factory =SAXParserFactory.newInstance();
//			 SAXParser parser=factory.newSAXParser();
//			 XMLReader xmlreader=parser.getXMLReader();
//			 RssHandler theRSSHandler=new RssHandler();
//			 xmlreader.setContentHandler(theRSSHandler);
//			 InputSource is=new InputSource(url.openStream() + lineBreak);
//			 xmlreader.parse(is);
//			 return theRSSHandler.getFeed();*/
//		}
//		catch (Exception e)
//		{this.exception = e;}
//		//return null;
//
//	}
//
//	protected void onPostExecute(ListManager listMgr)
//	{
//
//	}
//}


//new Thread(new Runnable() {
//public void run() {
//    pbarProgreso.post(new Runnable() {
//			public void run() {
//				pbarProgreso.setProgress(0);
//			}
//		});
//
//	for(int i=1; i<=10; i++) {
//		tareaLarga();
//		pbarProgreso.post(new Runnable() {
//				public void run() {
//					pbarProgreso.incrementProgressBy(10);
//				}
//			});
//	}
//
//	runOnUiThread(new Runnable() {
//			public void run() {
//				Toast.makeText(MainHilos.this, "Tarea finalizada!",
//							   Toast.LENGTH_SHORT).show();
//			}
//		});
//}
//}).start();
