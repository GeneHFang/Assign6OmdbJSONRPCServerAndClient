package ser321.sockets;

import java.net.*;
import java.io.*;

/**
 * Copyright 2020 Tim Lindquist,
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
 * A class to demonstrate a simple client-server connection using sockets.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2020
 */
public class SockServer {
  public static void main (String args[]) {
    Socket sock;
    try {
      ServerSocket serv = new ServerSocket(8888);
      System.out.println("Server ready for 3 connections");
      for (int rep = 0; rep < 3; rep++){
      System.out.println("Server waiting for a connection");
      sock = serv.accept();
      ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
      String s = (String) in.readObject();
      System.out.println("Received the String "+s);
      Integer i = (Integer) in.readObject();
      System.out.println("Received the Integer "+ i);
      }
    } catch(Exception e) {e.printStackTrace();}
  }
}
