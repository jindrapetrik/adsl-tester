package model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for communication with router
 * @author JPEXS
 */
public abstract class Router {

    private String adress="10.0.0.138";
    private int port=23;
    private Socket sock;
    protected boolean connected = false;
    private InputStream is;
    private OutputStream os;
    private boolean debugMode=false;
    protected String connectionUserName="admin";
    protected String connectionPassword="admin";

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public void setConnectionUserName(String connectionUserName) {
        this.connectionUserName = connectionUserName;
    }
   


    /**
     * Constructor of TelnetCommunicator
     * @param adress Address of router telnet interface
     * @param port Port of router telnet interface
     */
    public Router() {
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }


    public String getAdress() {
        return adress;
    }

    public boolean isConnected() {
        return connected;
    }

    public int getPort() {
        return port;
    }

    public void setAddressAndPort(String adress,int port) {        
        this.adress=adress;
        this.port=port;
        if(connected){
            disconnect();
        }
    }


    /**
     * Initializes connection to the router
     * @return True on success
     */
    public boolean connect() throws IOException {
        if (connected) {
            return true;
        }
            model.Main.connectingStart();
            sock = new Socket(InetAddress.getByName(adress), port);
            sock.setSoTimeout(1000);
            is = sock.getInputStream();
            os = sock.getOutputStream();
            connected = true;
            readLine(); //telnet header
            model.Main.connectingFinished();
            return true;        
    }

    /**
     * Log on the router
     */
    public void login() throws IOException{
        connect();

        String line=readAndStopAfterChar(':');
        readByte(); //space
        if((line.toLowerCase().indexOf("login")>-1)||(line.toLowerCase().indexOf("user")>-1)){
            sendLine(connectionUserName);
            readLine();
            line=readAndStopAfterChar(':');
            readByte(); //space
            readLine();
        }
        if(line.toLowerCase().indexOf("password")>-1){
            sendLine(connectionPassword);
            readLines();
            return;
        }
    }

    /**
     * Reads all available lines from router to a list
     * @return ArrayList of all lines
     */
    public ArrayList<String> readLinesList() throws IOException{
        //System.out.println("readLinesList start");        
        String s="";
        ArrayList<String> ret=new ArrayList<String>();
        if(!connect()) return ret;
        while((s=readLine())!=null){
            //System.out.println("added 1 line");
            ret.add(s);
        }
        return ret;
    }

    /**
     * Reads all available lines from router to an array
     * @return Array of all lines
     */
    public String[] readLines() throws IOException{
        //System.out.println("readLines start");
        if(!connect()) return new String[0];
        //System.out.println("Calling readLinesList");
        ArrayList<String> list=readLinesList();
        //System.out.println("Converting list to array");
        String ret[]=new String[list.size()];
        for(int i=0;i<list.size();i++)
        {
            ret[i]=list.get(i);
        }
        return ret;
    }

    /**
     * Reads one byte from the router
     * @return One byte or -1 on error
     */
    public int readByte() throws IOException{
        if(!connect()) return -1;        
            return is.read();        
    }

    /**
     * Reads one byte from the router
     * @return One byte or -1 on error
     */
    public int readByteDontWait() throws IOException{
        if(!connect()) return -1;       
            return is.read();
    }

    /**
     * Reads one line from router
     * @return Line which was read
     */
    public String readLine() throws IOException {
        //System.out.println("readLine start");
        if(!connect()) return "";
        try {
            boolean end = false;
            int i=0;
            int prev=0;
            String line="";
            if(debugMode)
            System.out.print("<\"");
            do {
                i = is.read();
                if((i!='\n')&&(i!='\r')){
                    line+=(char)i;
                }
                if((i=='\n')&&(prev=='\r')){
                    if(debugMode)
                    System.out.println("\"");
                    return line;
                }
                if(checkRouterHeader(line)){
                    if(debugMode)
                    System.out.println(((char)i)+"\"");
                    return null;
                }
                prev=i;
                if(i==-1) break;
                if(debugMode)
                  System.out.print((char)i);
            } while (!end);
        }catch(SocketTimeoutException ex){
        }
        if(debugMode)
        System.out.println("\"");
        return null;
    }

    /**
     * Send one command (line) to the router
     * @param line Command to send
     * @return True on success
     */
    public boolean sendLine(String line) throws IOException{
        if(debugMode)
        System.out.println(">\""+line+"\"");
        if(!connect()) return false;
        try {
            os.write((line + "\r\n").getBytes());
            return true;
        }catch(SocketTimeoutException ex){

        }
        return false;
    }

    /**
     * Reads from server and stops reading after str is read
     * @param str String to stop after
     * @return True when found
     */
    public boolean readAndStopAfterString(String str) throws IOException{
        if(!connect()) return false;
        try {
            boolean end = false;
            int i=0;
            int prev=0;
            String line="";
            do {
                i = is.read();
                if((i!='\n')&&(i!='\r')){
                    line+=(char)i;
                }
                if((i=='\n')&&(prev=='\r')){
                    if(debugMode)
                    System.out.println("Router header not found");
                    return false;
                }
                if(line.equals(str)){
                    if(debugMode)
                    System.out.println("Router header found");
                    return true;
                }
                prev=i;
            } while (!end);
        }catch(SocketTimeoutException ex){

        }
        return false;
    }

    /**
     * Reads from server and stops reading after c is read
     * @param c Char to stop after
     * @return String before c
     */
    public String readAndStopAfterChar(char c) throws IOException{
        if(!connect()) return "";
        try {
            boolean end = false;
            int i=0;
            int prev=0;
            String line="";
            do {
                i = is.read();
                if((i!='\n')&&(i!='\r')){
                    line+=(char)i;
                }
                /*if((i=='\n')&&(prev=='\r')){
                    if(debugMode)
                      System.out.println("Read till char - EOL:"+line);
                    return line;
                }*/
                if(i==c){
                    if(debugMode){
                        System.out.println("<"+line);
                    }
                    return line;
                }
                prev=i;
            } while (!end);
        }catch(SocketTimeoutException ex){

        }
        return null;
    }

    /**
     * Disconnects router
     */
    public void disconnect(){        
        connected=false;
        try {
            is.close();
            os.close();
            sock.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Sends command to the router and returns answer
     * @param command Command to send
     * @return Array of returned lines
     */
    public String[] sendCommand(String command) throws IOException{
        sendLine(command);
        readLine();
        return readLines();
    }

    public abstract boolean checkRouterHeader(String header);


}
