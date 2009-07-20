package model.routers;

import java.io.IOException;
import model.MeasuredRouter;
import java.util.ArrayList;

/**
 * Huawei
 * @author JPEXS
 */
public class Zyxel extends MeasuredRouter {

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

        String lines[];

        //DSL Standard
        lines = sendRequest("wan adsl opmode");
        if (lines.length > 0) {
            if (lines[0].indexOf("DSL standard") > -1) {
                dslStandard = lines[0].substring(lines[0].indexOf(":") + 2);
            }
        }

        boolean isADSL2Plus = dslStandard.equals("ADSL2PLUS");

        //WAN MTU,IP
        lines = sendRequest("ip ifconfig wanif1");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].indexOf("mtu") > -1) {
                WANMTU = lines[i].substring(lines[i].indexOf("mtu") + 4);
            }
            if (lines[i].indexOf("inet") > -1) {
                WANIP = lines[i].substring(lines[i].indexOf("inet ") + 5, lines[i].indexOf(","));
                break;
            }
        }

        if (WANIP.startsWith("10.") || WANIP.startsWith("192.")) {
            lines = sendRequest("ip ifconfig wanif0");
            WANMTU="?";
            WANIP="?";
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].indexOf("mtu") > -1) {
                    WANMTU = lines[i].substring(lines[i].indexOf("mtu") + 4);
                }
                if (lines[i].indexOf("inet") > -1) {
                    WANIP = lines[i].substring(lines[i].indexOf("inet ") + 5, lines[i].indexOf(","));
                    break;
                }
            }
        }

        //ES 24 h
        lines = sendRequest("wan adsl errorsecond show");
        if (lines.length > 0) {
            if (lines[0].indexOf("Num of ES") > -1) {
                ES24h = lines[0].substring(lines[0].indexOf("=") + 1);
            }
        }
        //Protocol, encaps, vpivci,...
        sendCommand("wan node index 1");
        lines = sendRequest("wan node display");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("Encapsulcation") || lines[i].startsWith("Encapsulation")) {
                protocol = lines[i].substring(lines[i].indexOf("=") + 2).trim();
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
            if (lines[i].startsWith("Mux")) {
                encapsulation = lines[i].substring(lines[i].indexOf("=") + 2).trim();
                if (encapsulation.equals("1")) {
                    encapsulation = "LLC";
                }
                if (encapsulation.equals("2")) {
                    encapsulation = "VC";
                }
            }
            if (lines[i].startsWith("VPI/VCI")) {
                vpivci = lines[i].substring(lines[i].indexOf("=") + 2);
            }

            if (lines[i].startsWith("Active")) {
                String PPoEStatus = lines[i].substring(lines[i].indexOf("=") + 2);
                PPPoEActive = PPoEStatus.indexOf("yes") > -1;
            }
            if (lines[i].startsWith("PPP username ")) {
                name = lines[i].substring(lines[i].indexOf("=") + 2);
            }
            if (lines[i].startsWith("PPP password ")) {
                password = lines[i].substring(lines[i].indexOf("=") + 2);
            }
        }
        if (!protocol.equals("PPPoE")) {
            PPPoEActive = false;
        }
        sendCommand("wan node index 2");
        lines = sendRequest("wan node display");
        for (int i = 0; i < lines.length; i++) {
            /*if (lines[i].startsWith("Encapsulcation")||lines[i].startsWith("Encapsulation")) {
            protocol = lines[i].substring(lines[i].indexOf("=") + 2).trim();
            if(protocol.equals("2")) protocol="PPPoE";
            if(protocol.equals("3")) protocol="RFC1483";
            if(protocol.equals("4")) protocol="PPPoA";
            if(protocol.equals("5")) protocol="Enet Encap";

            }*/
            /*if (lines[i].startsWith("Mux")) {
            encapsulation = lines[i].substring(lines[i].indexOf("=") + 2).trim();
            if(encapsulation.equals("1")) encapsulation="LLC";
            if(encapsulation.equals("2")) encapsulation="VC";
            }*/
            if (lines[i].startsWith("VPI/VCI")) {
                vpivci += " " + lines[i].substring(lines[i].indexOf("=") + 2);
            }

            if (lines[i].startsWith("Active")) {
                String BridgeStatus = lines[i].substring(lines[i].indexOf("=") + 2);
                BridgeActive = BridgeStatus.indexOf("yes") > -1;
            }
        }


        if (isADSL2Plus) {
            //ATTNDRds
            lines = sendRequest("wan dmt2 show rparams");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("ATTNDRds")) {
                    maxSpeedDown = lines[i].substring(lines[i].indexOf("=") + 2);
                    break;
                }
            }
            //ATTNDRus

            lines = sendRequest("wan dmt2 show cparams");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("ATTNDRus")) {
                    maxSpeedUp = lines[i].substring(lines[i].indexOf("=") + 2);
                    break;
                }
            }
        }

        lines = sendRequest("sys ver");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].indexOf("ZyNOS version") > -1) {
                sysVersion = lines[i].substring(lines[i].indexOf(":") + 2);
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
         dofirstMeasure();
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
            String[] lines = sendRequest("wan adsl chandata");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].indexOf("near-end bit rate") > -1) {
                    speedDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].indexOf("far-end bit rate") > -1) {
                    speedUp = lines[i].substring(lines[i].indexOf(":") + 2);
                }
            }

            boolean is2Plus = dslStandard.equals("ADSL2PLUS");

            //Graph
            lines = sendRequest("wan adsl linedata far");
            int tonestart = 0;
            for (int i = 0; i < lines.length; i++) {
                if (!is2Plus) {
                    if (lines[i].startsWith("attainable rate upstream: ")) {
                        maxSpeedUp = lines[i].substring(lines[i].indexOf(":") + 2);
                    }
                }
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
                    if (x >= 64) {
                        break;
                    }
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
            lines = sendRequest("wan adsl status wan");
            if (lines.length > 0) {
                ADSLStatus = lines[0].substring(lines[0].indexOf(":") + 2);
            }
            //Margin down...
            lines = sendRequest("wan adsl linedata near");
            for (int i = 0; i < lines.length; i++) {
                if (!is2Plus) {
                    if (lines[i].startsWith("attainable rate downstream: ")) {
                        maxSpeedDown = lines[i].substring(lines[i].indexOf(":") + 2);
                    }
                }
                if (lines[i].startsWith("noise margin downstream: ")) {
                    marginDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("output power upstream: ")) {
                    powerUp = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("attenuation downstream: ")) {
                    attenuationDown = lines[i].substring(lines[i].indexOf(":") + 2);
                }
                if (lines[i].startsWith("tone ")) {
                    tonestart = i;
                    break;
                }
            }
            x = 0;
            for (int i = tonestart; i < lines.length; i++) {
                if (lines[i].startsWith("tone ")) {
                    if (x < 64) {
                        x += 32;
                        continue;
                    }
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
            }
            //ATUC
            lines = sendRequest("wan adsl farituid");
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
                } catch (NumberFormatException nex) {
                    ATUC = "?";
                }
            }
            measureFinish();

    }

    @Override
    public void disconnect() {
        if(!connected) return;
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
        PPPoEActive = false;
        BridgeActive = false;
        try {
            sendLine("exit");
            readAndStopAfterChar(':');
            readByte();
            sendLine("99");
            readLines();
        } catch (IOException ex) {
            System.out.println("Error closing:"+ex.toString());
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
