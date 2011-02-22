package model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for communication with router
 * @author JPEXS
 */
public abstract class Router {

    private String adress = "10.0.0.138";
    private int port = 23;
    private Socket sock;
    protected boolean connected = false;
    private InputStream is;
    private OutputStream os;
    private boolean debugMode = false;
    protected String connectionUserName = "admin";
    protected String connectionPassword = "admin";

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

    public void setAddressAndPort(String adress, int port) {
        this.adress = adress;
        this.port = port;
        if (connected) {
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
        is = sock.getInputStream();
        os = sock.getOutputStream();
        connected = true;
        sock.setSoTimeout(100);
        readLine(); //telnet header
        model.Main.connectingFinished();
        return true;
    }

    /**
     * Log on the router
     */
    public void login() throws IOException {
        connect();
        Main.loggingInStart();
        sock.setSoTimeout(2000);
        String line = readAndStopAfterChar(':');
        readByte(); //space
        if ((line.toLowerCase().indexOf("login") > -1) || (line.toLowerCase().indexOf("user") > -1)) {
            sendLine(connectionUserName);
            readLine();
            line = readAndStopAfterChar(':');
            readByte(); //space            
            readLine();
        }
        if (line.toLowerCase().indexOf("password") > -1) {
            sendLine(connectionPassword);
            readLines();
            Main.loggingInFinished();
            return;
        }
        sock.setSoTimeout(Main.socketTimeout);
        Main.loggingInFinished();
    }

    /**
     * Reads one byte from the router
     * @return One byte or -1 on error
     */
    public int readByte() throws IOException {
        try {
            if (!connect()) {
                return -1;
            }
            return is.read();
        } catch (SocketTimeoutException ex) {
        }
        return 0;
    }

    /**
     * Reads one byte from the router
     * @return One byte or -1 on error
     */
    public int readByteDontWait() throws IOException {
        if (!connect()) {
            return -1;
        }
        return is.read();
    }

    /**
     * Reads one line from router
     * @return Line which was read
     */
    public String readLine() throws IOException {
        //ProgramLog.println("readLine start");
        if (!connect()) {
            return "";
        }
        try {
            boolean end = false;
            int i = 0;
            int prev = 0;
            String line = "";
            if (debugMode) {
                ProgramLog.print("<\"");
            }
            do {
                i = is.read();
                //ProgramLog.println("readline:"+i);
                if ((i != '\n') && (i != '\r')) {
                    //ProgramLog.println("A");
                    line += (char) i;
                    //ProgramLog.println("B");
                }
                if ((i == '\n') && (prev == '\r')) {
                    if (debugMode) {
                        ProgramLog.println("\"");
                    }
                    //ProgramLog.println("C");
                    return line;
                }
                if (checkRouterHeader(line)) {
                    if (debugMode) {
                        ProgramLog.println(((char) i) + "\"");
                    }
                   // ProgramLog.println("D");
                    return null;
                }
                prev = i;
                if (i == -1) {
                    //ProgramLog.println("E");
                    break;
                }
                if (debugMode) {
                    ProgramLog.print("" + (char) i);
                }
            } while (!end);
        } catch (SocketTimeoutException ex) {
        }
        if (debugMode) {
            ProgramLog.println("\"");
        }
        return null;
    }

    /**
     * Send one command (line) to the router
     * @param line Command to send
     * @return True on success
     */
    public boolean sendLine(String line) throws IOException {
        if (debugMode) {
            ProgramLog.println(">\"" + line + "\"");
        }
        if (!connect()) {
            return false;
        }
        os.write((line + "\r\n").getBytes());
        return true;
    }

    /**
     * Reads from server and stops reading after str is read
     * @param str String to stop after
     * @return True when found
     */
    public boolean readAndStopAfterString(String str) throws IOException {
        if (!connect()) {
            return false;
        }
        boolean end = false;
        int i = 0;
        int prev = 0;
        String line = "";
        do {
            i = is.read();
            if ((i != '\n') && (i != '\r')) {
                line += (char) i;
            }
            if ((i == '\n') && (prev == '\r')) {
                if (debugMode) {
                    ProgramLog.println("Router header not found");
                }
                return false;
            }
            if (line.equals(str)) {
                if (debugMode) {
                    ProgramLog.println("Router header found");
                }
                return true;
            }
            prev = i;
        } while (!end);

        return false;
    }

    /**
     * Reads from server and stops reading after c is read
     * @param c Char to stop after
     * @return String before c
     */
    public String readAndStopAfterChar(char c) throws IOException {
        if (!connect()) {
            return "";
        }
        String line = "";
        try {
            boolean end = false;
            int i = 0;
            int prev = 0;
            do {
                i = is.read();
                if ((i != '\n') && (i != '\r')) {
                    line += (char) i;
                }
                /*if((i=='\n')&&(prev=='\r')){
                if(debugMode)
                ProgramLog.println("Read till char - EOL:"+line);
                return line;
                }*/
                if (i == c) {
                    if (debugMode) {
                        ProgramLog.println("<" + line);
                    }
                    return line;
                }
                prev = i;
            } while (!end);
        } catch (SocketTimeoutException ex) {
        }
        return line;
    }

    /**
     * Disconnects router
     */
    public void disconnect() {
        connected = false;
        try {
            is.close();
            os.close();
            sock.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Sends request to the router and returns answer
     * @param command Command to send
     * @return Array of returned lines
     */
    public ArrayList<String> sendRequest(String command) throws IOException {
        sendLine(command);
        readLine();
        ArrayList<String> ret = readLines();
        if (ret == null) {
            throw new model.NoAnswerException();
        }
        return ret;
    }

    /**
     * Sends command to the router
     * @param command Command to send
     */
    public void sendCommand(String command) throws IOException {
        sendLine(command);
        readLine();
        readLines();
    }

    public abstract boolean checkRouterHeader(String header);

    public ArrayList<String> readLines() throws IOException {
        ArrayList<String> ret = new ArrayList<String>();
        try {
            boolean end = false;
            int i = 0;
            int prev = 0;
            String line = "";
            if (debugMode) {
                ProgramLog.print("<\"");
            }
            do {
                i = is.read();
                if ((i != '\n') && (i != '\r')) {
                    line += (char) i;
                }
                if ((i == '\n') && (prev == '\r')) {
                    if (debugMode) {
                        ProgramLog.println("\"");
                    }
                    ret.add(line);
                    line = "";
                }
                if (checkRouterHeader(line)) {
                    if (debugMode) {
                        ProgramLog.println(((char) i) + "\"");
                    }
                    return ret;
                }
                prev = i;
                if (i == -1) {
                    return null;
                }
                if (debugMode) {
                    ProgramLog.print("" + (char) i);
                }
            } while (!end);
        } catch (SocketTimeoutException ex) {
        }
        if (debugMode) {
            ProgramLog.println("\"");
        }
        return null;
    }
}
