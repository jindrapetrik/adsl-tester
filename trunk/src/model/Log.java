package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author JPEXS
 */
public class Log {
    private static PrintWriter pw=null;
    private static String LOG_FILENAME="debug.log.txt";

    public static void println(String s){
        if(pw==null){
            try {
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LOG_FILENAME)));
            } catch (FileNotFoundException ex) {
                return;
            }
        }
        pw.println(s);
    }
}
