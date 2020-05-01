

package ser321.assign6.ghli1;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;


import org.json.JSONArray;

/**
 * Copyright 2020 Tim Lindquist, Gene H. Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * A class for client-server connections with a threaded server based off of class example.
 * SeriesLibrary server creates a server socket.
 * When a client request arrives, which should be a JsonRPC request, a new
 * thread is created to service the call and create the appropriate response.
 * Byte arrays are used for communication to support multiple langs.
 *
 * @author Tim Lindquist 
 *          Gene H. Li (ghli1@asu.edu)
 *          ASU Polytechnic Department of Engineering
 * @version April 2020
 */


public class SeriesLibrarySkeleton extends Object {
    private static final boolean debugOn = false; 
    ser321.assign6.ghli1.SeriesLibrary seriesLib;

    public SeriesLibrarySkeleton(SeriesLibrary lib){
        this.seriesLib = lib;
    }

    public void debug(String msg){
        if (debugOn) {
            System.out.println("debug: "+msg);
        }
    }

    public String callMethod(String request){
        JSONObject result = new JSONObject();
        try{
           JSONObject theCall = new JSONObject(request);
           debug("Request is: "+theCall.toString());
           String method = theCall.getString("method");
           int id = theCall.getInt("id");
           JSONArray params = null;
           if(!theCall.isNull("params")){
              params = theCall.getJSONArray("params");
           }
           result.put("id",id);
           result.put("jsonrpc","2.0");


           if(method.equals("addSeriesSeason")){
              JSONObject ss = params.getJSONObject(0);
              //Might not work,  will have to do workaround if so.
              SeriesSeason toAdd = new SeriesSeason(ss);
              debug("adding SeriesLibrary: "+toAdd.toJson().toString());
              seriesLib.addSeriesSeason(toAdd);
              result.put("result",true);
           }
            else if(method.equals("removeSeriesSeason")){
              String ssName = params.getString(0);
              debug("removing SeriesSeason named "+ssName);
              seriesLib.removeSeriesSeason(ssName);
              result.put("result",true);
           }
            else if(method.equals("getLibrary")){
               JSONObject libJson = seriesLib.getLibrary();
               result.put("result",libJson);
            }
            else if(method.equals("getSeriesSeason")){
              String ssTitle = params.getString(0);
              debug("get SeriesSeason named: "+ssTitle);
              SeriesSeason ss = seriesLib.getSeriesSeason(ssTitle);
              JSONObject ssJson = ss.toJson(); 
              result.put("result",ss);
           }else if(method.equals("saveLibraryToFile")){
            boolean res = seriesLib.saveLibraryToFile();
            debug("Saving library to Server.. ");
            result.put("result",res);
           }else if(method.equals("restoreLibraryFromFile")){
            boolean res = seriesLib.restoreLibraryFromFile();
            debug("Saving library to Server.. ");
            result.put("result",res);
           }else if(method.equals("getSeriesSeasons")){
              ArrayList<String> names = seriesLib.getSeriesSeason();
              JSONArray resArr = new JSONArray();
              for (int i=0; i<names.length; i++){
                 resArr.put(names.get(i));
              }
              debug("getNames request found: "+resArr.toString());
              result.put("result",resArr);
           }else{
              debug("Unable to match method: "+method+". Returning 0.");
              result.put("result",0.0);
           }
        }catch(Exception ex){
           System.out.println("exception in callMethod: "+ex.getMessage());
        }
        return result.toString();
     }

}
