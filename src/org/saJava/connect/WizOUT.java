package org.saJava.connect;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.saJava.preferences.UserSettings;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/27/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizOUT {

       public static boolean disConnect()throws IOException{
           Connection.Response response=null;
           try {

               if(WizCON.checkCon())
               {
                   System.out.println("You have already Logged out manually.");
                   WizCON.message="mLogout";
                   return false;
               }
                WizCON.message="";
               UserSettings userSettings=new UserSettings();
               userSettings.setPreference();
               userSettings.prefs.put(userSettings.pSession,"");

               response= Jsoup.connect("http://192.168.183.201:9082/logoutUI.do3?sessUserName="+userSettings.prefs.get(userSettings.pUname,"")).timeout(20000)
                       .method(Connection.Method.GET).execute();

               return true;

           }
           catch(NoRouteToHostException e){
               System.out.println("RJ45 Disconnected.Please Check");
               WizCON.message="noRoute";
               System.out.println(e.getMessage());
               return false;
           }
           catch (ConnectException e)
           {
               System.out.println("Connection Exception: "+e.getMessage());
               return false;
           }
           catch (SocketException e){
               System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
               WizCON.message="noEthernet";
               System.out.println(e.getMessage());
               return false;
           }
           catch(SocketTimeoutException e){
               System.out.println("SocketTimeOut Occured");
               WizCON.message="connectTimeOut";
               return false;
           }
           catch (Exception e) {
               System.out.println("Logged Out Successfully");
               System.out.println(e.getMessage());
               return true;
           }
       }
}
