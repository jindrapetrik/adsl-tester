package model.routers;

import java.io.IOException;
import model.MeasuredRouter;
import java.util.ArrayList;
import model.StandardChangeable;

/**
 * Zyxel
 * @author JPEXS
 */
public class Zyxel extends MeasuredRouter implements StandardChangeable{

    public Zyxel() {
        type = "Zyxel";
    }

    @Override
    public String toString() {
        return "Zyxel";
    }

    @Override
    public void dofirstMeasure() throws IOException {
        super.dofirstMeasure();
        WANMTU = "?";
        WANIP = "?";
        ES24h = "?";
        protocol = "?";
        encapsulation = "?";
        vpivci = "?";
        name = "?";
        password = "?";
        maxSpeedDown = "?";
        maxSpeedUp = "?";
        sysVersion = "?";
        bootBaseVersion = "?";

        ArrayList<String> lines;

        //DSL Standard
        lines = sendRequest("wan adsl opmode");
        if (lines.size() > 0) {
            if (lines.get(0).indexOf("DSL standard") > -1) {
                dslStandard = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
            }
        }

        boolean isADSL2Plus = dslStandard.equals("ADSL2PLUS");

              

        //ES 24 h
        lines = sendRequest("wan adsl errorsecond show");
        if (lines.size() > 0) {
            if (lines.get(0).indexOf("Num of ES") > -1) {
                ES24h = lines.get(0).substring(lines.get(0).indexOf("=") + 1);
            }
        }
        //Protocol, encaps, vpivci,...
        sendCommand("wan node index 1");
        lines = sendRequest("wan node display");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Encapsulcation") || lines.get(i).startsWith("Encapsulation")) {
                protocol = lines.get(i).substring(lines.get(i).indexOf("=") + 2).trim();
                if (protocol.equals("2")) {
                    protocol = "PPPoE";
                }
                if (protocol.equals("3")) {
                    protocol = "RFC1483";
                }
                if (protocol.equals("4")) {
                    protocol = "PPPoA";
                }
                if (protocol.equals("5")) {
                    protocol = "Enet Encap";
                }

            }
            if (lines.get(i).startsWith("Mux")) {
                encapsulation = lines.get(i).substring(lines.get(i).indexOf("=") + 2).trim();
                if (encapsulation.equals("1")) {
                    encapsulation = "LLC";
                }
                if (encapsulation.equals("2")) {
                    encapsulation = "VC";
                }
            }
            if (lines.get(i).startsWith("VPI/VCI")) {
                vpivci = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }

            if (lines.get(i).startsWith("Active")) {
                String PPoEStatus = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                PPPoEActive = PPoEStatus.indexOf("yes") > -1;
            }
            if (lines.get(i).startsWith("PPP username ")) {
                name = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
            if (lines.get(i).startsWith("PPP password ")) {
                password = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
        }
        if (!protocol.equals("PPPoE")) {
            PPPoEActive = false;
        }
        sendCommand("wan node index 2");
        lines = sendRequest("wan node display");
        for (int i = 0; i < lines.size(); i++) {
            /*if (lines.get(i).startsWith("Encapsulcation")||lines.get(i).startsWith("Encapsulation")) {
            protocol = lines.get(i).substring(lines.get(i).indexOf("=") + 2).trim();
            if(protocol.equals("2")) protocol="PPPoE";
            if(protocol.equals("3")) protocol="RFC1483";
            if(protocol.equals("4")) protocol="PPPoA";
            if(protocol.equals("5")) protocol="Enet Encap";

            }*/
            /*if (lines.get(i).startsWith("Mux")) {
            encapsulation = lines.get(i).substring(lines.get(i).indexOf("=") + 2).trim();
            if(encapsulation.equals("1")) encapsulation="LLC";
            if(encapsulation.equals("2")) encapsulation="VC";
            }*/
            if (lines.get(i).startsWith("VPI/VCI")) {
                vpivci += " " + lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }

            if (lines.get(i).startsWith("Active")) {
                String BridgeStatus = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                BridgeActive = BridgeStatus.indexOf("yes") > -1;
            }
        }


        if (isADSL2Plus) {
            //ATTNDRds
            lines = sendRequest("wan dmt2 show rparams");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("ATTNDRds")) {
                    maxSpeedDown = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                    break;
                }
            }
            //ATTNDRus

            lines = sendRequest("wan dmt2 show cparams");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("ATTNDRus")) {
                    maxSpeedUp = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                    break;
                }
            }
        }

        lines = sendRequest("sys ver");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("ZyNOS version") > -1) {
                sysVersion = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                break;
            }
            if (lines.get(i).indexOf("bootbase version") > -1) {
                bootBaseVersion = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                break;
            }
        }

    }

    @Override
    public void login() throws IOException {
        /*connect();

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
        readLine();
        return;
        }*/
        super.login();
        readAndStopAfterChar(':');
        readByte();
        sendLine("24");
        readAndStopAfterChar(':');
        readByte();
        sendLine("8");
        readLines();
        //dofirstMeasure();
    }

    @Override
    public void doMeasurement() throws IOException {
        if (isMeasuringActive()) {
            return;
        }
        speedDown = "?";
        speedUp = "?";
        dslStandard = "?";
        marginUp = "?";
        powerDown = "?";
        attenuationUp = "?";
        marginDown = "?";
        powerUp = "?";
        attenuationDown = "?";
        errorsUpStreamFastFEC = "?";
        errorsUpStreamIntFEC = "?";
        errorsUpStreamFastCRC = "?";
        errorsUpStreamIntCRC = "?";
        errorsUpStreamFastHEC = "?";
        errorsUpStreamIntHEC = "?";
        errorsDownStreamFastFEC = "?";
        errorsDownStreamIntFEC = "?";
        errorsDownStreamFastCRC = "?";
        errorsDownStreamIntCRC = "?";
        errorsDownStreamFastHEC = "?";
        errorsDownStreamIntHEC = "?";
        uptime = "?";
        ATUC = "?";
        syncNum = "?";
        ADSLStatus = "?";
        measureStart();
        ArrayList<String> lines;

        WANMTU="?";
        WANIP="?";

        //WAN MTU,IP
        lines = sendRequest("ip ifconfig");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("netmask 0xffffffff") > -1) {
                WANMTU = lines.get(i - 1).substring(lines.get(i - 1).indexOf("mtu") + 4);
                WANIP = lines.get(i).substring(lines.get(i).indexOf("inet ") + 5, lines.get(i).indexOf(","));
                break;
            }
        }
        
        lines = sendRequest("wan adsl opmode");
        if (lines.size() > 0) {
            if (lines.get(0).indexOf("DSL standard") > -1) {
                dslStandard = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
            }
        }

        boolean is2Plus = dslStandard.equals("ADSL2PLUS");

        //Speed down
        lines = sendRequest("wan adsl chandata");
        String speedDownInt="0 kbps";
        String speedDownFast="0 kbps";
        String speedUpInt="0 kbps";
        String speedUpFast="0 kbps";
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("near-end bit rate") > -1) {
                    speedDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
                if (lines.get(i).indexOf("far-end bit rate") > -1) {
                    speedUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }            
                if (lines.get(i).indexOf("near-end interleaved channel bit rate") > -1) {
                    speedDownInt = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
                if (lines.get(i).indexOf("near-end fast channel bit rate") > -1) {
                    speedDownFast = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
                if (lines.get(i).indexOf("far-end interleaved channel bit rate") > -1) {
                    speedUpInt = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
                if (lines.get(i).indexOf("far-end fast channel bit rate") > -1) {
                    speedUpFast = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
        }

        if(speedDownInt.indexOf("0 ")!=0) speedDown=speedDownInt;
        if(speedDownFast.indexOf("0 ")!=0) speedDown=speedDownFast;
        if(speedUpInt.indexOf("0 ")!=0) speedUp=speedUpInt;
        if(speedUpFast.indexOf("0 ")!=0) speedUp=speedUpFast;

        //Graph
        lines = sendRequest("wan adsl linedata far");
        int tonestart = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (!is2Plus) {
                if (lines.get(i).startsWith("attainable rate upstream: ")) {
                    maxSpeedUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
            }
            if (lines.get(i).startsWith("noise margin upstream: ")) {
                marginUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("output power downstream: ")) {
                powerDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("attenuation upstream: ")) {
                attenuationUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("tone ")) {
                tonestart = i;
                break;
            }
        }
        
        //Decrease powerDown by 80 when greater than 80
        try{
          int pwdni=Integer.parseInt(powerDown);
          if(pwdni>80){
              pwdni-=80;
              powerDown=""+pwdni;
          }
        }catch(NumberFormatException nex){
        }

        graphData = new int[512];
        int x = 0;
        for (int i = tonestart; i < lines.size(); i++) {
            if (lines.get(i).startsWith("tone ")) {
                if (x >= 64) {
                    break;
                }
                int pos = lines.get(i).indexOf(":") + 2;
                for (int j = 0; j < 16; j++) {
                    try {
                        graphData[x] = Integer.parseInt("" + lines.get(i).charAt(pos), 16);
                        graphData[x + 1] = Integer.parseInt("" + lines.get(i).charAt(pos + 1), 16);
                    } catch (NumberFormatException ex) {
                    }
                    pos += 3;
                    x += 2;
                }
            }
        }
        //Status
        lines = sendRequest("wan adsl status wan");
        if (lines.size() > 0) {
            ADSLStatus = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
        }
        //Margin down...
        lines = sendRequest("wan adsl linedata near");
        for (int i = 0; i < lines.size(); i++) {
            if (!is2Plus) {
                if (lines.get(i).startsWith("attainable rate downstream: ")) {
                    maxSpeedDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                }
            }
            if (lines.get(i).startsWith("noise margin downstream: ")) {
                marginDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("output power upstream: ")) {
                powerUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("attenuation downstream: ")) {
                attenuationDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("tone ")) {
                tonestart = i;
                break;
            }
        }
        x = 0;
        for (int i = tonestart; i < lines.size(); i++) {
            if (lines.get(i).startsWith("tone ")) {
                if (x < 64) {
                    x += 32;
                    continue;
                }
                int pos = lines.get(i).indexOf(":") + 2;
                for (int j = 0; j < 16; j++) {
                    try {
                        graphData[x] = Integer.parseInt("" + lines.get(i).charAt(pos), 16);
                        graphData[x + 1] = Integer.parseInt("" + lines.get(i).charAt(pos + 1), 16);
                    } catch (NumberFormatException ex) {
                    }
                    pos += 3;
                    x += 2;
                }
            }
        }
        //Errors, uptime
        lines = sendRequest("wan adsl perfdata");
        for (int i = 0; i < lines.size(); i++) {
            //up:
            if (lines.get(i).startsWith("far-end FEC error fast")) {
                errorsUpStreamFastFEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("far-end FEC error interleaved")) {
                errorsUpStreamIntFEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("far-end CRC error fast")) {
                errorsUpStreamFastCRC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("far-end CRC error interleaved")) {
                errorsUpStreamIntCRC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("far-end HEC error fast")) {
                errorsUpStreamFastHEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("far-end HEC error interleaved")) {
                errorsUpStreamIntHEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            //down:
            if (lines.get(i).startsWith("near-end FEC error fast")) {
                errorsDownStreamFastFEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("near-end FEC error interleaved")) {
                errorsDownStreamIntFEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("near-end CRC error fast")) {
                errorsDownStreamFastCRC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("near-end CRC error interleaved")) {
                errorsDownStreamIntCRC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("near-end HEC error fast")) {
                errorsDownStreamFastHEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("near-end HEC error interleaved")) {
                errorsDownStreamIntHEC = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("Error second in 24hr")) {
                ES24h = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("ADSL uptime")) {
                uptime = lines.get(i).substring(12).trim();
            }
        }
        //ATUC
        lines = sendRequest("wan adsl farituid");
        if (lines.size() > 0) {
            String s = lines.get(0).substring(lines.get(0).indexOf(":") + 2).trim();
            byte[] b = new byte[4];
            ArrayList<String> octets = splitString(s, ' ');
            try {
                b[0] = (byte) Integer.parseInt(octets.get(2), 16);
                b[1] = (byte) Integer.parseInt(octets.get(3), 16);
                b[2] = (byte) Integer.parseInt(octets.get(4), 16);
                b[3] = (byte) Integer.parseInt(octets.get(5), 16);
                ATUC = new String(b);
            } catch (NumberFormatException nex) {
                ATUC = "?";
            }
        }
        measureFinish();

    }

    @Override
    public void disconnect() {
        if (!connected) {
            return;
        }        
        try {
            sendLine("exit");
            readAndStopAfterChar(':');
            readByte();
            sendLine("99");
            readLines();
        } catch (IOException ex) {
            System.out.println("Error closing:" + ex.toString());
        }
        super.disconnect();
    }

    @Override
    public boolean checkRouterHeader(String header) {
        header=header.replace(""+(char)0x1B+(char)0x37, "");
        boolean ok = (header.length() < 15) && header.endsWith("> ");
        if (ok) {
            model = header.substring(0, header.indexOf(">"));
        }
        return ok;
    }

    public void setStandardGDMT() {
        if(connected)
            disconnect();
        try {
            login();
            sendRequest("wan adsl close");
            sendRequest("wan adsl opencmd gdmt");
            sendRequest("wan adsl open");
            disconnect();
        } catch (IOException ex) {

        }
    }

    public void setStandard2Plus() {
        if(connected)
            disconnect();
        try {
            login();
            sendRequest("wan adsl close");
            sendRequest("wan adsl opencmd adsl2plus");
            sendRequest("wan adsl open");
            disconnect();
        } catch (IOException ex) {

        }
    }

    public void setStandardMulti() {
        if(connected)
            disconnect();
        try {
            login();
            sendRequest("wan adsl close");
            sendRequest("wan adsl opencmd multimode");
            sendRequest("wan adsl open");
            disconnect();
        } catch (IOException ex) {
            
        }
    }
}
