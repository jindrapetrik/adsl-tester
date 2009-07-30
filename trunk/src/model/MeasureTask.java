package model;

import java.io.IOException;
import java.util.TimerTask;

/**
 *
 * @author JPEXS
 */
public class MeasureTask extends TimerTask{
    private boolean measuringNow=false;
    public boolean measuringStopped=false;

    @Override
    public void run() {
        if(measuringNow) return;
        measuringNow=true;
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
        measuringNow=false;
        if(measuringStopped){
            cancel();
            Main.router.disconnect();
            view.Main.view.measuringStop();
        }
    }

}
