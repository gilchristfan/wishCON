package org.saJava.connect;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.saJava.preferences.UserSettings;


import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/27/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizCON {
    public static String username,lastname,diff,expiry,message="";
    public static boolean status=true;
    static String cookieVal=null;
    public static ArrayList<String> months,rowData;
    public static String sessions,dLoad,uLoad,time;

    public static void connect(String uname,String passwd){

        try {
            //Connnecting to the main server login page
            Connection.Response response= Jsoup.connect("http://192.168.183.201/WISHN/Login.jsp").method(Connection.Method.GET)
                    .execute();

            //Getting the all important JSESSIONID
            cookieVal=response.cookie("JSESSIONID");
            System.out.println(cookieVal);
            System.out.println("Connecting to the main Server....");

            //Using Jsoup Connection to connect to the main login servlet by posting with JSESSIONID and credentials
            response=Jsoup.connect("http://192.168.183.201:9082/loginUI.do2").cookie("JSESSIONID",cookieVal)
                    .data("Username", uname,
                            "Password", passwd).timeout(20000).method(Connection.Method.POST).execute();
            System.out.println("Authentication successfully passed...");
        }
        catch (HttpStatusException e){
            System.out.println("HttpStatus Exception Occured.");
            System.out.println(e.getUrl());
            String validity[]=e.getUrl().split("=");
            System.out.println(validity[1]);
                if(validity[1].equals("All available plans are inactive/expired/having restricted service now.."))
                    WizCON.message="validityExpire";
                else
                    WizCON.message="errorValidate";

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server is temporarily Down.Please Try Later");
            WizCON.message="connectTimeOut";
        }
    }

    public static boolean getUserDetails()throws IOException{

        Connection.Response response= null;
        Document document=null,document1=null;
        try {
            response = Jsoup.connect("http://192.168.183.201:9082/WISHN/Profile.jsp").method(Connection.Method.GET).execute();
            document=response.parse();

        }  catch(NoRouteToHostException e){
            System.out.println(e.getMessage());
            message="noRoute";
            return false;
        }
        catch (ConnectException e){

            System.out.println("Already logged in");
            message="mLogin";
            System.out.println("Connection Failed: "+e.getMessage());
            return false;
        }
        catch (SocketException e){
            System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
            message="noEthernet";
            System.out.println(e.getMessage());
            return false;
        }
        catch (SocketTimeoutException e){
            message="connectTimeOut";
            System.out.println("Connection TImed out: "+e.getMessage());
            return false;
        }
        catch (Exception e) {
            //To change body of catch statement use File | Settings | File Templates.
            System.out.println("Already logged in");
            System.out.print(e.getMessage());
            return false;
        }

        try{
        username=new String(document.select(".labelRightPofile").first().toString());
        lastname=document.select(".labelRightPofile").get(1).toString();
        username=username.substring(username.indexOf(">")+1,username.lastIndexOf("<"))+" "+
                lastname.substring(lastname.indexOf(">")+1,lastname.lastIndexOf("<"));

        expiry=document.select(".labelRightPofile1").last().toString();
        expiry=expiry.substring(expiry.indexOf(">")+1,expiry.lastIndexOf("<"))+" 23:59:59";

        System.out.println("Welcome " + username);
        System.out.println("Expiry Date: "+expiry);

        timeRemaining(expiry);
        //launchBrowser();
            return true;
        }
        catch (NullPointerException e){
            System.out.println("Invalid Username or password. Please Check.");
            return false;
        }
    }

    static void timeRemaining(String expiry){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date now=new Date();
        String strDate = dateFormat.format(now);
        Date d1= null;
        try {
            d1 = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String endDate=expiry;
        Date d2=null;
        try{
            d2=dateFormat.parse(endDate);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        long timeDiff = Math.abs(d2.getTime() - d1.getTime());

        diff = String.format("%d day(s) %d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff)/24,
                TimeUnit.MILLISECONDS.toHours(timeDiff)-TimeUnit.MILLISECONDS.toHours(timeDiff)/24*24,
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        System.out.println("Time Left: "+diff);

    }
    public static void launchBrowser(){
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try{

            if (os.indexOf( "win" ) >= 0) {
                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + "https://www.google.co.in");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean changePasswd(String oldPass,String newPass,String cfNewPass)throws IOException{
        Connection.Response response= null;
        Document document=null,document1=null;
        try {
            UserSettings userSettings=new UserSettings();
            userSettings.setPreference();

            response=Jsoup.connect("http://192.168.183.201:9082/WISHN/PasswordChange.jsp").
                    method(Connection.Method.GET).execute();

            System.out.println("Password Change in Progress...");
            response = Jsoup.connect("http://192.168.183.201:9082/WISHN/PasswordChangeUI.do5").
                    data("oldpasswd",oldPass,"newpasswd",newPass,"cfnewpasswd",cfNewPass,
                    "userNameFromParent",userSettings.prefs.get(userSettings.pUname,""),
                    "zoneName","WISHN").method(Connection.Method.POST).timeout(20000)
                    .execute();
             System.out.println("Password Changed...");
            document=response.parse();
            return true;

        }  catch(NoRouteToHostException e){
            System.out.println(e.getMessage());
            message="noRoute";
            return false;
        }
        catch (ConnectException e){

            System.out.println("Already logged in");
            message="mLogin";
            System.out.println("Connection Failed: "+e.getMessage());
            return false;
        }
        catch (SocketException e){
            System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
            message="noEthernet";
            System.out.println(e.getMessage());
            return false;
        }
        catch (SocketTimeoutException e){
            message="connectTimeOut";
            System.out.println("Connection TImed out: "+e.getMessage());
            UserSettings settings=new UserSettings();
            settings.setPreference();
            if(WizOUT.disConnect()){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if(checkCon()){
                    do{
                        connect(settings.prefs.get(settings.pUname, ""), settings.prefs.get(settings.pPasswd, ""));
                    }while (message.equals("connectTimeOut"));
                  System.out.println(message);
                if(message.equals("errorValidate"))
                    message="passChanged";
                    else
                    message="connectTimeOut";

                    System.out.println(message);

                }
            }

           // e.printStackTrace();
            return false;
        }
        catch (HttpStatusException e) {
            //To change body of catch statement use File | Settings | File Templates.

            System.out.println("HttpError");
            System.out.print(e.getMessage()+" "+e.getUrl());
            message=e.getUrl().substring(e.getUrl().indexOf("="));
            System.out.println(message);
            //e.printStackTrace();
            return false;
        }

    }
    public static boolean fetchMonths(){

        Connection.Response response= null;
        Document document=null;

        try {
            response = Jsoup.connect("http://192.168.183.201:9082/WISHN/UsageDetail.jsp").
                    method(Connection.Method.GET).execute();
            document=response.parse();
            months=new ArrayList<String>();
            Elements element=document.select("option");
            Iterator iterator=element.listIterator();
            while(iterator.hasNext()){
                String name=iterator.next().toString();
                name=name.substring(name.indexOf(">")+1,name.lastIndexOf("<"));
                months.add(name);
                System.out.println(name);
            }
            System.out.println("Months Fetched/.//");
            return true;

        }  catch(NoRouteToHostException e){
            System.out.println(e.getMessage());
            message="noRoute";
            return false;
        }
        catch (ConnectException e){

            System.out.println("Already logged in");
            message="mLogin";
            System.out.println("Connection Failed: "+e.getMessage());
            return false;
        }
        catch (SocketException e){
            System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
            message="noEthernet";
            System.out.println(e.getMessage());
            return false;
        }
        catch (SocketTimeoutException e){
            message="connectTimeOut";
            System.out.println("Connection TImed out: "+e.getMessage());
            return false;
        }
        catch (Exception e) {
            //To change body of catch statement use File | Settings | File Templates.
            System.out.println("Already logged in");
            System.out.print(e.getMessage());
            return false;
        }

    }
    public static boolean sessionDetails(String username,String itemIndex,String month){
        Connection.Response response= null;
        Document document=null;

        rowData=new ArrayList<String>();
        try {
            response = Jsoup.connect("http://192.168.183.201:9082/WISHN/UsageDetailUI.do6?userNameFromParent="+username
                    +"&itemIndex="+itemIndex+"&Month="+month+"&Group=All").method(Connection.Method.GET).execute();
            document=response.parse();
            System.out.println(document.getElementsByAttributeValue("align","left"));
            Elements packName=document.getElementsByAttributeValue("align","left");
            Iterator packNameIt=packName.listIterator();



            System.out.println(document.getElementsContainingOwnText(":"));
            Elements elements=document.getElementsContainingOwnText(":");
            Iterator elemIterator=elements.listIterator();
            sessions=elemIterator.next().toString();
            sessions=sessions.substring(sessions.indexOf(":")+2,sessions.lastIndexOf("<"));
            dLoad=elemIterator.next().toString();
            dLoad=dLoad.substring(dLoad.indexOf(":")+2,dLoad.lastIndexOf("<"));
            uLoad=elemIterator.next().toString();
            uLoad=uLoad.substring(uLoad.indexOf(":")+2,uLoad.lastIndexOf("<"));
            time=elemIterator.next().toString();
            time=time.substring(time.indexOf(":")+2,time.lastIndexOf("<"));



            System.out.println(document.getElementsContainingOwnText("."));
            Elements dataPack=document.getElementsContainingOwnText(".");
            Iterator dataPackIt=dataPack.listIterator();

            String data="";
            packNameIt.next();packNameIt.next();packNameIt.next();
            dataPackIt.next();dataPackIt.next();
            while(true){
                String temp=packNameIt.next().toString();
                temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                data+=","+temp+",";

                 temp=elemIterator.next().toString();
                 temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                 data+=temp+",";
                temp=elemIterator.next().toString();
                temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                data+=temp+",";


                temp=dataPackIt.next().toString();
                temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                data+=temp+",";
                temp=dataPackIt.next().toString();
                temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                data+=temp+",";
                temp=dataPackIt.next().toString();
                temp=temp.substring(temp.indexOf(">")+1,temp.lastIndexOf("<"));
                data+=temp+",";

                 System.out.println(data);
                 rowData.add(data);
                data="";
                 if(!packNameIt.hasNext())
                     break;
            }

            System.out.print(month+" "+itemIndex);
            return true;

        }  catch(NoRouteToHostException e){
            System.out.println(e.getMessage());
            message="noRoute";
            return false;
        }
        catch (ConnectException e){

            System.out.println("Already logged in");
            message="mLogin";
            System.out.println("Connection Failed: "+e.getMessage());
            return false;
        }
        catch (SocketException e){
            System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
            message="noEthernet";
            System.out.println(e.getMessage());
            return false;
        }
        catch (SocketTimeoutException e){
            message="connectTimeOut";
            System.out.println("Connection TImed out: "+e.getMessage());
            return false;
        }
        catch(NoSuchElementException e){
            message="noData";
            System.out.println("No Data Available for this month");

            return false;
        }
        catch (Exception e) {
            //To change body of catch statement use File | Settings | File Templates.
            System.out.println("Already logged in");
          e.printStackTrace();
            return false;
        }
    }

    public static boolean checkCon()throws IOException{
        try {

            Connection.Response response= Jsoup.connect("http://192.168.183.201/WISHN/Login.jsp").method(Connection.Method.GET).timeout(20000)
                    .execute();
            System.out.println("Connected...");
            return true;
        }
        catch(NoRouteToHostException e){
            System.out.println(e.getMessage());
            message="noRoute";
            return false;
        }
        catch (ConnectException e){

            System.out.println("Already logged in");
            message="mLogin";
            System.out.println("Connection Failed: "+e.getMessage());
            return false;
        }
        catch (SocketException e){
            System.out.println("Socket Exception occurred.Ethernet may be disabled or not working.");
            message="noEthernet";
            System.out.println(e.getMessage());
            return false;
        }
        catch (SocketTimeoutException e){
            message="connectTimeOut";
            System.out.println("Connection TImed out: "+e.getMessage());
            return false;
        }
        catch (Exception e) {
          //To change body of catch statement use File | Settings | File Templates.
            System.out.println("Already logged in");
            System.out.print(e.getMessage());
            return false;
        }
    }

}
