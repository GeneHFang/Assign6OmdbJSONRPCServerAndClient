package ser321.tcpjsonrpc.server;

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
 * Purpose: StudentCollection defines the interface to the server operations
 *
 * Ser321 Distributed Apps, and Ser423 Mobile Apps
 * see http://pooh.poly.asu.edu/Mobile
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 */
public interface StudentCollection {
   public boolean saveToJsonFile();
   public boolean resetFromJsonFile();
   public boolean add(Student stud);
   public boolean remove(String aName);
   public Student get(String aName);
   public String getNameById(int id);
   public String[] getNames();
}
