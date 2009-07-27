package model;

import eve.io.File;
import eve.sys.Device;
import eve.ui.Application;
import eve.ui.Window;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import model.routers.*;


/**
 *
 * @author JPEXS
 */
public class Main {

    
    public static MeasuredRouter router;
    private static Timer timer = null;
    public static ArrayList<MeasuredRouter> routers = new ArrayList<MeasuredRouter>();
    private static boolean logEnabled = false;
    private static File logFile;
    private static FileOutputStream logOutputStream;
    public static String version="Beta 9";
    public static String defaultIP="10.0.0.138";
    public static int defaultPort=23;
    public static int scanInterval=2;
    public static String connectionPassword="admin";
    public static String connectionUserName="admin";
    public static int socketTimeout=5000;
    private static boolean debugMode=false;

    public static void setDebugMode(boolean debugMode) {
        Main.debugMode = debugMode;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    

    public static void loadConfig(){
        File settingsFile=new File("o2tester.cfg");
        if(settingsFile.exists()){
            try {
                InputStream is = settingsFile.toReadableStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String s="";
                while((s=br.readLine())!=null){
                    if(s.startsWith("language=")){
                        view.Main.langId=s.substring(s.indexOf("=")+1);
                    }
                    if(s.startsWith("timeout=")){
                        try{
                           socketTimeout=Integer.parseInt(s.substring(s.indexOf("=")+1));
                        }catch(NumberFormatException nex){
                           
                        }
                    }
                    if(s.startsWith("defaultIP=")){
                        defaultIP=s.substring(s.indexOf("=")+1);
                    }
                    if(s.startsWith("defaultUserName=")){
                        connectionUserName=s.substring(s.indexOf("=")+1);
                    }
                    if(s.startsWith("defaultPassword=")){
                        connectionPassword=s.substring(s.indexOf("=")+1);
                    }
                    if(s.startsWith("defaultPort=")){
                        try{
                          defaultPort=Integer.parseInt(s.substring(s.indexOf("=")+1));
                        }catch(NumberFormatException nex){
                            defaultPort=23;
                        }
                    }
                    if(s.startsWith("debugMode=1")){
                        setDebugMode(true);
                    }
                    if(s.startsWith("forceMobileView=1")){
                        view.Main.forceMobile=true;
                    }
                }
                is.close();
            } catch (IOException ex) {

            }
        }
    }



    public static boolean isLogEnabled() {
        return logEnabled;
    }

    public static void setLogEnabled(boolean logEnabled) {
        Main.logEnabled = logEnabled;
    }


    public static int byteToInt(byte b) {
        if(b<0) {
            return (int)(b)+256;
        } else {
            return (int)b;
        }
    }

    public static void main(String args[]) {
        Application.startApplication(args);
        routers.add(new Huawei());
        routers.add(new DLink());
        routers.add(new Zyxel());

        loadConfig();
        try{
        Window.defaultWindowIcon = Device.toDeviceIcon("ikona.gif");//new Picture(
        }catch(Exception ex){}

        view.Main.start();
        
    }

    public static void exit() {
        /*if (router != null) {
            if (router.isConnected()) {
                router.disconnect();
            }
        } THIS IS MOVED INTO BUTTON STOP
         */
        Application.exit(0);
    }

    public static void startMeasure() {
        router.setDebugMode(debugMode);
        view.Main.view.startMeasurement();
        timer = null;
        timer = new Timer();
        int delay = scanInterval * 1000;
        
        timer.schedule(new MeasureTask(), 1, delay);

        if (logEnabled) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
            String date = "" + cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
            String min = ""+cal.get(Calendar.MINUTE);
            if(min.length()==1) min="0"+min;
            String hr = ""+cal.get(Calendar.HOUR_OF_DAY);
            if(hr.length()==1) hr="0"+hr;
            String time = hr + "_"+min;
            logFile = new File(router.toString().toLowerCase() + "log" + date + " " + time + ".txt");
            try {
                logOutputStream = new FileOutputStream(logFile.toJavaFile());
                String header=view.Main.language.logHeader;
                header=header.substring(0,header.indexOf("1"))+router.toString()+header.substring(header.indexOf("1")+1);
                header=header.substring(0,header.indexOf("2"))+date+header.substring(header.indexOf("2")+1);
                header=header.substring(0,header.indexOf("3"))+router.getAdress()+header.substring(header.indexOf("3")+1);
                logOutputStream.write(header.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }       
    }

    public static void stopMeasure() {        
        if (timer != null) {            
            try {
                timer.cancel();
            } catch (Exception ex) {
            }
            timer = null;            
        }

        while (router.isMeasuring()) {
                Thread.yield();
            }

        if (logEnabled) {
            try {
                logOutputStream.close();
            } catch (Exception ex) {
            }
            logOutputStream = null;
            logFile = null;
        }
        if (router != null) {
            router.disconnect();            
        }
        view.Main.view.measuringStop();
    }

    public static void connectingStart() {
        view.Main.view.connectingDisplay();
    }

    public static void connectingFinished() {
        view.Main.view.connectingHide();
    }

    public static void measureStart() {
        view.Main.view.measuringStart();
    }

    public static void firstMeasureStart() {
        view.Main.view.measuringFirstStart();
    }


    public static void measureFinished() {
        view.Main.view.measuringFinish();
        if (logEnabled) {
            try {
                logOutputStream.write(router.getDslStandard().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(("" + router.getSpeedDown() + " kbps").getBytes());
                logOutputStream.write(';');
                logOutputStream.write(("" + router.getSpeedUp() + " kbps").getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getUptime().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getPowerUp().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getAttenuationUp().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getPowerDown().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getAttenuationDown().getBytes());
                logOutputStream.write(';');

                logOutputStream.write(router.getErrorsDownStreamFastFEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsDownStreamFastCRC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsDownStreamFastHEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsDownStreamIntFEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsDownStreamIntCRC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsDownStreamIntHEC().getBytes());
                logOutputStream.write(';');

                logOutputStream.write(router.getErrorsUpStreamFastFEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsUpStreamFastCRC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsUpStreamFastHEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsUpStreamIntFEC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsUpStreamIntCRC().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getErrorsUpStreamIntHEC().getBytes());
                logOutputStream.write(';');

                logOutputStream.write(router.getMarginUp().getBytes());
                logOutputStream.write(';');
                logOutputStream.write(router.getMarginDown().getBytes());
                logOutputStream.write(';');

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
                String min = ""+cal.get(Calendar.MINUTE);
                if(min.length()==1) min="0"+min;
                String hr = ""+cal.get(Calendar.HOUR_OF_DAY);
                if(hr.length()==1) hr="0"+hr;
                String time = hr + ":"+min;
                logOutputStream.write(time.getBytes());
                logOutputStream.write(';');
                logOutputStream.write("\r\n".getBytes());

            } catch (Exception ex) {
                
            }
        }
    }

    public static void errorConnecting() {
        view.Main.view.connectingHide();
        view.Main.view.displayConnectionError();        
    }
}
