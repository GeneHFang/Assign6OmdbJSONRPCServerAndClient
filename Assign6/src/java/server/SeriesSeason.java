

 package ser321.assign6.ghli1;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;


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
 * Purpose: SeriesSeason is the Java Object that stores the series and season info fetched from omdb JSON response.
 *
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Gene H. Li ghli1@asu.edu
 *	   Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version March 2020
 */


public class SeriesSeason extends Object implements java.io.Serializable {

	private String title;
	private int season;
	private double rating;
	private String genre;
	private String imgURL;
	private String plotSummary;
	private ArrayList<Episode> episodes;
	
	public SeriesSeason(){
	}
	//didn't intend to make this constructor, but I can't get the JSONObject constructor to work for reason
	public SeriesSeason(String title, int season, double rating, String genre, String imgURL, String plotSummary, ArrayList<Episode> episodes){
		this.title = title;
		this.season = season;
		this.rating = rating;
		this.genre = genre;
		this.imgURL = imgURL;
		this.plotSummary = plotSummary;
		this.episodes = episodes;
	}
	//Creates a SeriesSeason object from a JSONObject. Doesn't work. Implemented workaround in MediaLibraryApp.java
	public SeriesSeason(JSONObject json){
		try{
			JSONObject seriesObj = json;
			JSONArray epObjs = seriesObj.getJSONArray("Episodes");
			
			
			ArrayList<Episode> eps = new ArrayList<>();
			if (epObjs != null) {
				for (int i = 0 ; i < epObjs.length(); i++){
					String epTitle;
					int epNum;
					double epRating;
					JSONObject jEp = epObjs.getJSONObject(i);
					System.out.println("This is the episode: "+jEp.toString());
					epTitle = jEp.getString("Title");
					epNum = (jEp.getInt("Episode"));
					epRating = (jEp.getDouble("imdbRating"));	
				
					Episode ep = new Episode(epTitle, epNum, epRating);
					eps.add(ep);
					
				}			
			}
			
			this.title = seriesObj.getString("Title");
			this.genre = seriesObj.getString("Genre");
			this.imgURL = seriesObj.getString("Poster");
			this.plotSummary = seriesObj.getString("Plot");
			this.rating = (seriesObj.getDouble("imdbRating"));			
			
			this.season = (seriesObj.getInt("Season"));
			
			this.episodes = eps;
		}
		catch(Exception e){
			System.out.println("Failing inside this constructor: "+ e.getMessage());
		}
	}

	//Converts SeriesSeason Object to JSON]
	public JSONObject toJson(){
		JSONObject obj = new JSONObject();

		
		//for each key of the SeriesSeason object serialize properties
		obj.put("Title", this.title);
		obj.put("Season", this.season);
		obj.put("imdbRating", this.rating);
		obj.put("Genre", this.genre);
		obj.put("Poster", this.imgURL);
		obj.put("Plot", this.plotSummary);
		//JSONArray eps = new JSONArray(this.Episodes().toArray());
		ArrayList<Episode> epis = this.episodes;
		JSONObject[] jEps = new JSONObject[epis.size()];
		for (int i = 0; i < epis.size(); i++){
			JSONObject s = new JSONObject();
			s.put("Title",epis.get(i).getTitle());
			s.put("imdbRating",epis.get(i).getImdbRating());
			s.put("Episode",epis.get(i).getEpisode());
			jEps[i] = s;
		}
		obj.put("Episodes", jEps);

		return obj;
	}

	
	//Getters
	public String getTitle(){
		return this.title;
	}
	public int getSeason(){
		return this.season;
	}
	public double getRating(){
		return this.rating;
	}
	public String getGenre(){
		return this.genre;
	}
	public String getImgURL(){
		return this.imgURL;
	}
	public String getPlotSummary(){
		return this.plotSummary;
	}
	public ArrayList<Episode> getEpisodes(){
		return this.episodes;	
	}
	public String getSeriesAndSeason(){
		return this.title+" - Season "+this.season;	
	}
	
	//Setters
	public void setTitle(String title){
		this.title = title;
	}
	public void setSeason(int season){
		this.season = season;
	}
	public void setRating(double rating){
		this.rating = rating;
	}
	public void setGenre(String genre){
		this.genre = genre;
	}
	public void setImgURL(String imgURL){
		this.imgURL = imgURL;
	}
	public void setPlotSummary(String plotSummary){
		this.plotSummary = plotSummary;
	}
	public void setEpisodes(ArrayList<Episode> episodes){
		this.episodes = episodes;	
	}
}

