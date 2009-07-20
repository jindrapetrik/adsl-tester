package model.routers;

import java.io.IOException;
import model.MeasuredRouter;
import java.util.ArrayList;

/**
 * DLink router
 * @author JPEXS
 */
public class DLink extends MeasuredRouter {

    public DLink(){
        type="D-link";
    }

    @Override
    public String toString() {
        return "D-link";
    }

    @Override
    public void login() throws IOException {
        super.login();
        dofirstMeasure();
    }



    @Override
    public void dofirstMeasure() throws IOException {
        super.dofirstMeasure();
        String[] lines;
        WANMTU = "?";
        WANIP = "?";
        name = "?";
        password = "?";
        maxSpeedDown = "?";
        maxSpeedUp = "?";
        sysVersion = "?";
        lines = sendRequest("grep VERSION= /etc/versions");
        if (lines.length > 0) {
            sysVersion = lines[0].substring(8);
        }
        lines = sendRequest("echo \"begin;connection0:pppoe:settings/password;end\" | cm_cli");
        if (lines.length > 0) {
            password = lines[0];
        }
        //lines = sendRequest("echo \"begin;connection0:pppoe:settings/username;end\" | cm_cli");
        if (lines.length > 0) {
            name = lines[0];
        }
        lines = sendRequest("echo \"begin;snmpcm:settings/system/sysname;end\" | cm_cli");
        if (lines.length > 0) {
            model = lines[0];
        }
        lines = sendRequest("ifconfig ppp0");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("ppp0")) {
                if (i < lines.length - 2) {
                    if (lines[i + 1].indexOf("inet addr")>-1) {
                        String s = lines[i + 1].substring(lines[i + 1].indexOf(":") + 1);
                        WANIP = s.substring(0, s.indexOf(" "));
                    }
                    if (lines[i + 2].indexOf("MTU:")>-1) {
                        String s = lines[i + 2].substring(lines[i + 2].indexOf("MTU:") + 4);
                        WANMTU = s.substring(0, s.indexOf(" "));
                    }
                }
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
        protocol = "?";
        encapsulation = "?";
        ES24h = "?";
        vpivci = "?";


            String lines[];            
            measureStart();




            sendCommand("cd proc/avalanche/");
            lines = sendRequest("cat avsar_modem_stats");
            String s = "";
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].indexOf("US Connection Rate:") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    speedUp = s.substring(0, s.indexOf(" "));
                    speedDown = lines[i].substring(lines[i].lastIndexOf(":") + 1).trim();
                }
                if (lines[i].indexOf("DS Line Attenuation:") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    attenuationDown = s.substring(0, s.indexOf(" "));
                    marginDown = lines[i].substring(lines[i].lastIndexOf(":") + 1).trim();
                }
                if (lines[i].indexOf("US Line Attenuation:") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    attenuationUp = s.substring(0, s.indexOf(" "));
                    marginUp = lines[i].substring(lines[i].lastIndexOf(":") + 1).trim();
                }

                if (lines[i].indexOf("US Transmit Power :") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    powerUp = s.substring(0, s.indexOf(" "));
                    powerDown = lines[i].substring(lines[i].lastIndexOf(":") + 1).trim();
                }

                if (lines[i].indexOf("Trained Mode:") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    String dslString = s.substring(0, s.indexOf(" "));
                    if (dslString.equals("16")) {
                        dslStandard = "ADSL2PLUS";
                    } else if (dslString.equals("3")) {
                        dslStandard = "ADSL";
                    } else {
                        dslStandard = dslString + " (?)";
                    }

                }

                String l1 = "";
                String l2 = "";
                if (lines[i].indexOf("Upstream (TX) Interleave path") > -1) {
                    l1 = lines[i + 1];
                    l2 = lines[i + 2];
                    s = l1.substring(l1.indexOf(":") + 1).trim();
                    errorsUpStreamIntCRC = s.substring(0, s.indexOf(" "));
                    s = s.substring(l1.indexOf(":") + 1).trim();
                    errorsUpStreamIntFEC = s.substring(0, s.indexOf(" "));
                    errorsUpStreamIntHEC = l2.substring(l2.indexOf("HEC:") + 4).trim();
                }
                if (lines[i].indexOf("Downstream (RX) Interleave path") > -1) {
                    l1 = lines[i + 1];
                    l2 = lines[i + 2];
                    s = l1.substring(l1.indexOf(":") + 1).trim();
                    errorsDownStreamIntCRC = s.substring(0, s.indexOf(" "));
                    s = s.substring(l1.indexOf(":") + 1).trim();
                    errorsDownStreamIntFEC = s.substring(0, s.indexOf(" "));
                    errorsDownStreamIntHEC = l2.substring(l2.indexOf("HEC:") + 4).trim();
                }
                if (lines[i].indexOf("Upstream (TX) Fast path") > -1) {
                    l1 = lines[i + 1];
                    l2 = lines[i + 2];
                    s = l1.substring(l1.indexOf(":") + 1).trim();
                    errorsUpStreamFastCRC = s.substring(0, s.indexOf(" "));
                    s = s.substring(l1.indexOf(":") + 1).trim();
                    errorsUpStreamFastFEC = s.substring(0, s.indexOf(" "));
                    errorsUpStreamFastHEC = l2.substring(l2.indexOf("HEC:") + 4).trim();
                }
                if (lines[i].indexOf("Downstream (RX) Fast path") > -1) {
                    l1 = lines[i + 1];
                    l2 = lines[i + 2];
                    s = l1.substring(l1.indexOf(":") + 1).trim();
                    errorsDownStreamFastCRC = s.substring(0, s.indexOf(" "));
                    s = s.substring(l1.indexOf(":") + 1).trim();
                    errorsDownStreamFastFEC = s.substring(0, s.indexOf(" "));
                    errorsDownStreamFastHEC = l2.substring(l2.indexOf("HEC:") + 4).trim();
                }
                if (lines[i].indexOf("DS Max Attainable Bit Rate") > -1) {
                    maxSpeedDown = lines[i].substring(lines[i].lastIndexOf(":") + 2);
                }
                if (lines[i].indexOf("Showtime Count") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 1).trim();
                    syncNum = s.substring(0, s.indexOf(" "));
                }

                if (lines[i].indexOf("US Max Attainable Bit Rate") > -1) {
                    maxSpeedUp = lines[i].substring(lines[i].lastIndexOf(":") + 2);
                }

                if (lines[i].indexOf("ATUC ghsVid") > -1) {
                    s = lines[i].substring(lines[i].indexOf(":") + 2).trim();
                    byte b[] = new byte[4];
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

            }
            lines = sendRequest("cat avsar_bit_allocation_table");
            graphData = new int[512];
            for (int i = 0; i < lines.length; i++) {
                int g = 0;
                if (lines[i].startsWith("AR7 DSL Modem US Bit Allocation")) {
                    g = 0;
                    for (int y = 0; y < 4; y++) {
                        int pos = 0;
                        for (int x = 0; x < 16; x++) {
                            graphData[g] = Integer.parseInt(lines[i + 1 + y].substring(pos, pos + 2).trim(), 16);
                            g++;
                            pos += 3;
                        }

                    }
                }
                if (lines[i].startsWith("AR7 DSL Modem DS Bit Allocation")) {
                    g = 64;
                    for (int y = 4; y < 32; y++) {
                        int pos = 0;
                        for (int x = 0; x < 16; x++) {
                            graphData[g] = Integer.parseInt(lines[i + 1 + y].substring(pos, pos + 2).trim(), 16);
                            g++;
                            pos += 3;
                        }

                    }
                }
            }

            lines = sendRequest("cat avsar_channels");
            for (int i = 0; i < lines.length; i++) {
                // 00    00001   00000   00008   00048
                if (lines[i].startsWith(" 00") && (lines[i].substring(7, 12).equals("00001"))) {
                    String s1 = lines[i].substring(23, 28);
                    String s2 = lines[i].substring(31, 36);
                    vpivci = "" + strtointdef(s1, 0) + "/" + strtointdef(s2, 0);
                    PPPoEActive = true;
                }
                if (lines[i].startsWith(" 01") && (lines[i].substring(7, 12).equals("00001"))) {
                    String s1 = lines[i].substring(23, 28);
                    String s2 = lines[i].substring(31, 36);
                    vpivci += " " + strtointdef(s1, 0) + "/" + strtointdef(s2, 0);
                    BridgeActive = true;
                }
            }


        measureFinish();

    }

    @Override
    public void disconnect() {
        if (!connected) {
            return;
        }
        WANMTU = "?";
        WANIP = "?";
        name = "?";
        password = "?";
        maxSpeedDown = "?";
        maxSpeedUp = "?";
        sysVersion = "?";
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
        return header.equals("# ");
    }
}
