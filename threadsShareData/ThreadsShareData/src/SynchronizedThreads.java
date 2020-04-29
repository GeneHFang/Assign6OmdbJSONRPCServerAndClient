package ser321.threads;

/**
 * Copyright (c) 2020 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p>
 * Please review the GNU General Public License at:<br>
 * <a href="http://www.gnu.org/licenses/gpl-2.0.html">
 * http://www.gnu.org/licenses/gpl-2.0.html</a> and the FAQs at:<br>
 * <a href="https://www.gnu.org/licenses/gpl-faq.html">
 * https://www.gnu.org/licenses/gpl-faq.html</a>,<br>
 * so you are aware of the terms and your rights with regard to this software.
 * <p>
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p>
 * Purpose:
 * A program to demonstrate threads with a synchronized shared object in java.
 *
 * <p>
 * Ser321 Principles of Distributed Software Systems
 * @see <a href="http://pooh.poly.asu.edu/Ser321">Ser321 Home Page</a>
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering
 *                       Ira Fulton Schools of Engineering, ASU Polytechnic
 * @version April, 2020
 */
public class SynchronizedThreads {
    public static void main(String args[]){
        // create a data object to be shared among the 4 threads.
	ShareableData theDataItem = new ShareableData(25);
        // start up 4 more threads in this program
	for (int i=1; i <= 4; i++){
	    AThread t = new AThread(i, theDataItem);
	    t.start();
	}
    }
}

/*
 * Exercise. Question, how do we know that the 4 threads are blocked
 * from executing either access or mutate methods 
 * while another of the 4 threads are executing them?
 * Modify the code below by adding println's and sleep's
 * in such a way that demonstrates that the threads are blocked from 
 * entering the methods while another thread is executing them.
 */
class AThread extends Thread {
    private int id;
    private ShareableData theData;
    public AThread(int newId, ShareableData sd){
	id = newId;
	theData = sd;
    }

    public void run(){
	System.out.println("Started thread #" + id);
	try{
	    for (int count = 0; count < 3; count++){
		if(id==1 && count>0) sleep(1); //delay the first thread
                if(count<2){
                   theData.access(id, count);
                } else {
                   theData.increment(id,count);
                }
		yield();
	    }
	}
	catch (Exception e) {e.printStackTrace();}
    }
}

class ShareableData {
    private int myData;

    public ShareableData(int theValue){
        myData = theValue;
    }

    synchronized public void access(int who, int count){
	System.out.println("Shareable data with value " + myData
                         + " accessed by thread " + who + " count is " + count);
    }

    synchronized public void increment(int who, int count){
       myData = myData + 1;
	System.out.println("Shareable data with value " + myData
                        + " changed  by thread " + who + " count is " + count);
    }
}
