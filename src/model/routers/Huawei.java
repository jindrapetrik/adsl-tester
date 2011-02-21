package model.routers;

import java.io.IOException;
import model.MeasuredRouter;
import java.util.ArrayList;

/**
 * Huawei
 * @author JPEXS
 */
public class Huawei extends MeasuredRouter {

    public Huawei() {
        type = "Huawei";
    }

    @Override
    public String toString() {
        return "Huawei";
    }

    @Override
    public void login() throws IOException {
        super.login();
        //dofirstMeasure();
    }

    @Override
    public void dofirstMeasure() throws IOException {
        super.dofirstMeasure();
        maxSpeedDown = "?";
        maxSpeedUp = "?";
        sysVersion = "?";
        ArrayList<String> lines;


        /*
        lines=sendRequest("ip ifconfig wanif0");
        for(int i=0;i<lines.size();i++){
        if(lines.get(i).indexOf("mtu")>-1){
        WANMTU=lines.get(i).substring(lines.get(i).indexOf("mtu") + 4);
        }
        if(lines.get(i).indexOf("inet")>-1){
        WANIP=lines.get(i).substring(lines.get(i).indexOf("inet ") + 5,lines.get(i).indexOf(","));
        break;
        }
        }*/

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

        lines = sendRequest("sys ver");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("RAS version") > -1) {
                sysVersion = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
                break;
            }
        }

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
        WANMTU = "?";
        WANIP = "?";
        bootBaseVersion = "?";
        inpDown="?";
        inpUp="?";
        measureStart();
        //Speed down
        ArrayList<String> lines;

        //WAN MTU,IP
        lines = sendRequest("ip ifconfig");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("netmask 0xffffffff") > -1) {
                WANMTU = lines.get(i - 1).substring(lines.get(i - 1).indexOf("mtu") + 4);
                WANIP = lines.get(i).substring(lines.get(i).indexOf("inet ") + 5, lines.get(i).indexOf(","));
                break;
            }
        }

        lines = sendRequest("show wan adsl chandata");
        if (lines.size() >= 4) {
            try {
                String speedDownInterleaved = lines.get(0).substring(lines.get(0).indexOf(":") + 2).trim();
                String speedDownFast = lines.get(1).substring(lines.get(1).indexOf(":") + 2).trim();
                if (speedDownInterleaved.startsWith("0 ")) {
                    speedDown = speedDownFast;
                } else {
                    speedDown = speedDownInterleaved;
                }
                //Speed up
                String speedUpInterleaved = lines.get(2).substring(lines.get(2).indexOf(":") + 2).trim();
                String speedUpFast = lines.get(3).substring(lines.get(3).indexOf(":") + 2).trim();
                if (speedUpInterleaved.startsWith("0 ")) {
                    speedUp = speedUpFast;
                } else {
                    speedUp = speedUpInterleaved;
                }
            } catch (Exception ex) {
            }
        }
        //DSL Standard
        lines = sendRequest("show wan adsl opmode");
        if (lines.size() > 0) {
            dslStandard = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
        }
        //Graph
        lines = sendRequest("show wan adsl linedata far");
        int tonestart = 0;
        for (int i = 0; i < lines.size(); i++) {
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
        graphData = new int[512];
        int x = 0;
        for (int i = tonestart; i < lines.size(); i++) {
            if (lines.get(i).startsWith("tone ")) {
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
        lines = sendRequest("show wan adsl status wan");
        if (lines.size() > 0) {
            ADSLStatus = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
        }
        //Margin down...
        lines = sendRequest("show wan adsl linedata near");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("noise margin downstream: ")) {
                marginDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("output power upstream: ")) {
                powerUp = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
            if (lines.get(i).startsWith("attenuation downstream: ")) {
                attenuationDown = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
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
            if (lines.get(i).startsWith("ADSL uptime")) {
                uptime = lines.get(i).substring(12).trim();
            }
            if (lines.get(i).startsWith("Error second in 24hr")) {
                ES24h = lines.get(i).substring(lines.get(i).indexOf(":") + 2);
            }
        }
        //Protocol, encaps, vpivci,...
        lines = sendRequest("show wan node 0");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Encapsulation")) {
                protocol = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
            if (lines.get(i).startsWith("Multiplexing")) {
                encapsulation = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
            if (lines.get(i).startsWith("Channel active")) {
                String PPoEStatus = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                PPPoEActive = PPoEStatus.indexOf("Yes") > -1;
            }
            if (lines.get(i).startsWith("VPI/VCI value")) {
                vpivci = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
            if (lines.get(i).startsWith("PPP Username ")) {
                name = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
            if (lines.get(i).startsWith("PPP Password ")) {
                password = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
        }
        //Bridge, vpivci,...
        lines = sendRequest("show wan node 1");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Channel active")) {
                String BridgeStatus = lines.get(i).substring(lines.get(i).indexOf("=") + 2);
                BridgeActive = BridgeStatus.indexOf("Yes") > -1;
            }
            if (lines.get(i).startsWith("VPI/VCI value")) {
                vpivci += " " + lines.get(i).substring(lines.get(i).indexOf("=") + 2);
            }
        }
        //ATUC
        lines = sendRequest("show wan adsl farituid");
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
            } catch (Exception nex) {
                ATUC = "?";
            }
        }

        measureFinish();

    }

    @Override
    public void disconnect() {
        if(!connected) return;
        try {
            sendRequest("exit");
        } catch (IOException ex) {
        }
        super.disconnect();
    }

    @Override
    public boolean checkRouterHeader(String header) {
        boolean ok = (header.length() < 15) && header.endsWith("> ");
        if (ok) {
            model = header.substring(0, header.indexOf(">"));
        }
        return ok;
    }
}
