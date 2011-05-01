package model;

import eve.fx.Picture;
import eve.io.File;
import eve.sys.Device;
import eve.sys.ImageData;
import eve.ui.Application;
import eve.ui.Window;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
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
    public static String version="2011.2";
    public static String defaultIP="10.0.0.138";
    public static int defaultPort=23;
    public static int scanInterval=2;
    public static String connectionPassword="admin";
    public static String connectionUserName="admin";
    public static int socketTimeout=5000;
    private static boolean debugMode=false;
    private static String lastLogLine="";
    private static MeasureTask measureTask;
    private static boolean measureStarted=false;

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

    public static String replaceStr(String haystack,String needle,String replacement){
        String ret="";
        int pos=0;

        int npos=-1;
        do{
            npos=haystack.substring(pos).indexOf(needle);
            if(npos==-1)
                break;
            ret+=haystack.substring(pos,pos+npos)+replacement;
            pos=pos+npos+needle.length();
        }while(npos>-1);
        ret=ret+haystack.substring(pos);
        return ret;
    }

    public static void main(String args[]) {
        Application.startApplication(args);
        routers.add(new Huawei());
        routers.add(new DLink_584_684());
        routers.add(new DLink_362_664());
        routers.add(new DLink_DVA_G3671B());
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

    private static String replaceAll(String s,String characters,String replacement){
        String out="";
        loopi:for(int i=0;i<s.length();i++){
            for(int c=0;c<characters.length();c++){
                if(characters.charAt(c)==s.charAt(i)){
                    out+=replacement;
                    continue loopi;
                }
            }
            out+=s.charAt(i);
        }
        return out;
    }

    private static String fixFileName(String name){
        return replaceAll(name,"[\\/]", "_").toLowerCase();
    }

    public static void startMeasure() {
        measureStarted=true;
        router.setDebugMode(debugMode);
        view.Main.view.startMeasurement();
        timer = null;
        timer = new Timer();
        int delay = scanInterval * 1000;
        measureTask=new MeasureTask();
        timer.schedule(measureTask, 1, delay);

        if (logEnabled) {
            Calendar cal = Calendar.getInstance();
            String date = "" + cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
            String min = ""+cal.get(Calendar.MINUTE);
            if(min.length()==1) min="0"+min;
            String hr = ""+cal.get(Calendar.HOUR_OF_DAY);
            if(hr.length()==1) hr="0"+hr;
            String time = hr + "_"+min;
            logFile = new File(fixFileName(router.toString()) + "_log" + date + " " + time + ".txt");
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
        if(!measureStarted)
            return;
        measureStarted=false;
        measureTask.measuringStopped=true;
        measureTask.cancel();
        while(measureTask.measuringNow){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                
            }
        }
        Main.router.disconnect();
        view.Main.view.measuringStop();
        if (logEnabled) {
            try {
                logOutputStream.close();
            } catch (Exception ex) {
            }
            logOutputStream = null;
            logFile = null;
        }        
    }

    public static void connectingStart() {
        view.Main.view.connectingDisplay();
    }

    public static void connectingFinished() {
        view.Main.view.connectingHide();
    }

    public static void loggingInStart() {
        view.Main.view.loggingInDisplay();
    }

    public static void loggingInFinished() {
        view.Main.view.loggingInHide();
    }

    public static void measureStart() {
        view.Main.view.measuringStart();
    }

    public static void firstMeasureStart() {
        view.Main.view.measuringFirstStart();
    }


    public static void measureFinished() {
        view.Main.view.measuringFinish();
        String logLine="";
        if (logEnabled) {
            try {
                logLine+=router.getDslStandard()+";";
                logLine+=router.getSpeedDown()+";";
                logLine+=router.getSpeedUp()+";";
                logLine+=router.getUptime()+";";
                logLine+=router.getPowerUp()+";";
                logLine+=router.getAttenuationUp()+";";
                logLine+=router.getPowerDown()+";";
                logLine+=router.getAttenuationDown()+";";

                logLine+=router.getErrorsDownStreamFastFEC()+";";
                logLine+=router.getErrorsDownStreamFastCRC()+";";
                logLine+=router.getErrorsDownStreamFastHEC()+";";

                logLine+=router.getErrorsDownStreamIntFEC()+";";
                logLine+=router.getErrorsDownStreamIntCRC()+";";
                logLine+=router.getErrorsDownStreamIntHEC()+";";

                logLine+=router.getErrorsUpStreamFastFEC()+";";
                logLine+=router.getErrorsUpStreamFastCRC()+";";
                logLine+=router.getErrorsUpStreamFastHEC()+";";

                logLine+=router.getErrorsUpStreamIntFEC()+";";
                logLine+=router.getErrorsUpStreamIntCRC()+";";
                logLine+=router.getErrorsUpStreamIntHEC()+";";
                
                logLine+=router.getMarginUp()+";";
                logLine+=router.getMarginDown()+";";

                Calendar cal = Calendar.getInstance();
                String min = ""+cal.get(Calendar.MINUTE);
                if(min.length()==1) min="0"+min;
                String hr = ""+cal.get(Calendar.HOUR_OF_DAY);
                if(hr.length()==1) hr="0"+hr;
                String time = hr + ":"+min;

                if(!logLine.equals(lastLogLine)){
                    logOutputStream.write(logLine.getBytes());
                    logOutputStream.write(time.getBytes());
                    logOutputStream.write(';');
                    logOutputStream.write("\r\n".getBytes());
                    lastLogLine=logLine;
                }

            } catch (Exception ex) {
                
            }
        }
    }

    public static void errorConnecting() {
        view.Main.view.connectingHide();
        view.Main.view.displayConnectionError();        
    }
}
