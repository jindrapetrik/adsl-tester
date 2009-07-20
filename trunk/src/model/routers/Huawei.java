package model.routers;

import java.io.IOException;
import model.MeasuredRouter;
import java.util.ArrayList;

/**
 * Huawei
 * @author JPEXS
 */
public class Huawei extends MeasuredRouter {


    public Huawei(){
        type="Huawei";
    }

    @Override
    public String toString() {
        return "Huawei";
    }

    @Override
    public void login() throws IOException {
        super.login();
        dofirstMeasure();
    }



    @Override
    public void dofirstMeasure() throws IOException{
        super.dofirstMeasure();
        WANMTU="?";
        WANIP="?";
        maxSpeedDown="?";
        maxSpeedUp="?";
        sysVersion="?";
        String lines[];

        //WAN MTU,IP        
        lines=sendRequest("ip ifconfig wanif0");
        for(int i=0;i<lines.length;i++){
            if(lines[i].indexOf("mtu")>-1){
                WANMTU=lines[i].substring(lines[i].indexOf("mtu") + 4);
            }
            if(lines[i].indexOf("inet")>-1){
                WANIP=lines[i].substring(lines[i].indexOf("inet ") + 5,lines[i].indexOf(","));
                break;
            }
        }
        
        //ATTNDRds
        lines=sendRequest("wan dmt2 show rparams");
        for(int i=0;i<lines.length;i++){
            if(lines[i].startsWith("ATTNDRds")){
                maxSpeedDown=lines[i].substring(lines[i].indexOf("=") + 2);
                break;
            }
        }
        //ATTNDRus
        lines=sendRequest("wan dmt2 show cparams");
        for(int i=0;i<lines.length;i++){
            if(lines[i].startsWith("ATTNDRus")){
                maxSpeedUp=lines[i].substring(lines[i].indexOf("=") + 2);
                break;
            }
        }

        lines=sendRequest("sys ver");
        for(int i=0;i<lines.length;i++){
            if(lines[i].indexOf("RAS version")>-1){
                sysVersion=lines[i].substring(lines[i].indexOf(":") + 2);
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
            measureStart();
            //Speed down
            String[] lines = sendRequest("show wan adsl chandata");
            if (lines.length >= 4) {
                try {
                    String speedDownInterleaved = lines[0].substring(lines[0].indexOf(":") + 2).trim();
                    String speedDownFast = lines[1].substring(lines[1].indexOf(":") + 2).trim();
                    if (speedDownInterleaved.startsWith("0 ")) {
                        speedDown = speedDownFast;
                    } else {
                        speedDown = speedDownInterleaved;
                    }
                    //Speed up
                    String speedUpInterleaved = lines[2].substring(lines[2].indexOf(":") + 2).trim();
                    String speedUpFast = lines[3].substring(lines[3].indexOf(":") + 2).trim();
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
            if (lines.length > 0) {
                dslStandard = lines[0].substring(lines[0].indexOf(":") + 2);
            }
            //Graph
            lines = sendRequest("show wan adsl linedata far");
            int tonestart = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("noise margin upstream: ")) {
                    marginUp = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("output power downstream: ")) {
                    powerDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("attenuation upstream: ")) {
                    attenuationUp = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("tone ")) {
                    tonestart = i;
                    break;
                }
            }
            graphData = new int[512];
            int x = 0;
            for (int i = tonestart; i < lines.length; i++) {
                if (lines[i].startsWith("tone ")) {
                    int pos = lines[i].indexOf(":") + 2;
                    for (int j = 0; j < 16; j++) {
                        try {
                            graphData[x] = Integer.parseInt("" + lines[i].charAt(pos), 16);
                            graphData[x + 1] = Integer.parseInt("" + lines[i].charAt(pos + 1), 16);
                        } catch (NumberFormatException ex) {
                        }
                        pos += 3;
                        x += 2;
                    }
                }
            }
            //Status
            lines = sendRequest("show wan adsl status wan");
            if (lines.length > 0) {
                ADSLStatus = lines[0].substring(lines[0].indexOf(":") + 2);
            }
            //Margin down...
            lines = sendRequest("show wan adsl linedata near");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("noise margin downstream: ")) {
                    marginDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("output power upstream: ")) {
                    powerUp = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("attenuation downstream: ")) {
                    attenuationDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
            }
            //Errors, uptime
            lines = sendRequest("wan adsl perfdata");
            for (int i = 0; i < lines.length; i++) {
                //up:
                if (lines[i].startsWith("far-end FEC error fast")) {
                    errorsUpStreamFastFEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("far-end FEC error interleaved")) {
                    errorsUpStreamIntFEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("far-end CRC error fast")) {
                    errorsUpStreamFastCRC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("far-end CRC error interleaved")) {
                    errorsUpStreamIntCRC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("far-end HEC error fast")) {
                    errorsUpStreamFastHEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("far-end HEC error interleaved")) {
                    errorsUpStreamIntHEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                //down:
                if (lines[i].startsWith("near-end FEC error fast")) {
                    errorsDownStreamFastFEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("near-end FEC error interleaved")) {
                    errorsDownStreamIntFEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("near-end CRC error fast")) {
                    errorsDownStreamFastCRC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("near-end CRC error interleaved")) {
                    errorsDownStreamIntCRC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("near-end HEC error fast")) {
                    errorsDownStreamFastHEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("near-end HEC error interleaved")) {
                    errorsDownStreamIntHEC = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("ADSL uptime")) {
                    uptime = lines[i].substring(12).trim();
                }
                if (lines[i].startsWith("Error second in 24hr")) {
                    ES24h = lines[i].substring(lines[i].indexOf(":") + 2);
                }
            }
            //Protocol, encaps, vpivci,...
            lines = sendRequest("show wan node 0");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("Encapsulation")) {
                    protocol = lines[i].substring(lines[i].indexOf("=") + 2);
                }
                if (lines[i].startsWith("Multiplexing")) {
                    encapsulation = lines[i].substring(lines[i].indexOf("=") + 2);
                }
                if (lines[i].startsWith("Channel active")) {
                    String PPoEStatus = lines[i].substring(lines[i].indexOf("=") + 2);
                    PPPoEActive = PPoEStatus.indexOf("Yes") > -1;
                }
                if (lines[i].startsWith("VPI/VCI value")) {
                    vpivci = lines[i].substring(lines[i].indexOf("=") + 2);
                }
                if (lines[i].startsWith("PPP Username ")) {
                    name = lines[i].substring(lines[i].indexOf("=") + 2);
                }
                if (lines[i].startsWith("PPP Password ")) {
                    password = lines[i].substring(lines[i].indexOf("=") + 2);
                }
            }
            //Bridge, vpivci,...
            lines = sendRequest("show wan node 1");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("Channel active")) {
                    String BridgeStatus = lines[i].substring(lines[i].indexOf("=") + 2);
                    BridgeActive = BridgeStatus.indexOf("Yes") > -1;
                }
                if (lines[i].startsWith("VPI/VCI value")) {
                    vpivci += " " + lines[i].substring(lines[i].indexOf("=") + 2);
                }
            }
            //ATUC
            lines = sendRequest("show wan adsl farituid");
            if (lines.length > 0) {
                String s = lines[0].substring(lines[0].indexOf(":") + 2).trim();
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
        WANMTU="?";
        WANIP="?";
        maxSpeedDown="?";
        maxSpeedUp="?";
        sysVersion="?";
        PPPoEActive=false;
        BridgeActive=false;
        try {
            sendRequest("exit");
        } catch (IOException ex) {

        }
        super.disconnect();
    }

    @Override
    public boolean checkRouterHeader(String header) {
        boolean ok=(header.length()<15)&&header.endsWith("> ");
        if(ok){
            model=header.substring(0,header.indexOf(">"));
        }
        return ok;
    }
}
