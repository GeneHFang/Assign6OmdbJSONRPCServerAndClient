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
 * A class to demonstrate receiving a datagram packet.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 */
class DatagramReceive {
  public static void main (String args[]) {
    try {
      byte buffer[] = new byte[1024];
      DatagramPacket pack = new DatagramPacket(buffer, 1024);
      DatagramSocket sock = new DatagramSocket(8889);
      sock.receive(pack);
      buffer = pack.getData();
      System.out.println("Received " +
		 new String(pack.getData(),pack.getOffset(),pack.getLength()));
      sock.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
