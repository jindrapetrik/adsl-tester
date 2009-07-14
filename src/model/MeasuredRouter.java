package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Measured router
 * @author JPEXS
 */
public abstract class MeasuredRouter extends Router {

    protected boolean measureDone = false;
    private boolean measuring = false;
    protected String marginDown;
    protected String powerDown;
    protected String attenuationDown;
    protected String marginUp;
    protected String powerUp;
    protected String attenuationUp;
    protected String syncNum;
    protected String speedDown;
    protected String speedUp;
    protected String errorsDownStreamFastFEC;
    protected String errorsDownStreamFastCRC;
    protected String errorsDownStreamFastHEC;
    protected String errorsDownStreamIntFEC;
    protected String errorsDownStreamIntCRC;
    protected String errorsDownStreamIntHEC;
    protected String errorsUpStreamFastFEC;
    protected String errorsUpStreamFastCRC;
    protected String errorsUpStreamFastHEC;
    protected String errorsUpStreamIntFEC;
    protected String errorsUpStreamIntCRC;
    protected String errorsUpStreamIntHEC;
    protected String mode = "";
    protected String protocol = "";
    protected String encapsulation = "";
    protected String vpivci = "";
    protected String name = "";
    protected String password = "";
    protected String model = "";
    protected String dslStandard = "";
    protected String ADSLStatus = "";
    protected String uptime = "";
    protected int graphData[] = new int[512];
    protected boolean PPPoEActive = false;
    protected boolean BridgeActive = false;
    protected String maxSpeedUp = "";
    protected String maxSpeedDown = "";
    protected String sysVersion = "";
    protected String ATUC = "";
    protected String WANMTU = "";
    protected String WANIP = "";
    protected String ES24h = "";
    protected String type = "Router";

    public String getES24h() {
        return ES24h;
    }

    public String getWANIP() {
        return WANIP;
    }

    public String getWANMTU() {
        return WANMTU;
    }

    public String getATUC() {
        return ATUC;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public String getMaxSpeedDown() {
        return maxSpeedDown;
    }

    public String getMaxSpeedUp() {
        return maxSpeedUp;
    }

    public boolean isBridgeActive() {
        return BridgeActive;
    }

    public boolean isPPPoEActive() {
        return PPPoEActive;
    }

    public String getUptime() {
        return uptime;
    }

    public int[] getGraphData() {
        return graphData;
    }

    public String getADSLStatus() {
        return ADSLStatus;
    }

    public String getDslStandard() {
        return dslStandard;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getEncapsulation() {
        return encapsulation;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVpivci() {
        return vpivci;
    }

    public boolean isMeasureDone() {
        return measureDone;
    }

    public boolean isMeasuringActive() {
        return measuring;
    }

    public void doMeasurement() {
        measureStart();
        measureFinish();
    }

    protected void measureStart() {
        measuring = true;
        Main.measureStart();
    }

    protected void measureFirstStart() {
        Main.firstMeasureStart();
    }

    protected void firstMeasure() throws IOException {
        measureFirstStart();
    }

    protected void measureFinish() {
        measureDone = true;
        measuring = false;
        Main.measureFinished();
    }

    public String getErrorsDownStreamFastCRC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamFastCRC;
    }

    public String getErrorsDownStreamCRC() {
        if (errorsDownStreamFastCRC.startsWith("0")) {
            return errorsDownStreamIntCRC;
        } else {
            return errorsDownStreamFastCRC;
        }
    }
    public String getErrorsDownStreamFEC() {
        if (errorsDownStreamFastFEC.startsWith("0")) {
            return errorsDownStreamIntFEC;
        } else {
            return errorsDownStreamFastFEC;
        }
    }
    public String getErrorsDownStreamHEC() {
        if (errorsDownStreamFastHEC.startsWith("0")) {
            return errorsDownStreamIntHEC;
        } else {
            return errorsDownStreamFastHEC;
        }
    }


    public String getErrorsUpStreamCRC() {
        if (errorsUpStreamFastCRC.startsWith("0")) {
            return errorsUpStreamIntCRC;
        } else {
            return errorsUpStreamFastCRC;
        }
    }
    public String getErrorsUpStreamFEC() {
        if (errorsUpStreamFastFEC.startsWith("0")) {
            return errorsUpStreamIntFEC;
        } else {
            return errorsUpStreamFastFEC;
        }
    }
    public String getErrorsUpStreamHEC() {
        if (errorsUpStreamFastHEC.startsWith("0")) {
            return errorsUpStreamIntHEC;
        } else {
            return errorsUpStreamFastHEC;
        }
    }

    public String getErrorsDownStreamFastFEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamFastFEC;
    }



    public String getErrorsDownStreamFastHEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamFastHEC;
    }



    public String getErrorsDownStreamIntCRC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamIntCRC;
    }

    public String getErrorsDownStreamIntFEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamIntFEC;
    }

    public String getErrorsDownStreamIntHEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsDownStreamIntHEC;
    }

    public String getErrorsUpStreamFastCRC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamFastCRC;
    }

    public String getErrorsUpStreamFastFEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamFastFEC;
    }

    public String getErrorsUpStreamFastHEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamFastHEC;
    }

    public String getErrorsUpStreamIntCRC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamIntCRC;
    }

    public String getErrorsUpStreamIntFEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamIntFEC;
    }

    public String getErrorsUpStreamIntHEC() {
        if (!measureDone) {
            doMeasurement();
        }
        return errorsUpStreamIntHEC;
    }

    public String getMarginDown() {
        if (!measureDone) {
            doMeasurement();
        }
        return marginDown;
    }

    public String getMarginUp() {
        if (!measureDone) {
            doMeasurement();
        }
        return marginUp;
    }

    public String getPowerDown() {
        if (!measureDone) {
            doMeasurement();
        }
        return powerDown;
    }

    public String getPowerUp() {
        if (!measureDone) {
            doMeasurement();
        }
        return powerUp;
    }

    public String getSpeedDown() {
        if (!measureDone) {
            doMeasurement();
        }
        return speedDown;
    }

    public String getSpeedUp() {
        if (!measureDone) {
            doMeasurement();
        }
        return speedUp;
    }

    public String getSyncNum() {
        if (!measureDone) {
            doMeasurement();
        }
        return syncNum;
    }

    public String getAttenuationDown() {
        if (!measureDone) {
            doMeasurement();
        }
        return attenuationDown;
    }

    public String getAttenuationUp() {
        if (!measureDone) {
            doMeasurement();
        }
        return attenuationUp;
    }

    /**
     * Converts string to integer, returns def on error
     * @param s String to convert
     * @param def Default value on error
     * @return Integer value of s or def on error
     */
    protected int strtointdef(String s, int def) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public boolean isMeasuring() {
        return measuring;
    }

    protected ArrayList<String> splitString(String s, char splitter) {
        ArrayList<String> ret = new ArrayList<String>();
        String buf = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == splitter) {
                ret.add(buf);
                buf = "";
            } else {
                buf += s.charAt(i);
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return type;
    }
    
    public String getTypeAndModel() {
        return type+" "+model;
    }
}
