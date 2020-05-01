package ser321.assign6.ghli1;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
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
 * A class for client-server connections with a threaded server based on class 
 * example.
 * The Series Library client proxy implements the server methods
 * by marshalling/unmarshalling parameters and results and using a TCP
 * connection to request the method be executed on the server.
 * Byte arrays are used for communication to support multiple langs.
 *
 * @author Tim Lindquist 
 *          Gene H. Li ghli1@asu.edu
 *          ASU Polytechnic Department of Engineering
 * @version April 2020
 */

public class SeriesSeasonTcpProxy extends Object implements SeriesLibrary {
    private static final boolean debugOn = false;
    private static final int buffSize = 65536;
    private static int id = 0;
    private String host;
    private int port;

    public SeriesSeasonTcpProxy (String host, int port){
        this.host = host;
        this.port = port;
    }

    private void debug(String message) {
        if (debugOn) {
            System.out.println("debug: "+message);
        }
    }

    public String callMethod(String method, Object[] params){
        JSONObject theCall = new JSONObject();
        String ret = "{}";
        try{
           debug("Request is: "+theCall.toString());
           theCall.put("method",method);
           theCall.put("id",id);
           theCall.put("jsonrpc","2.0");
           ArrayList<Object> al = new ArrayList();
           for (int i=0; i<params.length; i++){
              al.add(params[i]);
           }
           JSONArray paramsJson = new JSONArray(al);
           theCall.put("params",paramsJson);
           Socket sock = new Socket(host,port);
           OutputStream os = sock.getOutputStream();
           InputStream is = sock.getInputStream();
           int numBytesReceived;
           int bufLen = 65536;
           String strToSend = theCall.toString();
           byte bytesReceived[] = new byte[buffSize];
           byte bytesToSend[] = strToSend.getBytes();
           os.write(bytesToSend,0,bytesToSend.length);
           numBytesReceived = is.read(bytesReceived,0,bufLen);
           ret = new String(bytesReceived,0,numBytesReceived);
           debug("callMethod received from server: "+ret);
           os.close();
           is.close();
           sock.close();
        }catch(Exception ex){
           System.out.println("exception in callMethod: "+ex.getMessage());
        }
        return ret;
     }

    public boolean saveLibraryToFile(){
        boolean ret = false;
        String result = callMethod("saveLibraryToFile", new Object[]{});
        JSONObject res = new JSONObject(result);
        ret = res.optBoolean("result",false);
        return ret;
    }

    public JSONObject getLibrary(){
        String result = callMethod("getLibrary", new Object[]{});
        JSONObject res = new JSONObject(result);
        JSONObject libJson = res.optJSONObject("result");
        return libJson;
    }

    public boolean createNewFromJson(JSONObject series){
        boolean ret = false;
        String result = callMethod("createNewFromJson",new Object[]{});
        JSONObject res = new JSONObject(result);
        ret = res.optBoolean("result",false);
        return ret;
    }

    public boolean restoreLibraryFromFile(){
        boolean ret = false;
        String result = callMethod("restoreLibraryFromFile", new Object[]{});
        JSONObject res = new JSONObject(result);
        ret = res.optBoolean("result",false);
        return ret;
    }

    public boolean addSeriesSeason(SeriesSeason ss){
        boolean ret = false;
        String result = callMethod("addSeriesSeason", new Object[]{ss.toJson()});
        JSONObject res = new JSONObject(result);
        ret = res.optBoolean("result",false);
        return ret;
    }

    public boolean removeSeriesSeason(String key){
        boolean ret = false;
        String result = callMethod("removeSeriesSeason", new Object[]{key});
        JSONObject res = new JSONObject(result);
        ret = res.optBoolean("result",false);
        return ret;
    }

    public SeriesSeason getSeriesSeason(String key){
        SeriesSeason ret = new SeriesSeason();
        String result = callMethod("getSeriesSeason", new Object[]{key});
        JSONObject res = new JSONObject(result);
        JSONObject ssJson = res.optJSONObject("result");
        ret = new SeriesSeason(ssJson);
        return ret;
    }

    public ArrayList<String> getSeriesSeason(){
        ArrayList<String> ret = new ArrayList<>();
        String result = callMethod("getSeriesSeasons", new Object[0]);
        JSONObject res = new JSONObject(result);
        JSONArray ssJson = res.optJSONArray("result");
        for (int i=0; i<ssJson.length(); i++){
           ret.add(ssJson.optString(i,"unknown"));
        }
        return ret;
    }




}