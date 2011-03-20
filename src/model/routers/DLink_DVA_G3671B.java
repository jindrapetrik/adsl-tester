package model.routers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Base64Coder;
import model.Main;
import model.MeasuredRouter;

/**
 * DLink router
 * @author JPEXS
 */
public class DLink_DVA_G3671B extends MeasuredRouter {

    public DLink_DVA_G3671B() {
        type = "D-link DVA-G3671B";
    }

    @Override
    public String toString() {
        return "D-link DVA-G3671B";
    }

    @Override
    public void dofirstMeasure() throws IOException {
        super.dofirstMeasure();
        ArrayList<String> lines;
        sysVersion = "?";
        bootBaseVersion = "?";
        password = "?";
        name = "?";
        model = "?";
        lines = sendRequest("swversion");
        if (lines.size() >= 2) {
            sysVersion = lines.get(0);
            bootBaseVersion = lines.get(1);
        }

        if (true) {
            lines = sendRequest("dumpcfg\r\n"); //Output of dumfcfg does not contain CRLF at the end so router header cannot be read successfully
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().indexOf("<WANPPPConnection instance=\"") == 0) {
                    for (int j = i + 1; j < lines.size(); j++) {
                        if (lines.get(j).trim().indexOf("<Username>") == 0) {
                            String un = lines.get(j).trim();
                            un = un.substring(10);
                            un = un.substring(0, un.indexOf("</"));
                            name = un;
                        }
                        if (lines.get(j).trim().indexOf("<Password>") == 0) {
                            String pwBase64 = lines.get(j).trim();
                            pwBase64 = pwBase64.substring(10);
                            pwBase64 = pwBase64.substring(0, pwBase64.indexOf("</"));
                            byte[] decoded = Base64Coder.decode(pwBase64);
                            password = new String(decoded, 0, decoded[decoded.length - 1] == 0 ? decoded.length - 1 : decoded.length);
                        }
                        if (lines.get(j).trim().equals("</WANPPPConnection>")) {
                            break;
                        }
                    }
                }
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
        WANMTU = "?";
        WANIP = "?";
        maxSpeedUp = "?";
        maxSpeedDown = "?";
        inpDown = "?";
        inpUp = "?";



        ArrayList<String> lines;
        measureStart();



        lines = sendRequest("ifconfig ppp0");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("ppp0")) {
                if (i < lines.size() - 2) {
                    if (lines.get(i + 1).indexOf("inet addr") > -1) {
                        String s = lines.get(i + 1).substring(lines.get(i + 1).indexOf(":") + 1);
                        WANIP = s.substring(0, s.indexOf(" "));
                    }
                    if (lines.get(i + 2).indexOf("MTU:") > -1) {
                        String s = lines.get(i + 2).substring(lines.get(i + 2).indexOf("MTU:") + 4);
                        WANMTU = s.substring(0, s.indexOf(" "));
                    }
                }
                break;
            }
        }


        lines = sendRequest("adsl info --Bits");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("Status: ") == 0) {
                String status = lines.get(i).substring(8);
                if (status.equals("Showtime")) {
                    ADSLStatus = "up";
                } else {
                    ADSLStatus = "down";
                }
            }
            if (lines.get(i).indexOf("Max: ") == 0) {
                String max = lines.get(i);
                max = max.substring(max.indexOf("Upstream rate = ") + 16);
                maxSpeedUp = max.substring(0, max.indexOf(","));
                maxSpeedDown = max.substring(max.indexOf("Downstream rate = ") + 18);
            }
            if (lines.get(i).indexOf("Path: ") == 0) {
                String max = lines.get(i);
                max = max.substring(max.indexOf("Upstream rate = ") + 16);
                speedUp = max.substring(0, max.indexOf(","));
                speedDown = max.substring(max.indexOf("Downstream rate = ") + 18);
            }
            if (lines.get(i).startsWith("Tone number")) {
                graphData = new int[512];
                for (int j = 0; j < 512; j++) {
                    if (1 + i + j < lines.size()) {
                        String tone = lines.get(1 + i + j);
                        tone = tone.trim();
                        graphData[j] = Integer.parseInt(tone.substring(tone.lastIndexOf(" ") + 1));
                    }
                }
                i = lines.size() - 1;
            }
        }

        lines = sendRequest("adsl info --vendor");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("ChipSet Vendor Id:") == 0) {
                String a = lines.get(i);
                a = a.substring(18).trim();
                if (a.indexOf(":") > -1) {
                    a = a.substring(0, a.indexOf(":"));
                    ATUC = a;
                }
            }
        }

        String errorsDownStreamCRC = "0";
        String errorsDownStreamFEC = "0";
        String errorsDownStreamHEC = "0";
        String errorsUpStreamCRC = "0";
        String errorsUpStreamFEC = "0";
        String errorsUpStreamHEC = "0";

        lines = sendRequest("adsl info --stats");
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).indexOf("SNR (dB):") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(9);
                tmp = tmp.trim();
                marginDown = tmp.substring(0, tmp.indexOf(" "));
                marginUp = tmp.substring(tmp.lastIndexOf(" ") + 1);
            }
            if (lines.get(i).indexOf("Attn(dB):") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(9);
                tmp = tmp.trim();
                attenuationDown = tmp.substring(0, tmp.indexOf(" "));
                attenuationUp = tmp.substring(tmp.lastIndexOf(" ") + 1);
            }
            if (lines.get(i).indexOf("Pwr(dBm):") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(9);
                tmp = tmp.trim();
                powerDown = tmp.substring(0, tmp.indexOf(" "));
                powerUp = tmp.substring(tmp.lastIndexOf(" ") + 1);
            }
            if (lines.get(i).indexOf("INP:") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(4);
                tmp = tmp.trim();
                inpDown = tmp.substring(0, tmp.indexOf(" "));
                inpUp = tmp.substring(tmp.lastIndexOf(" ") + 1);
            }
            if (lines.get(i).indexOf("Mode:") == 0) {
                dslStandard = lines.get(i).substring(5).trim();
            }

            if (lines.get(i).indexOf("HEC:") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(4);
                tmp = tmp.trim();
                errorsDownStreamHEC = tmp.substring(0, tmp.indexOf(" "));
                errorsUpStreamHEC = tmp.substring(tmp.lastIndexOf(" ") + 1);
            }
            if (lines.get(i).indexOf("Total time = ") == 0) {
                String tmp = lines.get(i);
                tmp = tmp.substring(13);
                String ret = "";
                if (tmp.indexOf(" hours ") > 0) {
                    ret += tmp.substring(0, tmp.indexOf(" hours ")) + ":";
                    tmp = tmp.substring(tmp.indexOf(" hours ") + 7);
                }
                if (tmp.indexOf(" hour ") > 0) {
                    ret += tmp.substring(0, tmp.indexOf(" hour ")) + ":";
                    tmp = tmp.substring(tmp.indexOf(" hour ") + 6);
                }
                if (tmp.indexOf(" min ") > 0) {
                    String m = tmp.substring(0, tmp.indexOf(" min "));
                    if (m.length() == 1) {
                        m = "0" + m;
                    }
                    ret += m + ":";
                    tmp = tmp.substring(tmp.indexOf(" min ") + 5);
                }

                String sec = Main.replaceStr(tmp, " sec", "");
                if (sec.length() == 1) {
                    sec = "0" + sec;
                }
                ret += sec;

                uptime = ret;
                tmp = lines.get(i + 1);
                if (tmp.indexOf("FEC:") == 0) {
                    tmp = tmp.substring(4);
                    tmp = tmp.trim();
                    errorsDownStreamFEC = tmp.substring(0, tmp.indexOf(" "));
                    errorsUpStreamFEC = tmp.substring(tmp.lastIndexOf(" ") + 1);
                }
                tmp = lines.get(i + 2);
                if (tmp.indexOf("CRC:") == 0) {
                    tmp = tmp.substring(4);
                    tmp = tmp.trim();
                    errorsDownStreamCRC = tmp.substring(0, tmp.indexOf(" "));
                    errorsUpStreamCRC = tmp.substring(tmp.lastIndexOf(" ") + 1);
                }

                //TODO: errorsUpStreamIntCRC,...
            }
            if (lines.get(i).indexOf("Latest 1 day time =") == 0) {
                String tmp = lines.get(i + 3);
                if (tmp.indexOf("ES:") == 0) {
                    tmp = tmp.substring(3);
                    tmp = tmp.trim();
                    int esDown = Integer.parseInt(tmp.substring(0, tmp.indexOf(" ")));
                    int esUp = Integer.parseInt(tmp.substring(tmp.lastIndexOf(" ") + 1));
                    ES24h = "" + (esDown + esUp);
                }
            }
            //TODO: Sync num
        }

        try {
            double inpDownD = Double.parseDouble(inpDown);
            if (inpDownD > 1.0) {
                errorsDownStreamIntCRC = errorsDownStreamCRC;
                errorsDownStreamIntFEC = errorsDownStreamFEC;
                errorsDownStreamIntHEC = errorsDownStreamHEC;
            } else {
                errorsDownStreamFastCRC = errorsDownStreamCRC;
                errorsDownStreamFastFEC = errorsDownStreamFEC;
                errorsDownStreamFastHEC = errorsDownStreamHEC;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Chyba:'" + inpDown + "'");
        }

        try {
            double inpUpD = Double.parseDouble(inpUp);
            if (inpUpD > 1.0) {
                errorsUpStreamIntCRC = errorsUpStreamCRC;
                errorsUpStreamIntFEC = errorsUpStreamFEC;
                errorsUpStreamIntHEC = errorsUpStreamHEC;
            } else {
                errorsUpStreamFastCRC = errorsUpStreamCRC;
                errorsUpStreamFastFEC = errorsUpStreamFEC;
                errorsUpStreamFastHEC = errorsUpStreamHEC;
            }
        } catch (NumberFormatException nfe) {
        }

        lines = sendRequest("wan show interface");
        if (lines.size() > 1) {
            String tempvpi = "";
            for (int i = 0; i < lines.size() - 1; i++) {
                String temp = lines.get(1 + i);
                temp = temp.substring(temp.indexOf(" ")).trim();
                temp = temp.substring(temp.indexOf(" ")).trim();
                if (i > 0) {
                    tempvpi += " ";
                }
                tempvpi += temp.substring(0, temp.indexOf(" "));
                temp = temp.substring(temp.indexOf(" ")).trim();
                //protocol=temp.substring(0,temp.indexOf(" "));
                temp = temp.substring(temp.indexOf(" ")).trim();
                temp = temp.substring(temp.indexOf(" ")).trim();
                encapsulation = temp.substring(0, temp.indexOf(" "));
            }
            vpivci = tempvpi;
        }

        lines = sendRequest("wan show");
        if (lines.size() > 3) {
            for (int i = 2; i < lines.size(); i++) {
                String temp = lines.get(i);
                temp = temp.substring(temp.indexOf(" ")).trim();
                temp = temp.substring(temp.indexOf(" ")).trim();
                temp = temp.substring(temp.indexOf(" ")).trim();
                String intface = temp.substring(0, temp.indexOf(" "));
                temp = temp.substring(temp.indexOf(" ")).trim();
                String proto = temp.substring(0, temp.indexOf(" "));
                if (intface.equals("ppp0")) {
                    protocol = proto;
                }
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
            sendRequest("exit");
        } catch (IOException ex) {
        }
        super.disconnect();
    }

    @Override
    public boolean checkRouterHeader(String header) {
        return header.equals(" > ");
    }
}
