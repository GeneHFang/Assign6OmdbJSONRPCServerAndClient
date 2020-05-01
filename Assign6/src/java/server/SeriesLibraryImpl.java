
 package ser321.assign6.ghli1;
import java.io.*;
import java.util.*;
import java.net.URL;
// import java.rmi.server.*;
// import java.rmi.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;


/**
 * Copyright 2020 Gene Li and Tim Lindquist,
 *
 * This software is the intellectual property of the author, and can not be 
 * distributed, used, copied, or reproduced, in whole or in part, for any purpose, commercial or otherwise.
 * The author grants the ASU Software Engineering program the right to copy, execute, and evaluate this
 * work for the purpose of determining performance of the author in coursework,
 * and for Software Engineering program evaluation, so long as this copyright and
 * right-to-use statement is kept in-tact in such use.
 * All other uses are prohibited and reserved to the author.
 * 
 * 
 * Purpose: SeriesLibraryImpl is the implementing class for library interface. This version is run on a raspberry pi server
 *
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Gene H. Li ghli1@asu.edu
 *	   Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 */



public class SeriesLibraryImpl extends Object implements SeriesLibrary{

	protected Hashtable<String,SeriesSeason> aLib;
	private static final String fileName="seriesTest.json"; //JSON file on server-side that stores library information
	
	//blank constructor, used to manually populate fields
	public SeriesLibraryImpl(){ 
		super();
		this.aLib = new Hashtable<String,SeriesSeason>();
	}

	public boolean createNewFromJson(JSONObject series){
		System.out.println("Before clear");
		clears();
		boolean ret = false;
		System.out.println("after clear");
		Iterator<String> keys = series.keys();
			
			//create a SeriesSeason Object per JSONObject
			while (keys.hasNext()){
				String nodeTitle = keys.next();
				JSONObject actual = series.optJSONObject(nodeTitle);
				SeriesSeason sseason = new SeriesSeason();
				JSONArray epObjs = actual.getJSONArray("Episodes");
				ArrayList<Episode> eps = new ArrayList<>();
				if (epObjs != null) {
					for (int ik = 0 ; ik < epObjs.length(); ik++){
						String epTitle;
						int epNum;
						double epRating;
						JSONObject jEp = epObjs.getJSONObject(ik);
						epTitle = (String)jEp.get("Title");
							
						epNum = new Integer(jEp.get("Episode").toString());
						epRating = new Double(jEp.get("imdbRating").toString());
					
						Episode ep = new Episode(epTitle, epNum, epRating);
						eps.add(ep);
						
					}			
				}
				sseason.setTitle((String)actual.get("Title"));
				sseason.setGenre((String)actual.get("Genre"));
				sseason.setImgURL((String)actual.get("Poster"));
				sseason.setPlotSummary((String)actual.get("Plot"));
				sseason.setRating(new Double(actual.get("imdbRating").toString()));			
				sseason.setSeason(new Integer(actual.get("Season").toString()));
				sseason.setEpisodes(eps);
				
				
				try {
					addSeriesSeason(sseason);
					ret = true;
				}
				catch (Exception er) {
					ret = false;
					er.printStackTrace();
				}
				
			}
			return ret;
	}

	//constructor that is called first, populates user's current library seriesTest.json. If user has no JSON file saved, it will log to console informing user.
	public SeriesLibraryImpl(boolean init){
		super();
		if (init){
			this.aLib= new Hashtable<String,SeriesSeason>();
			try { InputStream i = new FileInputStream(new File(fileName));
					JSONObject series = new JSONObject(new JSONTokener(i));
					Iterator<String> keys = series.keys();
					while (keys.hasNext()){
						String nodeTitle = keys.next();
						//System.out.println("KEY"+nodeTitle);
						JSONObject actual = series.optJSONObject(nodeTitle);
						
						//System.out.println("SON"+actual.toString());
							SeriesSeason sseason = new SeriesSeason();
							JSONArray epObjs = actual.getJSONArray("Episodes");
				//Iterator<String> keys = obj.keys();
				

				ArrayList<Episode> eps = new ArrayList<>();
				if (epObjs != null) {
					for (int ik = 0 ; ik < epObjs.length(); ik++){
						String epTitle;
						int epNum;
						double epRating;
						JSONObject jEp = epObjs.getJSONObject(ik);
						epTitle = (String)jEp.get("Title");
						epNum = new Integer(jEp.get("Episode").toString());
						epRating = new Double(jEp.get("imdbRating").toString());
						//System.out.println("attrs: "+epTitle+epNum+epRating);
						Episode ep = new Episode(epTitle, epNum, epRating);
						eps.add(ep);
						
					}			
				}
				sseason.setTitle((String)actual.get("Title"));
				sseason.setGenre((String)actual.get("Genre"));
				sseason.setImgURL((String)actual.get("Poster"));
				sseason.setPlotSummary((String)actual.get("Plot"));
				sseason.setRating(new Double(actual.get("imdbRating").toString()));			
				
				sseason.setSeason(new Integer(actual.get("Season").toString()));
				sseason.setEpisodes(eps);
				
				System.out.println("sseason: "+sseason.getTitle());
						this.aLib.put(nodeTitle, sseason);
						
				//System.out.println(saveLibraryToFile().toString());
						
					}
			}
			catch(Exception e) { 
				e.printStackTrace();
			 }	
		}
		else{
			System.out.println("shouldn't be here");
		}
	}

	//Returns a string arraylist in format of "SHOW TITLE - SHOW SEASON", which acts as the keys for the aLib hash
	public ArrayList<String> getSeriesSeason(){
		ArrayList<String> retVal = new ArrayList<>();		
		Enumeration keys = this.aLib.keys();

		while (keys.hasMoreElements()){
			retVal.add((String)keys.nextElement());
		}
	
		return retVal;
	}

	//returns the SeriesSeason object that corresponds to the key provided
	public SeriesSeason getSeriesSeason(String title){
		return this.aLib.get(title);
	}
	
	//Adds a SeriesSeason object to the aLib hash, using the "SHOW TITLE - SHOW SEASON" as key
	public boolean addSeriesSeason(SeriesSeason seriesSeason){
		try{
			System.out.println("Adding "+seriesSeason.getTitle()+" - Season "+seriesSeason.getSeason());
			this.aLib.put(seriesSeason.getTitle()+" - Season "+seriesSeason.getSeason(), seriesSeason);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	//Removes a SeriesSeason object that corresponds to the key provided
	public boolean removeSeriesSeason(String title){
		try{
			System.out.println("Removing "+title);
			this.aLib.remove(title);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	//Helper method for getting Iterator of keyvalues
	private Iterator<String> getKeys(){
		return this.aLib.keySet().iterator();	
	}
	
	//method for resetting aLib object
	public void clears(){
		this.aLib = new Hashtable<String, SeriesSeason>();
	}

	//get JSON of current library
	public JSONObject getLibrary(){
		JSONObject obj = new JSONObject();
		Iterator<String> keys = getKeys();
		
		//iterate through titles
		while (keys.hasNext()){
			JSONObject subObj = new JSONObject();
			//title
			String key = keys.next();
			
			//for each key of the SeriesSeason object serialize properties
			subObj.put("Title", this.aLib.get(key).getTitle());
			subObj.put("Season", this.aLib.get(key).getSeason());
			subObj.put("imdbRating", this.aLib.get(key).getRating());
			subObj.put("Genre", this.aLib.get(key).getGenre());
			subObj.put("Poster", this.aLib.get(key).getImgURL());
			subObj.put("Plot", this.aLib.get(key).getPlotSummary());
			//JSONArray eps = new JSONArray(this.aLib.get(key).getEpisodes().toArray());
			ArrayList<Episode> epis = this.aLib.get(key).getEpisodes();
			JSONObject[] jEps = new JSONObject[epis.size()];
			for (int i = 0; i < epis.size(); i++){
				JSONObject s = new JSONObject();
				s.put("Title",epis.get(i).getTitle());
				s.put("imdbRating",epis.get(i).getImdbRating());
				s.put("Episode",epis.get(i).getEpisode());
				jEps[i] = s;
			}
			subObj.put("Episodes", jEps);
			obj.put(key, subObj);
		}
		return obj;
	}
	
	//create JSON of Library
	public boolean saveLibraryToFile(){
		System.out.println("Saving Library to seriesTest.json...");
		boolean saveres = false;
		
		JSONObject obj = getLibrary(); 
		
		try {
			FileWriter file = new FileWriter("seriesTest.json");//SAVE TO seriesTest.json
			file.write(obj.toString());
			file.flush();
			saveres = true;
		}
		catch(Exception e) {
			System.out.println("Error saving, "+e.getMessage());
		}
		System.out.println("Saving to seriesTest.json"+(saveres ? "succeeded" : "failed"));
		return saveres;

		
	}


	//REFACTORED - Restores from JSON file in server-side application's root directory, returns true if successful
	public boolean restoreLibraryFromFile(){
		
		System.out.println("Restoring Library from seriesTest.json...");
		boolean resRes = false;
		
		try {
			InputStream i = new FileInputStream(new File("seriesTest.json"));
			JSONObject series = new JSONObject(new JSONTokener(i));
			Iterator<String> keys = series.keys();
			
			//create a SeriesSeason Object per JSONObject
			while (keys.hasNext()){
				String nodeTitle = keys.next();
				JSONObject actual = series.optJSONObject(nodeTitle);
				SeriesSeason sseason = new SeriesSeason();
				JSONArray epObjs = actual.getJSONArray("Episodes");
				ArrayList<Episode> eps = new ArrayList<>();
				if (epObjs != null) {
					for (int ik = 0 ; ik < epObjs.length(); ik++){
						String epTitle;
						int epNum;
						double epRating;
						JSONObject jEp = epObjs.getJSONObject(ik);
						epTitle = (String)jEp.get("Title");
							
						epNum = new Integer(jEp.get("Episode").toString());
						epRating = new Double(jEp.get("imdbRating").toString());
					
						Episode ep = new Episode(epTitle, epNum, epRating);
						eps.add(ep);
						
					}			
				}
				sseason.setTitle((String)actual.get("Title"));
				sseason.setGenre((String)actual.get("Genre"));
				sseason.setImgURL((String)actual.get("Poster"));
				sseason.setPlotSummary((String)actual.get("Plot"));
				sseason.setRating(new Double(actual.get("imdbRating").toString()));			
				sseason.setSeason(new Integer(actual.get("Season").toString()));
				sseason.setEpisodes(eps);
				
				
				try {
					addSeriesSeason(sseason);
				}
				catch (Exception er) {er.printStackTrace();}
				
			}
			resRes = true; 
		}
		catch (Exception dl) {
			dl.printStackTrace();
		}
		
		System.out.println("Library restore "+(resRes ? "successful!" : "failed"));
		return resRes;
	}

	// public static void main(String args[]){
	// 	try { 
	// 		String hostId="192.168.1.60";
	// 		String regPort="2020";
	// 		if(args.length >= 2){
	// 			hostId = args[0];
	// 			regPort = args[1];
	// 		}

	// 		//Bind library object
	// 		SeriesLibrary obj = new SeriesLibraryImpl(true);
	// 		Naming.bind("rmi://"+hostId+":"+regPort+"/SeriesLibrary", obj);
	// 		System.out.println("Server bound in registry as: "+
	// 		"rmi://"+hostId+":"+regPort+"/SeriesLibrary");
	// 	}
	// 	catch(Exception e){
	// 		e.printStackTrace();
	// 	}
	// }


}

