package org.saJava.GUI;

import javax.swing.*;
import java.net.ServerSocket;

/**
 * Created with IntelliJ IDEA.
 * User: Arun
 * Date: 18/8/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Error extends JDialog {

     public void display(){

         setVisible(true);
         setBounds(600,600,1,1);

         ImageIcon icon=new ImageIcon(getClass().getResource("image/error.png"));
         JOptionPane.showMessageDialog(this,"Instance already running.Please check systray","Oops..",JOptionPane.INFORMATION_MESSAGE,icon);
     }
}
