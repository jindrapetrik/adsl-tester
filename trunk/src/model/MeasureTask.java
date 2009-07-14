package model;

import java.util.TimerTask;

/**
 *
 * @author JPEXS
 */
public class MeasureTask extends TimerTask{

    @Override
    public void run() {
        Main.router.doMeasurement();
        view.Main.view.update();
    }

}
