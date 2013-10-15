package org.saJava.main;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import org.saJava.GUI.ConnectProg;
import org.saJava.GUI.Error;
import org.saJava.GUI.launchUI;
import org.saJava.connect.WizCON;
import org.saJava.connect.WizOUT;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/27/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    private static File f;
    private static FileChannel channel;
    private static FileLock lock;

    public static void main(String args[])throws IOException {

        /*try {
            f = new File("RingOnRequest.lock");
            // Check if the lock exist
            if (f.exists()) {
                // if exist try to delete it
                f.delete();
            }

            // Try to get the lo

            channel = new RandomAccessFile(f, "rw").getChannel();
            lock = channel.tryLock();
            if(lock == null)
            {
                // File is lock by other application
                channel.close();
                throw new RuntimeException("Only 1 instance of MyApp can run.");
            }
            // Add shutdown hook to release lock when application shutdown
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            //Your application tasks here..
            System.out.println("Running");

        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not start process.", e);
        }       */

        try{
            ServerSocket socket =
                    new ServerSocket(9999,10,InetAddress.getLocalHost());

            javax.swing.SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    //JFrame.setDefaultLookAndFeelDecorated(true);
                /*BigDecimal version = new BigDecimal(System.getProperty("java.specification.version"));

                if (version.compareTo(new BigDecimal("1.7")) == 0) {
                    System.out.println("1.7");
                    JOptionPane.showMessageDialog(new Frame(),"OK");
                }
                else if (version.compareTo(new BigDecimal("1.6")) == 0) {
                    System.out.println("1.6");
                } */


                    try {
                        UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
                    }
                    catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    new ConnectProg().setVisible(true);

                }
            });

        }catch(java.net.BindException b){
            System.out.println("Already Running...");
            javax.swing.SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    //JFrame.setDefaultLookAndFeelDecorated(true);
                /*BigDecimal version = new BigDecimal(System.getProperty("java.specification.version"));

                if (version.compareTo(new BigDecimal("1.7")) == 0) {
                    System.out.println("1.7");
                    JOptionPane.showMessageDialog(new Frame(),"OK");
                }
                else if (version.compareTo(new BigDecimal("1.6")) == 0) {
                    System.out.println("1.6");
                } */


                    try {
                        UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
                    }
                    catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    new Error().display();

                }
            });
        }catch(Exception e){
            System.out.println(e.toString());
        }



    }

    /*public static void unlockFile() {
        // release and delete file lock
        try {
            if(lock != null) {
                lock.release();
                channel.close();
                f.delete();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static class ShutdownHook extends Thread {

        public void run() {
            unlockFile();
        }

    }       */
}
