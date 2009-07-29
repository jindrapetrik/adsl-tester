package view.mobile;

import eve.ui.Button;
import eve.ui.Choice;
import eve.ui.Form;
import eve.ui.Frame;
import eve.ui.Input;
import eve.ui.Label;
import eve.ui.MessageBox;
import eve.ui.Panel;
import eve.ui.PasswordInput;
import java.net.InetAddress;
import java.net.UnknownHostException;
import model.MeasuredRouter;

/**
 *
 * @author JPEXS
 */
public class ConnectionForm extends Form {

    private Label ipLabel = new Label(view.Main.language.modemIPAddress);
    private Input ipInput = new Input();
    private Label portLabel = new Label(view.Main.language.telnetPort);
    private Input portInput = new Input();
    private Frame buttonFrame = new Frame();
    private Button okButton = new Button(view.Main.language.ok);
    private Button cancelButton = new Button(view.Main.language.cancel);
    private Label routerLabel = new Label(view.Main.language.modemType);
    private Choice routersList = new Choice();//List(1, 1, false);
    private static int lastSelectedModem=0;

    private Label connectionUserNameLabel=new Label(view.Main.language.connectionUserName);
    private Input connectionUserNameInput=new Input();
    private Label connectionPasswordLabel=new Label(view.Main.language.connectionPassword);
    private PasswordInput connectionPasswordInput=new PasswordInput();

    private Frame advancedFrame=new Frame();
    private Button advancedButton=new Button(view.Main.language.advancedConfig);
    private boolean advancedVisible=false;

    @Override
    public void shown() {
        super.shown();
        advancedFrame.setHidden(true, true);
    }


    public void switchAdvanced(){
        if(!advancedVisible){
            advancedFrame.setHidden(false, true);
        }else{
            advancedFrame.setHidden(true, true);
        }
        advancedVisible=!advancedVisible;
    }

    public ConnectionForm() {
        addNext(ipLabel);
        addLast(ipInput);

        advancedFrame.addNext(portLabel);
        advancedFrame.addLast(portInput);
        advancedFrame.addNext(connectionUserNameLabel);
        advancedFrame.addLast(connectionUserNameInput);
        connectionUserNameInput.setText(model.Main.connectionUserName);
        advancedFrame.addNext(connectionPasswordLabel);
        advancedFrame.addLast(connectionPasswordInput);
        connectionPasswordInput.setText(model.Main.connectionPassword);
        addNext(routerLabel);
        addLast(routersList);
        Panel psep=new Panel();
        psep.setFixedSize(100, 20);
        addLast(psep);
        addLast(advancedFrame);
        advancedFrame.setBorder(Frame.EDGE_RAISED, 2);
        okButton.setAction("OK");
        okButton.setPreferredSize(75, 25);
        okButton.addListener(controller.Main.connectionEventListener);
        cancelButton.setAction("CANCEL");
        cancelButton.addListener(controller.Main.connectionEventListener);
        advancedButton.setAction("ADVANCED");
        advancedButton.addListener(controller.Main.connectionEventListener);
        buttonFrame.addNext(okButton);
        buttonFrame.addNext(advancedButton);
        buttonFrame.addLast(cancelButton);
        //routersList.setFixedSize(100, 75);
        for (int i = 0; i < model.Main.routers.size(); i++) {
            routersList.addItem(model.Main.routers.get(i));
        }
        routersList.select(lastSelectedModem);
        title = view.Main.language.parameters;
        addLast(buttonFrame);
    }

    public MeasuredRouter getRouter() {
        lastSelectedModem=routersList.selectedIndex;
        return model.Main.routers.get(routersList.selectedIndex);
    }



    public void display() {
        if (model.Main.router == null) {
            ipInput.setText(model.Main.defaultIP);
            portInput.setText(""+model.Main.defaultPort);
        } else {
            ipInput.setText(model.Main.router.getAdress());
            portInput.setText("" + model.Main.router.getPort());
        }
        routersList.select(lastSelectedModem);
        advancedFrame.setHidden(true, true);
        show();
    }

    public boolean areParametersValid() {
        try {
            int port = Integer.parseInt(portInput.getText());
        } catch (NumberFormatException ex) {
            return false;
        }
        try {
            InetAddress.getByName(ipInput.getText());
        } catch (UnknownHostException ex) {
            return false;
        }
        return true;
    }

    public int getPort() {
        int port = 23;
        try {
            port = Integer.parseInt(portInput.getText());
        } catch (NumberFormatException ex) {
        }
        return port;
    }

    public String getAddress() {
        String address = ipInput.getText();
        try {
            InetAddress.getByName(address);
            return address;
        } catch (UnknownHostException ex) {
            return "10.0.0.138";
        }
    }

    public String getConnectionUserName(){
        return connectionUserNameInput.getText();
    }

    public String getConnectionPassword(){
        return connectionPasswordInput.getText();
    }

    public void displayMessageInvalid() {
        (new MessageBox(view.Main.language.invalid, view.Main.language.invalidParameters, MessageBox.MBOK)).execute();
    }

    @Override
    protected boolean canExit(int exitCode) {
        if (model.Main.router == null) {
            model.Main.exit();
        }
        return true;
    }
}
