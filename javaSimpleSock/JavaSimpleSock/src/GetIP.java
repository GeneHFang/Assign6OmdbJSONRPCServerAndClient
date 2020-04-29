package ser321.sockets;

import java.net.*;

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
 * A class to demonstrate Java's support for internet addresses.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 */
public class GetIP {

    public static void main (String args[]) {
	if(args.length < 1)
	    System.out.println(
	       "usage: java cst420.sockets.GetIP quay.poly.asu.edu");
        else
  	    try{
	      System.out.println(InetAddress.getByName(
                                                    args[0]).getHostAddress());
	      byte ip[] = InetAddress.getByName(args[0]).getAddress();
	      for (int i=0; i<4; i++){
		  System.out.println(new Byte(ip[i]).toString());
	      }
	    }catch (Exception e){
	        System.out.println(e.getMessage());
	    }
    }
}
