package view.desktop;

import model.MeasuredRouter;
import view.View;

/**
 *
 * @author JPEXS
 */
public class DesktopView extends View{
    public static MainForm mainForm;
    public static ConnectionForm connectionForm;

    @Override
    public void showMain() {
        if(mainForm==null){
            mainForm = new MainForm();
        }
        mainForm.display();
    }

    @Override
    public void showConfig() {
        if(mainForm==null){
            mainForm = new MainForm();
        }else{
            mainForm.close(0);
        }
        connectionForm = new ConnectionForm();
        connectionForm.display();
    }

    public boolean isLogEnabled(){
        return mainForm.isLogEnabled();
    }

    public int getScanDelay() {
        return mainForm.getScanDelay();
    }

    public void connectingDisplay() {
        mainForm.connectingDisplay();
    }

    public void connectingHide() {
        mainForm.connectingHide();
    }

    public void measuringStart() {
        mainForm.measuringStart();
    }

    public void measuringFinish() {
        mainForm.measuringFinish();
    }

    public void measuringFirstStart() {
        mainForm.measuringFirstStart();
    }

    public void measuringStop(){
        mainForm.measuringStop();
    }

    public void displayConnectionError() {
        mainForm.displayConnectionError();
    }


    public void update() {
        mainForm.update();
    }

    @Override
    public boolean areParametersValid() {
        return connectionForm.areParametersValid();
    }

    @Override
    public int getPort() {
        return connectionForm.getPort();
    }

    @Override
    public String getAddress() {
        return connectionForm.getAddress();
    }

    @Override
    public void displayMessageInvalid() {
        connectionForm.displayMessageInvalid();
    }

    @Override
    public MeasuredRouter getRouter() {
        return connectionForm.getRouter();
    }

    @Override
    public void hideConfig() {
        connectionForm.close(0);
        mainForm.display();
    }

    @Override
    public void showSettings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void hideSettings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void startMeasurement() {
        mainForm.startMeasurement();
    }


    @Override
    public void switchAdvancedConfig() {
        connectionForm.switchAdvanced();
    }

    public String getConnectionUserName(){
        return connectionForm.getConnectionUserName();
    }

    public String getConnectionPassword(){
       return connectionForm.getConnectionPassword();
    }

}
