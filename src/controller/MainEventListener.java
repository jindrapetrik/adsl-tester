package controller;

import eve.sys.Event;
import eve.sys.EventListener;
import eve.ui.event.ControlEvent;
import model.Main;
import model.StandardChangeable;

/**
 *
 * @author JPEXS
 */
public class MainEventListener implements EventListener{

    public void onEvent(Event ev) {
        if(ev.type==ControlEvent.PRESSED)
        {
            ControlEvent cev=(ControlEvent)ev;            
            if(cev.action.equals("START"))
            {
                model.Main.scanInterval=view.Main.view.getScanDelay();
                model.Main.setLogEnabled(view.Main.view.isLogEnabled());
                Main.startMeasure();
            }

            if(cev.action.equals("STOP"))
            {
                Main.stopMeasure();
            }

            if(cev.action.equals("CHANGEIP"))
            {
                view.Main.view.showConfig();
            }

            if(cev.action.equals("SETGDMT"))
            {
                if(model.Main.router instanceof StandardChangeable){
                    ((StandardChangeable)model.Main.router).setStandardGDMT();
                }
            }

            if(cev.action.equals("SET2PLUS"))
            {
                if(model.Main.router instanceof StandardChangeable){
                    ((StandardChangeable)model.Main.router).setStandard2Plus();
                }
            }

            if(cev.action.equals("SETMULTI"))
            {
                if(model.Main.router instanceof StandardChangeable){
                    ((StandardChangeable)model.Main.router).setStandardMulti();
                }
            }


            if(cev.action.equals("EXIT"))
            {
                Main.stopMeasure();
                Main.exit();
            }

        }
    }

}
