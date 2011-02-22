package view;

import model.MeasuredRouter;

/**
 *
 * @author JPEXS
 */
public abstract class View {
    public double zoom=1.0;

    public abstract void showMain();
    public abstract void showConfig();
    public abstract void hideConfig();
    public abstract void switchAdvancedConfig();

    public abstract boolean isLogEnabled();

    public abstract int getScanDelay();

    public abstract void connectingDisplay();

    public abstract void connectingHide();

    public abstract void loggingInDisplay();

    public abstract void loggingInHide();

    public abstract void measuringStart();

    public abstract void measuringFinish();

    public abstract void measuringFirstStart();

    public abstract void measuringStop();

    public abstract void displayConnectionError();


    public abstract void update();

    public abstract boolean areParametersValid();

    public abstract int getPort();

    public abstract String getAddress();

    public abstract void displayMessageInvalid();

    public abstract MeasuredRouter getRouter();
    public abstract void showSettings();
    public abstract void hideSettings();

    public abstract void startMeasurement();

    public abstract String getConnectionUserName();
    public abstract String getConnectionPassword();
    public void setTab(int index){
    }

}
