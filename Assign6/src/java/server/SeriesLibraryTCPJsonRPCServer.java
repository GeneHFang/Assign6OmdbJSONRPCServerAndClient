package ser321.assign6.ghli1;

import java.net.*;
import java.io.*;
import java.util.*;


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
 * A class for TCP client-server connections with a threaded server that
 * implements JsonRPC method calls for a collection of SeriesSeasons, based on class example.
 *
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Gene H. Li ghli1@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * 
 * @version April 2020
 */

public class SeriesLibraryTCPJsonRPCServer extends Thread{
    private Socket conn;
    private int id;
    private SeriesLibrarySkeleton skeleton;


    public SeriesLibraryTCPJsonRPCServer (Socket sock, int id,
                                            SeriesLibrary ss) {
        this.conn = sock;
        this.id = id;
        skeleton = new SeriesLibrarySkeleton(ss);
    }

    public void run() {
        try {
            OutputStream outSock = conn.getOutputStream();
            InputStream inSock = conn.getInputStream();
            byte clientInput[] = new byte[1024]; // up to 1024 bytes in a message.
            int numr = inSock.read(clientInput,0,1024);
            if (numr != -1) {
            //System.out.println("read "+numr+" bytes");
            String request = new String(clientInput,0,numr);
            System.out.println("request is: "+request);
            String response = skeleton.callMethod(request);
            byte clientOut[] = response.getBytes();
            outSock.write(clientOut,0,clientOut.length);
            System.out.println("response is: "+response);
            }
            inSock.close();
            outSock.close();
            conn.close();
        } catch (IOException e) {
            System.out.println("I/O exception occurred for the connection:\n"+e.getMessage());
        }
    }

    public static void main (String args[]) {
        Socket sock;
        SeriesLibrary ss = new SeriesLibraryImpl(true);
        int id=0;
        try {
           if (args.length != 1) {
              System.out.println("Usage: java ser321.assign6.ghli1.server."+
                                 "SeriesLibraryTCPJsonRPCServer [portNum]");
              System.exit(0);
           }
           int portNo = Integer.parseInt(args[0]);
           if (portNo <= 1024) portNo=8888;
           ServerSocket serv = new ServerSocket(portNo);
           // accept client requests. For each request create a new thread to handle
           while (true) { 
              System.out.println("SeriesLibrary server waiting for connects on port "
                                 +portNo);
              sock = serv.accept();
              System.out.println("SeriesLibrary server connected to client: "+id);
              SeriesLibraryTCPJsonRPCServer myServerThread =
                 new SeriesLibraryTCPJsonRPCServer(sock,id++,ss);
              myServerThread.start();
           }
        } catch(Exception e) {e.printStackTrace();}
     }

}