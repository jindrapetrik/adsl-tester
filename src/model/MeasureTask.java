package model;

import java.io.IOException;
import java.util.TimerTask;

/**
 *
 * @author JPEXS
 */
public class MeasureTask extends TimerTask{
    private static boolean measuringNow=false;

    @Override
    public void run() {
        if(measuringNow) return;
        measuringNow=true;
        try {
            if(!Main.router.isConnected()){
                Main.router.login();
            }
            Main.router.doMeasurement();
        } catch (IOException ex) {
            Main.router.disconnect();
            Main.errorConnecting();
        }
        view.Main.view.update();
        measuringNow=false;
    }

}
