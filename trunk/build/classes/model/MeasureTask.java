package model;

import java.io.IOException;
import java.util.TimerTask;

/**
 *
 * @author JPEXS
 */
public class MeasureTask extends TimerTask{

    @Override
    public void run() {
        try {
            if(!Main.router.isConnected()){
                Main.router.login();
                Main.router.dofirstMeasure();
            }
            Main.router.doMeasurement();
        } catch (IOException ex) {
            Main.router.disconnect();
            Main.errorConnecting();
        }
        view.Main.view.update();
    }

}
