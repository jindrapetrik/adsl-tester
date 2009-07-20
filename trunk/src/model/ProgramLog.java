package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author JPEXS
 */
public class ProgramLog {

    private static boolean souborOtevren=false;
    private static FileOutputStream logStream=null;

    private static boolean kontrolaSouboru(){
        if(!souborOtevren){
            Calendar cal = Calendar.getInstance();
            String date = "" + cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
            String min = ""+cal.get(Calendar.MINUTE);
            if(min.length()==1) min="0"+min;
            String hr = ""+cal.get(Calendar.HOUR_OF_DAY);
            if(hr.length()==1) hr="0"+hr;
            String time = hr + "_"+min;
            String file="programlog" + date + " " + time + ".txt";
            try {
                logStream = new FileOutputStream(file);
            } catch (FileNotFoundException ex) {
                return false;
            }
            souborOtevren=true;
        }
        return true;
    }

    public static void println(String s){
        if(!Main.isDebugMode()) return;
        if(kontrolaSouboru()){
            System.out.println(s);
            try {
                logStream.write((s + "\r\n").getBytes());
            } catch (IOException ex) {
            }
        }
    }

    public static void println(){
        println("");
    }

    public static void print(String s){
        if(!Main.isDebugMode()) return;
        if(kontrolaSouboru()){
            System.out.print(s);
            try {
                logStream.write(s.getBytes());
            } catch (IOException ex) {
            }
        }
    }
    public static void close(){
        try {
            logStream.close();
        } catch (IOException ex) {

        }
        souborOtevren=false;
    }
}
