package controller;

import eve.sys.Event;
import eve.sys.EventListener;
import eve.ui.event.ControlEvent;
import java.net.InetAddress;

/**
 *
 * @author JPEXS
 */
public class ConnectionEventListener implements EventListener {

    public void onEvent(Event ev) {
        if (ev.type == ControlEvent.PRESSED) {
            ControlEvent cev = (ControlEvent) ev;
            if (cev.action.equals("OK")) {
                if (!view.Main.view.areParametersValid()) {
                    view.Main.view.displayMessageInvalid();
                } else {
                    if(model.Main.router!=null){
                        model.Main.stopMeasure();
                    }
                    model.Main.router=view.Main.view.getRouter();                    
                    model.Main.connectionPassword=view.Main.view.getConnectionPassword();
                    model.Main.connectionUserName=view.Main.view.getConnectionUserName();
                    model.Main.router.setConnectionUserName(model.Main.connectionUserName);
                    model.Main.router.setConnectionPassword(model.Main.connectionPassword);
                    model.Main.router.setAddressAndPort(view.Main.view.getAddress(), view.Main.view.getPort());
                    view.Main.view.hideConfig();
                }
            }
            if (cev.action.equals("ADVANCED")) {
               view.Main.view.switchAdvancedConfig();
            }
            if (cev.action.equals("CANCEL")) {
               view.Main.view.hideConfig();
                if(model.Main.router==null){
                    model.Main.exit();
                }
            }

        }
    }
}
