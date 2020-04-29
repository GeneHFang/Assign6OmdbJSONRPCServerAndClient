package ser321.sockets;

import java.net.*;
import java.util.*;

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
public class GetNameForIP {

    public static void main (String args[]) {
	if(args.length < 1)
	    System.out.println(
	       "usage: java ser321.sockets.GetNameForIP 129.219.40.47");
        else
  	    try{
		StringTokenizer st = new StringTokenizer(args[0], ".");
		byte ip[] = new byte[4];
		int i=0;
		while (st.hasMoreElements()){
		    String seg = (String)st.nextElement();
		    int next = Integer.parseInt(seg);
		    if (next > 127) {
			next = next - 256;
		    }
		    ip[i] = (new Integer(next)).byteValue();
		    i++;
		}
		System.out.println("Domain name for IP: "+args[0]+" is: "
                                   +(InetAddress.getByAddress(ip))
                                   .getHostName());
	    }catch (Exception e){
	        System.out.println(e.getMessage());
	    }
    }
}
