package ser321.tcpjsonrpc;

import java.io.*;
import java.util.*;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

import ser321.tcpjsonrpc.server.Student;
import ser321.tcpjsonrpc.server.StudentCollection;
import ser321.tcpjsonrpc.StudentTcpProxy;

/**
 * Copyright (c) 2020 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p/>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p/>
 * Please review the GNU General Public License at:
 * http://www.gnu.org/licenses/gpl-2.0.html
 * see also: https://www.gnu.org/licenses/gpl-faq.html
 * so you are aware of the terms and your rights with regard to this software.
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p/>
 * Purpose:
 * A Java class and main method demonstrating an approach to calling
 * JsonRpc methods where argument and return types are user-defined classes,
 * and communication between client and server is done using TCP/IP sockets.
 *
 * @author Tim Lindquist (tim.lindquist@asu.edu), ASU Software Engineering
 * @version April 2020
 * @license See above
 */
public class StudentCollectionClient extends Object {

   static final boolean debugOn = false;

   private static void debug(String message) {
      if (debugOn)
         System.out.println("debug: "+message);
   }

   public static void main(String args[]) {

      String host = "localhost";
      String port = "8080";
      
      try {
         if(args.length >= 2){
            host = args[0];
            port = args[1];
         }
         String url = "http://"+host+":"+port+"/";
         System.out.println("Opening connection to: "+url);
         StudentTcpProxy sc = (StudentTcpProxy)new StudentTcpProxy(host, Integer.parseInt(port));
         BufferedReader stdin = new BufferedReader(
            new InputStreamReader(System.in));
         System.out.print("Enter end or {add|get|getNameById|getNames|remove} followed by args>");
         String inStr = stdin.readLine();
         StringTokenizer st = new StringTokenizer(inStr);
         String opn = st.nextToken();
         while(!opn.equalsIgnoreCase("end")) {
            if(opn.equalsIgnoreCase("add")){
               String name = "";
               while(st.hasMoreTokens()){
                  name = name + st.nextToken();
                  if(st.hasMoreTokens()) name = name + " ";
               }
               Student aStud = new Student(name,7,new String[]{"Ser423","Ser321"});
               boolean result = sc.add(aStud);
               System.out.println("Add "+aStud.name+" result "+result);
            }else if (opn.equalsIgnoreCase("get")) {
               String name = "";
               while(st.hasMoreTokens()){
                  name = name + st.nextToken();
                  if(st.hasMoreTokens()) name = name + " ";
               }
               Student result = sc.get(name);
               System.out.println("Got "+result.toString());
            }else if (opn.equalsIgnoreCase("getNames")) {
               String[] result = sc.getNames();
               System.out.print("The collection has entries for: ");
               for (int i = 0; i < result.length; i++){
                  System.out.print(result[i]+", ");
               }
               System.out.println();
            }else if (opn.equalsIgnoreCase("remove")) {
               String name = st.nextToken();
               while(st.hasMoreTokens()){
                  name = name + " " + st.nextToken();
               }
               boolean result = sc.remove(name);
               System.out.println("remove "+name+" result "+result);
            }else if (opn.equalsIgnoreCase("getNamebyid")) {
               int idNo = Integer.parseInt(st.nextToken());
               String result = sc.getNameById(idNo);
               System.out.println(result+" has id number "+idNo);
            }
            System.out.print("Enter end or {add|get|getNameById|getNames|remove} followed by args>");
            inStr = stdin.readLine();
            st = new StringTokenizer(inStr);
            opn = st.nextToken();
         }
      }catch (Exception e) {
         e.printStackTrace();
         System.out.println("Oops, you didn't enter the right stuff");
      }
   }
}
