package view.mobile;

import eve.fx.Font;
import eve.fx.gui.WindowConstants;
import eve.ui.Application;
import eve.ui.Button;
import eve.ui.CheckBox;
import eve.ui.Form;
import eve.ui.Frame;
import eve.ui.Gui;
import eve.ui.Input;
import eve.ui.Label;
import eve.ui.Menu;
import eve.ui.MenuItem;
import eve.ui.MessageBox;
import eve.ui.SoftKeyBar;
import eve.ui.formatted.TextDisplay;
import model.Main;

/**
 * Main form
 * @author JPEXS
 */
public class MainForm extends Form {


    private Label settingsLabel = new Label(view.Main.language.modemSettings);
    private Frame settingsFrame = new Frame();
    private Frame settingsInnerFrame = new Frame();
    private Frame settingsFieldsFrame = new Frame();
    //private BooleanDisplay PPPoEBooleanDisplay = new BooleanDisplay(view.Main.language.PPPoE);
    //private BooleanDisplay bridgeBooleanDisplay = new BooleanDisplay(view.Main.language.bridge);
    private Frame booleanDisplayFrame = new Frame();
    private Label modeLabel = new Label(view.Main.language.mode);
    private Label ATTNDRdsLabel = new Label(view.Main.language.ATTNDRds);
    private Label protocolLabel = new Label(view.Main.language.protocol);
    private Label encapsulationLabel = new Label(view.Main.language.encapsulation);
    private Label vpivciLabel = new Label(view.Main.language.vpivci);
    private Label nameLabel = new Label(view.Main.language.name);
    private Label passwordLabel = new Label(view.Main.language.password);
    private TextDisplay modeTextDisplay = new TextDisplay();
    private TextDisplay ATTNDRdsTextDisplay = new TextDisplay();
    private TextDisplay protocolTextDisplay = new TextDisplay();
    private TextDisplay encapsulationTextDisplay = new TextDisplay();
    private TextDisplay vpivciTextDisplay = new TextDisplay();
    private TextDisplay nameTextDisplay = new TextDisplay();
    private TextDisplay passwordTextDisplay = new TextDisplay();
    private Frame graphFrame=new Frame();
    private Frame graphCol2Frame=new Frame();
    //private Graph adslGraph = new Graph();
    private Frame lineMeasureFrame = new Frame();
    private Label lineMeasureLabel = new Label(view.Main.language.lineMeasure);
    private Frame lineMeasureRow1Frame = new Frame();
    private Frame lineMeasureErrorsFrame = new Frame();
    private TextDisplay marginDownTextDisplay = new TextDisplay();
    private TextDisplay powerDownTextDisplay = new TextDisplay();
    private TextDisplay attenuationDownTextDisplay = new TextDisplay();
    private Label marginLabel = new Label(view.Main.language.margin);
    private TextDisplay marginUpTextDisplay = new TextDisplay();
    private Label powerLabel = new Label(view.Main.language.power);
    private TextDisplay powerUpTextDisplay = new TextDisplay();
    private Label attenuationLabel = new Label(view.Main.language.attenuation);
    private TextDisplay attenuationUpTextDisplay = new TextDisplay();
    private Label dslStandardLabel = new Label(view.Main.language.dslStandard);
    private TextDisplay dslStandardTextDisplay = new TextDisplay();
    private Label speedLabel = new Label(view.Main.language.speed);
    private Label maxSpeedLabel = new Label(view.Main.language.maxSpeed);
    private TextDisplay speedDownTextDisplay = new TextDisplay();
    private TextDisplay speedUpTextDisplay = new TextDisplay();
    private TextDisplay maxSpeedDownTextDisplay = new TextDisplay();
    private TextDisplay maxSpeedUpTextDisplay = new TextDisplay();
    private Label statusLabel = new Label(view.Main.language.ADSLStatus);
    private TextDisplay statusTextDisplay = new TextDisplay();
    private Label uptimeLabel = new Label(view.Main.language.uptime);
    private TextDisplay uptimeTextDisplay = new TextDisplay();
    private Label errorsLabel = new Label(view.Main.language.errors);
    private Label FECLabel = new Label(view.Main.language.FEC);
    private Label CRCLabel = new Label(view.Main.language.CRC);
    private Label HECLabel = new Label(view.Main.language.HEC);
    private Label downStreamLabel = new Label(view.Main.language.downStream);
    private Label upStreamLabel = new Label(view.Main.language.upStream);
    private TextDisplay FECDownTextDisplay = new TextDisplay();
    private TextDisplay CRCDownTextDisplay = new TextDisplay();
    private TextDisplay HECDownTextDisplay = new TextDisplay();
    private TextDisplay FECUpTextDisplay = new TextDisplay();
    private TextDisplay CRCUpTextDisplay = new TextDisplay();
    private TextDisplay HECUpTextDisplay = new TextDisplay();
    private Frame lineMeasureErrorsInnerFrame = new Frame();
    private Frame lineMeasureCol1Frame = new Frame();
    private Frame lineMeasureCol2Frame = new Frame();
    private Label modemIPAddressLabel = new Label(view.Main.language.modemIPAddress);
    private Label modemTypeLabel = new Label(view.Main.language.modemType);
    private TextDisplay modemIPAddressTextDisplay = new TextDisplay();
    private TextDisplay modemTypeTextDisplay = new TextDisplay();
    private Label scanEveryLabel = new Label(view.Main.language.scanEveryXSec);
    private Input scanEveryInput = new Input();
    private CheckBox logCheckBox = new CheckBox(view.Main.language.doLog);
    private Button startMeasureButton = new Button(view.Main.language.startMeasure);
    private Button stopMeasureButton = new Button(view.Main.language.stopMeasure);
    private Frame mainButtonsFrame = new Frame();
    private Button modemIPChangeButton = new Button(view.Main.language.modemIPChange);
    private Button exitButton = new Button(view.Main.language.exit);
    private StatusDisplay statusDisplay = new StatusDisplay();

    private O2TesterLabel O2ModemTestLabel=new O2TesterLabel();
    private TextDisplay versionTextDisplay=new TextDisplay();
    private Label sysVersionLabel=new Label(view.Main.language.sysVersion);
    private TextDisplay sysVersionTextDisplay = new TextDisplay();
    private Label syncNumLabel=new Label(view.Main.language.syncNumber);
    private TextDisplay syncNumTextDisplay = new TextDisplay();
    public MainForm() {        
        int fieldHeight=19;
        int fullFieldWidth=170;
        int halfFieldWidth=90;
        int twoFieldsWidth=60;

        
        
        setFont(new Font("",0,12));
        scanEveryInput.textCase=Input.CASE_NUMBERS;
        scanEveryInput.setText(""+model.Main.scanInterval);
        title = view.Main.language.mainTitle+" - "+model.Main.version;
        exitButton.setAction("EXIT");
        exitButton.addListener(controller.Main.mainEventListener);
        modemIPChangeButton.setAction("CHANGEIP");
        modemIPChangeButton.addListener(controller.Main.mainEventListener);
        modemIPChangeButton.setFixedSize(100, 16);
        startMeasureButton.setAction("START");
        startMeasureButton.addListener(controller.Main.mainEventListener);
        stopMeasureButton.setAction("STOP");
        stopMeasureButton.addListener(controller.Main.mainEventListener);
        
        //addNext(O2ModemTestLabel,Frame.CENTER,Frame.CENTER);
        addNext(statusDisplay);
        addNext(downStreamLabel);
        downStreamLabel.setFixedSize(halfFieldWidth, fieldHeight);
        addLast(upStreamLabel);
        upStreamLabel.setFixedSize(halfFieldWidth, fieldHeight);

        speedDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        speedUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(speedLabel);
        addNext(speedDownTextDisplay);        
        addLast(speedUpTextDisplay);        

        maxSpeedDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        maxSpeedUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(maxSpeedLabel);
        addNext(maxSpeedDownTextDisplay);        
        addLast(maxSpeedUpTextDisplay);
        

        marginDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        marginUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(marginLabel);
        addNext(marginDownTextDisplay);        
        addLast(marginUpTextDisplay);
        

        attenuationDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        attenuationUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(attenuationLabel);
        addNext(attenuationDownTextDisplay);        
        addLast(attenuationUpTextDisplay);        

        powerDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        powerUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(powerLabel);
        addNext(powerDownTextDisplay);        
        addLast(powerUpTextDisplay);        

        
        CRCDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        CRCUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(CRCLabel);
        addNext(CRCDownTextDisplay);
        addLast(CRCUpTextDisplay);

        FECDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        FECUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(FECLabel);
        addNext(FECDownTextDisplay);        
        addLast(FECUpTextDisplay);
        

        HECDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        HECUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        addNext(HECLabel);
        addNext(HECDownTextDisplay);        
        addLast(HECUpTextDisplay);
        


        sysVersionTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        addNext(sysVersionLabel);
        addLast(sysVersionTextDisplay);        

        modemTypeTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        addNext(modemTypeLabel);
        addLast(modemTypeTextDisplay);        
        
        addNext(modemIPAddressLabel);
        if (SoftKeyBar.getType() == SoftKeyBar.TYPE_NONE){
            modemIPAddressTextDisplay.setFixedSize(twoFieldsWidth, fieldHeight);
            addNext(modemIPAddressTextDisplay);
            addLast(modemIPChangeButton);            
        }
        else{
            modemIPAddressTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
            addLast(modemIPAddressTextDisplay);
        }

        uptimeTextDisplay.setFixedSize(twoFieldsWidth, fieldHeight);
        syncNumTextDisplay.setFixedSize(30, fieldHeight);
        addNext(uptimeLabel);
        addNext(uptimeTextDisplay);        
        addNext(syncNumLabel);
        addLast(syncNumTextDisplay,Frame.RIGHT,Frame.RIGHT);
        

        startMeasureButton.setFixedSize(75, 20);
        stopMeasureButton.setFixedSize(75, 20);
        exitButton.setFixedSize(75, 20);
        Frame buttonsFrame=new Frame();
        buttonsFrame.addNext(startMeasureButton);
        buttonsFrame.addNext(stopMeasureButton);
        buttonsFrame.addLast(exitButton);


        addNext(new Frame());
        addNext(logCheckBox);
        scanEveryInput.setFixedSize(30, fieldHeight);
        addNext(scanEveryLabel);
        addLast(scanEveryInput,Frame.RIGHT,Frame.RIGHT);







        if (SoftKeyBar.getType() == SoftKeyBar.TYPE_NONE){
            addLast(buttonsFrame);
        }else{
                            SoftKeyBar sk = new SoftKeyBar();
                            Menu left = new Menu();
                            left.addItem(sk.createMenuItem(startMeasureButton));
                            left.addItem(sk.createMenuItem(stopMeasureButton));
                            left.addItem(sk.createMenuItem(modemIPChangeButton));
                            if (SoftKeyBar.numberOfKeys() == 1){
                                    left.addItem("-");
                                    left.addItem(sk.createMenuItem(exitButton));
                            }else{
                                    sk.setKey(2,exitButton);
                            }
                            sk.setKey(1, view.Main.language.actions,left);
                            setSoftKeyBarFor(null, sk);

             }

             //fullScreenOnPDA();
    }

    public void display() {
        stopMeasureButton.set(Frame.Disabled, true);
        show();
    }

    public void update() {
        if (Main.router == null) {
            return;
        }
        try {
            modeTextDisplay.setText(Main.router.getMode());
            protocolTextDisplay.setText(Main.router.getProtocol());
            encapsulationTextDisplay.setText(Main.router.getEncapsulation());
            vpivciTextDisplay.setText(Main.router.getVpivci());
            nameTextDisplay.setText(Main.router.getName());
            passwordTextDisplay.setText(Main.router.getPassword());

            marginDownTextDisplay.setText(Main.router.getMarginDown());
            powerDownTextDisplay.setText(Main.router.getPowerDown());
            attenuationDownTextDisplay.setText(Main.router.getAttenuationDown());
            marginUpTextDisplay.setText(Main.router.getMarginUp());
            powerUpTextDisplay.setText(Main.router.getPowerUp());
            attenuationUpTextDisplay.setText(Main.router.getAttenuationUp());

            dslStandardTextDisplay.setText(Main.router.getDslStandard());
            speedDownTextDisplay.setText("" + Main.router.getSpeedDown());
            speedUpTextDisplay.setText("" + Main.router.getSpeedUp());
            statusTextDisplay.setText(Main.router.getADSLStatus());
            uptimeTextDisplay.setText(Main.router.getUptime());

            FECDownTextDisplay.setText(Main.router.getErrorsDownStreamFEC());
            CRCDownTextDisplay.setText(Main.router.getErrorsDownStreamCRC());
            HECDownTextDisplay.setText(Main.router.getErrorsDownStreamHEC());
            FECUpTextDisplay.setText(Main.router.getErrorsUpStreamFEC());
            CRCUpTextDisplay.setText(Main.router.getErrorsUpStreamCRC());
            HECUpTextDisplay.setText(Main.router.getErrorsUpStreamHEC());

            modemIPAddressTextDisplay.setText(Main.router.getAdress());
//            adslGraph.setGraphData(Main.router.getGraphData());
 //           PPPoEBooleanDisplay.setOn(Main.router.isPPPoEActive());
 //           bridgeBooleanDisplay.setOn(Main.router.isBridgeActive());
            modemTypeTextDisplay.setText(Main.router.getTypeAndModel());
            maxSpeedDownTextDisplay.setText(Main.router.getMaxSpeedDown());
            maxSpeedUpTextDisplay.setText(Main.router.getMaxSpeedUp());
            syncNumTextDisplay.setText(""+Main.router.getSyncNum());
            sysVersionTextDisplay.setText(Main.router.getSysVersion());
            repaint();
        } catch (NullPointerException nex) {
        }

    }

    @Override
    protected boolean canExit(int exitCode) {
        Application.exit(0);
        return true;
    }


    public boolean isLogEnabled(){
        return logCheckBox.getState();
    }

    public int getScanDelay() {
        int i = 1;
        try {
            i = Integer.parseInt(scanEveryInput.getText());
        } catch (NumberFormatException ex) {
            scanEveryInput.setText("1");
        }
        return i;
    }

    public void connectingDisplay() {
        statusDisplay.setStatusText(view.Main.language.connecting);
        statusDisplay.setDisplayed(true);
        statusDisplay.repaint();
    }

    public void connectingHide() {
        statusDisplay.setDisplayed(false);
    }

    public void measuringStart() {
        statusDisplay.setStatusText(view.Main.language.measuring);
        statusDisplay.setDisplayed(true);
    }

    public void measuringFinish() {
        statusDisplay.setDisplayed(false);
    }

    public void startMeasurement(){
        startMeasureButton.set(Frame.Disabled, true);
        stopMeasureButton.set(Frame.Disabled, false);
        modemIPChangeButton.set(Frame.Disabled, true);
        exitButton.set(Frame.Disabled, true);
        scanEveryInput.set(Frame.Disabled, true);
        logCheckBox.set(Frame.Disabled, true);
    }

    public void measuringStop(){
        startMeasureButton.set(Frame.Disabled, false);
        stopMeasureButton.set(Frame.Disabled, true);
        modemIPChangeButton.set(Frame.Disabled, false);
        exitButton.set(Frame.Disabled, false);
        scanEveryInput.set(Frame.Disabled, false);
        logCheckBox.set(Frame.Disabled, false);
        modemIPChangeButton.set(Frame.Disabled, false);
        repaint();
    }

    public void measuringFirstStart() {
        statusDisplay.setStatusText(view.Main.language.firstMeasuring);
        statusDisplay.setDisplayed(true);
        repaint();
    }

    public void displayConnectionError() {
        (new MessageBox(view.Main.language.error, view.Main.language.cannotConnect, MessageBox.MBOK)).execute();
    }
}
