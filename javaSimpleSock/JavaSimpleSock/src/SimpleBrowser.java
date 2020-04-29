package ser321.sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
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
 * A class to demonstrate Java's support for http socket connections.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 */
public class SimpleBrowser extends JFrame implements ActionListener {

    private final static String home = "http://quay.poly.asu.edu/Ser321";
    private JTextField urlStrField;
    JEditorPane displayPane;
    public SimpleBrowser (String urlString){
	//set up a file menu with one submenu and an exit menuItem
	JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
	menu.setMnemonic(KeyEvent.VK_F);
	menu.setToolTipText("Use file menu to display new page or exit");

        String menuIs [] = {"Display", "Exit"};
        JMenuItem mi;
        for(int i = 0; i < menuIs.length; i++){
	    mi = new JMenuItem(menuIs[i]);
	    mi.addActionListener(this);
  	    menu.add(mi);
	}
	menuBar.add(menu);

	//add a tree in a scroll pane
        displayPane = new JEditorPane();
        displayPane.setEditable(false);
        JScrollPane sourceScrollPane = new JScrollPane(displayPane);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sourceScrollPane, BorderLayout.CENTER);

        JPanel jp = new JPanel();
        JButton up = new JButton("Display");
        up.addActionListener(this);
        jp.add(up);
        String tmp = home;
        if(!urlString.equals(""))
	    tmp = urlString;
        urlStrField = new JTextField(tmp, 45);
	try{
	    displayPane.setPage(new URL(tmp));
        }catch (Exception e){
	    JOptionPane.showMessageDialog(this,
                                        "URL unreachable " + e.getMessage());
	}
        jp.add(urlStrField);
        getContentPane().add(jp, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Exit"))
	   System.exit(0);
        else if (e.getActionCommand().equals("Display"))
	    try {
  	       displayPane.setPage(new URL(urlStrField.getText()));
            }catch (Exception ex){
		JOptionPane.showMessageDialog(this,
                                        "URL unreachable " + ex.getMessage());
	    }
    }

    public static void main(String args[]){
	String inStr = "";
	if(args.length>=1){
	    inStr = args[0];
	}
	SimpleBrowser window = new SimpleBrowser(inStr);
        window.setSize(600,600);
        window.setVisible(true);
    }
}
