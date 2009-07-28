package view.desktop;

import eve.fx.Font;
import eve.ui.Application;
import eve.ui.Button;
import eve.ui.CheckBox;
import eve.ui.Form;
import eve.ui.Frame;
import eve.ui.Input;
import eve.ui.Label;
import eve.ui.formatted.TextDisplay;
import model.Main;
import model.StandardChangeable;

/**
 * Main form
 * @author JPEXS
 */
public class MainForm extends Form {
    private Label settingsLabel = new Label(view.Main.language.modemSettings);
    private Frame settingsFrame = new Frame();
    private Frame settingsInnerFrame = new Frame();
    private Frame settingsFieldsFrame = new Frame();
    private BooleanDisplay PPPoEBooleanDisplay = new BooleanDisplay(view.Main.language.PPPoE);
    private BooleanDisplay bridgeBooleanDisplay = new BooleanDisplay(view.Main.language.bridge);
    private Frame booleanDisplayFrame = new Frame();
    private Label modeLabel = new Label(view.Main.language.mode);
    
    private Label protocolLabel = new Label(view.Main.language.protocol);
    private Label encapsulationLabel = new Label(view.Main.language.encapsulation);
    private Label vpivciLabel = new Label(view.Main.language.vpivci);
    private Label nameLabel = new Label(view.Main.language.name);
    private Label passwordLabel = new Label(view.Main.language.password);
    private TextDisplay modeTextDisplay = new TextDisplay();

    private TextDisplay protocolTextDisplay = new TextDisplay();
    private TextDisplay encapsulationTextDisplay = new TextDisplay();
    private TextDisplay vpivciTextDisplay = new TextDisplay();
    private TextDisplay nameTextDisplay = new TextDisplay();
    private TextDisplay passwordTextDisplay = new TextDisplay();
    private Frame graphFrame=new Frame();
    private Frame graphCol2Frame=new Frame();
    private Graph adslGraph = new Graph();
    private Frame lineMeasureFrame = new Frame();
    private Label lineMeasureLabel = new Label(view.Main.language.lineMeasure);
    private Frame lineMeasureRow1Frame = new Frame();
    private Frame lineMeasureErrorsFrame = new Frame();
    private Label marginDownLabel = new Label(view.Main.language.marginDown);
    private TextDisplay marginDownTextDisplay = new TextDisplay();
    private Label powerDownLabel = new Label(view.Main.language.powerDown);
    private TextDisplay powerDownTextDisplay = new TextDisplay();
    private Label attenuationDownLabel = new Label(view.Main.language.attenuationDown);
    private TextDisplay attenuationDownTextDisplay = new TextDisplay();
    private Label marginUpLabel = new Label(view.Main.language.marginUp);
    private TextDisplay marginUpTextDisplay = new TextDisplay();
    private Label powerUpLabel = new Label(view.Main.language.powerUp);
    private TextDisplay powerUpTextDisplay = new TextDisplay();
    private Label attenuationUpLabel = new Label(view.Main.language.attenuationUp);
    private TextDisplay attenuationUpTextDisplay = new TextDisplay();
    private Label dslStandardLabel = new Label(view.Main.language.dslStandard);
    private TextDisplay dslStandardTextDisplay = new TextDisplay();
    private Label speedDownLabel = new Label(view.Main.language.speedDown);
    private TextDisplay speedDownTextDisplay = new TextDisplay();
    private Label speedUpLabel = new Label(view.Main.language.speedUp);
    private TextDisplay speedUpTextDisplay = new TextDisplay();
    private Label ADSLStatusLabel = new Label(view.Main.language.ADSLStatus);
    private TextDisplay ADSLStatusTextDisplay = new TextDisplay();
    private Label uptimeLabel = new Label(view.Main.language.uptime);
    private TextDisplay uptimeTextDisplay = new TextDisplay();
    private Label errorsLabel = new Label(view.Main.language.errors);
    private Label fastFECLabel = new Label(view.Main.language.fastFEC);
    private Label fastCRCLabel = new Label(view.Main.language.fastCRC);
    private Label fastHECLabel = new Label(view.Main.language.fastHEC);
    private Label intFECLabel = new Label(view.Main.language.intFEC);
    private Label intCRCLabel = new Label(view.Main.language.intCRC);
    private Label intHECLabel = new Label(view.Main.language.intHEC);
    private Label downStreamLabel = new Label(view.Main.language.downStream);
    private Label upStreamLabel = new Label(view.Main.language.upStream);
    private TextDisplay fastFECDownTextDisplay = new TextDisplay();
    private TextDisplay fastCRCDownTextDisplay = new TextDisplay();
    private TextDisplay fastHECDownTextDisplay = new TextDisplay();
    private TextDisplay intFECDownTextDisplay = new TextDisplay();
    private TextDisplay intCRCDownTextDisplay = new TextDisplay();
    private TextDisplay intHECDownTextDisplay = new TextDisplay();
    private TextDisplay fastFECUpTextDisplay = new TextDisplay();
    private TextDisplay fastCRCUpTextDisplay = new TextDisplay();
    private TextDisplay fastHECUpTextDisplay = new TextDisplay();
    private TextDisplay intFECUpTextDisplay = new TextDisplay();
    private TextDisplay intCRCUpTextDisplay = new TextDisplay();
    private TextDisplay intHECUpTextDisplay = new TextDisplay();
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
    private Logo logo=new Logo();

    private Label maxSpeedDownLabel = new Label(view.Main.language.maxSpeedDown);
    private TextDisplay maxSpeedDownTextDisplay = new TextDisplay();
    private Label maxSpeedUpLabel = new Label(view.Main.language.maxSpeedUp);
    private TextDisplay maxSpeedUpTextDisplay = new TextDisplay();

    private Label syncNumLabel = new Label(view.Main.language.syncNumber);
    private TextDisplay syncNumTextDisplay = new TextDisplay();

    private Label sysVersionLabel = new Label(view.Main.language.sysVersion);
    private TextDisplay sysVersionTextDisplay = new TextDisplay();
    private Label ATUCLabel = new Label(view.Main.language.ATUC);
    private TextDisplay ATUCTextDisplay = new TextDisplay();

    private Label WANMTULabel = new Label(view.Main.language.WANMTU);
    private TextDisplay WANMTUTextDisplay = new TextDisplay();
    
    private Label WANIPLabel = new Label(view.Main.language.WANIP);
    private TextDisplay WANIPTextDisplay = new TextDisplay();

    private Label ES24hLabel = new Label(view.Main.language.ES24h);
    private TextDisplay ES24hTextDisplay = new TextDisplay();

    private Button setGDMTButton=new Button("GDMT");
    private Button set2PlusButton=new Button("2Plus");
    private Button setMultimodeButton=new Button("Multi");

    public MainForm() {
        int textDisplayWidth = 85;
        int errorTextDisplayWidth = 75;
        int textDisplayHeight = 25;
        lineMeasureLabel.setFont(new Font("", Font.BOLD, 20));
        lineMeasureCol1Frame.addLast(lineMeasureLabel, 0, Frame.CENTER);

        lineMeasureRow1Frame.addNext(speedDownLabel);
        lineMeasureRow1Frame.addNext(maxSpeedDownLabel);
        lineMeasureRow1Frame.addNext(marginDownLabel);
        lineMeasureRow1Frame.addNext(attenuationDownLabel);
        lineMeasureRow1Frame.addLast(powerDownLabel);


        scanEveryInput.textCase=Input.CASE_NUMBERS;
        

        lineMeasureRow1Frame.addNext(speedDownTextDisplay);
        speedDownTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        lineMeasureRow1Frame.addNext(maxSpeedDownTextDisplay);
        maxSpeedDownTextDisplay.setFixedSize(100, textDisplayHeight);
        lineMeasureRow1Frame.addNext(marginDownTextDisplay);
        marginDownTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        lineMeasureRow1Frame.addNext(attenuationDownTextDisplay);
        attenuationDownTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        lineMeasureRow1Frame.addLast(powerDownTextDisplay);
        powerDownTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);

        lineMeasureRow1Frame.addNext(speedUpLabel);
        lineMeasureRow1Frame.addNext(maxSpeedUpLabel);
        lineMeasureRow1Frame.addNext(marginUpLabel);        
        lineMeasureRow1Frame.addNext(attenuationUpLabel);
        lineMeasureRow1Frame.addLast(powerUpLabel);

        lineMeasureRow1Frame.addNext(speedUpTextDisplay);
        speedUpTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        lineMeasureRow1Frame.addNext(maxSpeedUpTextDisplay);
        maxSpeedUpTextDisplay.setFixedSize(100, textDisplayHeight);
        lineMeasureRow1Frame.addNext(marginUpTextDisplay);
        marginUpTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        lineMeasureRow1Frame.addNext(attenuationUpTextDisplay);
        attenuationUpTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);                      
        lineMeasureRow1Frame.addLast(powerUpTextDisplay);
        powerUpTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);




        
        


        lineMeasureErrorsInnerFrame.addNext(errorsLabel);
        lineMeasureErrorsInnerFrame.addNext(fastFECLabel);
        lineMeasureErrorsInnerFrame.addNext(fastCRCLabel);
        lineMeasureErrorsInnerFrame.addNext(fastHECLabel);
        lineMeasureErrorsInnerFrame.addNext(intFECLabel);
        lineMeasureErrorsInnerFrame.addNext(intCRCLabel);
        lineMeasureErrorsInnerFrame.addLast(intHECLabel);


        lineMeasureErrorsInnerFrame.addNext(downStreamLabel);
        fastFECDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastFECDownTextDisplay);
        fastCRCDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastCRCDownTextDisplay);
        fastHECDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastHECDownTextDisplay);
        intFECDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(intFECDownTextDisplay);
        intCRCDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(intCRCDownTextDisplay);
        intHECDownTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addLast(intHECDownTextDisplay);

        
        lineMeasureErrorsInnerFrame.addNext(upStreamLabel);
        fastFECUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastFECUpTextDisplay);
        fastCRCUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastCRCUpTextDisplay);
        fastHECUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(fastHECUpTextDisplay);
        intFECUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(intFECUpTextDisplay);
        intCRCUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addNext(intCRCUpTextDisplay);
        intHECUpTextDisplay.setFixedSize(errorTextDisplayWidth, textDisplayHeight);
        lineMeasureErrorsInnerFrame.addLast(intHECUpTextDisplay);

        

        //lineMeasureErrorsFrame.setFixedSize(7 * errorTextDisplayWidth, 5 * textDisplayHeight);
        lineMeasureErrorsFrame.addLast(lineMeasureErrorsInnerFrame);



        lineMeasureCol1Frame.addLast(lineMeasureRow1Frame);
        lineMeasureCol1Frame.addLast(lineMeasureErrorsFrame);

        lineMeasureCol2Frame.addLast(modemIPAddressLabel);
        modemIPAddressTextDisplay.setFixedSize(100, textDisplayHeight);
        lineMeasureCol2Frame.addLast(modemIPAddressTextDisplay);
        lineMeasureCol2Frame.addLast(scanEveryLabel);
        scanEveryInput.setText(""+model.Main.scanInterval);
        scanEveryInput.setFixedSize(textDisplayWidth, textDisplayHeight);
        scanEveryInput.set(Frame.TakesKeyFocus, false);
        lineMeasureCol2Frame.addLast(scanEveryInput);
        logCheckBox.set(Frame.TakesKeyFocus, false);
        lineMeasureCol2Frame.addLast(logCheckBox);
        lineMeasureCol2Frame.addLast(startMeasureButton);
        lineMeasureCol2Frame.addLast(stopMeasureButton);

        lineMeasureFrame.addNext(lineMeasureCol1Frame);
        lineMeasureFrame.addLast(lineMeasureCol2Frame);

        title = view.Main.language.mainTitle+" - "+model.Main.version;
        //settingsFrame.setFixedSize(750, 2 * textDisplayHeight + 30);
        settingsFieldsFrame.addNext(dslStandardLabel);
        settingsFieldsFrame.addNext(protocolLabel);
        settingsFieldsFrame.addNext(encapsulationLabel);
        settingsFieldsFrame.addNext(vpivciLabel);
        settingsFieldsFrame.addNext(nameLabel);
        settingsFieldsFrame.addLast(passwordLabel);

        modeTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        protocolTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        encapsulationTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        vpivciTextDisplay.setFixedSize(100, textDisplayHeight);
        nameTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        passwordTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);

        //settingsFieldsFrame.addNext(modeTextDisplay);
        dslStandardTextDisplay.setFixedSize(180, textDisplayHeight);
        settingsFieldsFrame.addNext(dslStandardTextDisplay);
        settingsFieldsFrame.addNext(protocolTextDisplay);
        settingsFieldsFrame.addNext(encapsulationTextDisplay);
        settingsFieldsFrame.addNext(vpivciTextDisplay);
        settingsFieldsFrame.addNext(nameTextDisplay);
        settingsFieldsFrame.addNext(passwordTextDisplay);

        settingsLabel.setFont(new Font("", Font.BOLD, 20));

        settingsInnerFrame.addLast(settingsLabel, 0, Frame.CENTER);
        settingsInnerFrame.addLast(settingsFieldsFrame);
        settingsInnerFrame.setBorder(Frame.EDGE_BUMP, 2);
        settingsFrame.addNext(settingsInnerFrame);

        booleanDisplayFrame.addLast(PPPoEBooleanDisplay);
        booleanDisplayFrame.addLast(bridgeBooleanDisplay);
        settingsFrame.addLast(booleanDisplayFrame);
        addLast(settingsFrame);

        graphCol2Frame.addNext(uptimeLabel, 0, Frame.CENTER);
        graphCol2Frame.addLast(WANMTULabel, 0, Frame.CENTER);

        uptimeTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        graphCol2Frame.addNext(uptimeTextDisplay, 0, Frame.CENTER);                
        WANMTUTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        graphCol2Frame.addLast(WANMTUTextDisplay, 0, Frame.CENTER);

        graphCol2Frame.addNext(ADSLStatusLabel, 0, Frame.CENTER);
        graphCol2Frame.addLast(WANIPLabel, 0, Frame.CENTER);
        ADSLStatusTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        graphCol2Frame.addNext(ADSLStatusTextDisplay, 0, Frame.CENTER);
        WANIPTextDisplay.setFixedSize(100, textDisplayHeight);
        graphCol2Frame.addLast(WANIPTextDisplay, 0, Frame.CENTER);

        graphCol2Frame.addNext(syncNumLabel, 0, Frame.CENTER);
        graphCol2Frame.addLast(ES24hLabel, 0, Frame.CENTER);
        syncNumTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        graphCol2Frame.addNext(syncNumTextDisplay, 0, Frame.CENTER);       
        ES24hTextDisplay.setFixedSize(textDisplayWidth, textDisplayHeight);
        graphCol2Frame.addLast(ES24hTextDisplay, 0, Frame.CENTER);

        graphCol2Frame.addLast(sysVersionLabel, 0, Frame.CENTER);
        sysVersionTextDisplay.setFixedSize(260, textDisplayHeight);
        graphCol2Frame.addLast(sysVersionTextDisplay, 0, Frame.CENTER);

        Frame frame1=new Frame();
        frame1.addNext(modemTypeLabel);
        frame1.addLast(ATUCLabel, 0, Frame.CENTER);
        modemTypeTextDisplay.setFixedSize(180, textDisplayHeight);
        frame1.addNext(modemTypeTextDisplay);
        ATUCTextDisplay.setFixedSize(60, textDisplayHeight);
        frame1.addLast(ATUCTextDisplay, 0, Frame.CENTER);

        graphCol2Frame.addLast(frame1);
        graphFrame.setBorder(Frame.EDGE_RAISED, 2);
        graphFrame.addNext(adslGraph, 0, Frame.CENTER);
        graphFrame.addLast(graphCol2Frame, Frame.CENTER, Frame.CENTER);
        addLast(graphFrame);
        lineMeasureFrame.setBorder(Frame.EDGE_BUMP, 2);
        //lineMeasureFrame.setFixedSize(750, 7 * textDisplayHeight + 30);
        addLast(lineMeasureFrame);

        Frame sepFrame = new Frame();
        sepFrame.setFixedSize(100, 5);
        addLast(sepFrame);

        //mainButtonsFrame.setFixedSize(750, 60);
        mainButtonsFrame.addNext(logo, Frame.LEFT, Frame.Left);
        mainButtonsFrame.addNext(statusDisplay, Frame.LEFT, Frame.Left);


        Frame sepFrame2 = new Frame();
        if(Main.router instanceof StandardChangeable){
            setGDMTButton.setFixedSize(100, 30);
            setGDMTButton.addListener(controller.Main.mainEventListener);
            setGDMTButton.setAction("SETGDMT");
            set2PlusButton.setFixedSize(100, 30);
            set2PlusButton.addListener(controller.Main.mainEventListener);
            set2PlusButton.setAction("SET2PLUS");
            setMultimodeButton.setFixedSize(100, 30);
            setMultimodeButton.addListener(controller.Main.mainEventListener);
            setMultimodeButton.setAction("SETMULTI");
            
            mainButtonsFrame.addNext(setGDMTButton, Frame.LEFT, Frame.Left);
            mainButtonsFrame.addNext(set2PlusButton, Frame.LEFT, Frame.Left);
            mainButtonsFrame.addNext(setMultimodeButton, Frame.LEFT, Frame.Left);
            sepFrame2.setPreferredSize(50, 30);
        }else{            
            sepFrame2.setPreferredSize(100, 30);
        }
         mainButtonsFrame.addNext(sepFrame2);

        modemIPChangeButton.setFixedSize(130, 30);
        mainButtonsFrame.addNext(modemIPChangeButton, Frame.RIGHT, Frame.Right);
        exitButton.setFixedSize(75, 30);
        mainButtonsFrame.addLast(exitButton, Frame.RIGHT, Frame.Right);
        addLast(mainButtonsFrame);

        exitButton.setAction("EXIT");
        exitButton.addListener(controller.Main.mainEventListener);
        modemIPChangeButton.setAction("CHANGEIP");
        modemIPChangeButton.addListener(controller.Main.mainEventListener);
        startMeasureButton.setAction("START");
        startMeasureButton.addListener(controller.Main.mainEventListener);
        stopMeasureButton.setAction("STOP");
        stopMeasureButton.addListener(controller.Main.mainEventListener);
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
            ADSLStatusTextDisplay.setText(Main.router.getADSLStatus());
            uptimeTextDisplay.setText(Main.router.getUptime());

            fastFECDownTextDisplay.setText(Main.router.getErrorsDownStreamFastFEC());
            fastCRCDownTextDisplay.setText(Main.router.getErrorsDownStreamFastCRC());
            fastHECDownTextDisplay.setText(Main.router.getErrorsDownStreamFastHEC());
            fastFECUpTextDisplay.setText(Main.router.getErrorsUpStreamFastFEC());
            fastCRCUpTextDisplay.setText(Main.router.getErrorsUpStreamFastCRC());
            fastHECUpTextDisplay.setText(Main.router.getErrorsUpStreamFastHEC());

            intFECDownTextDisplay.setText(Main.router.getErrorsDownStreamIntFEC());
            intCRCDownTextDisplay.setText(Main.router.getErrorsDownStreamIntCRC());
            intHECDownTextDisplay.setText(Main.router.getErrorsDownStreamIntHEC());
            intFECUpTextDisplay.setText(Main.router.getErrorsUpStreamIntFEC());
            intCRCUpTextDisplay.setText(Main.router.getErrorsUpStreamIntCRC());
            intHECUpTextDisplay.setText(Main.router.getErrorsUpStreamIntHEC());
            modemIPAddressTextDisplay.setText(Main.router.getAdress());
            adslGraph.setGraphData(Main.router.getGraphData());
            PPPoEBooleanDisplay.setOn(Main.router.isPPPoEActive());
            bridgeBooleanDisplay.setOn(Main.router.isBridgeActive());
            modemTypeTextDisplay.setText(Main.router.getTypeAndModel());
            maxSpeedDownTextDisplay.setText(Main.router.getMaxSpeedDown());
            maxSpeedUpTextDisplay.setText(Main.router.getMaxSpeedUp());
            syncNumTextDisplay.setText(""+Main.router.getSyncNum());
            sysVersionTextDisplay.setText(Main.router.getSysVersion());
            ATUCTextDisplay.setText(Main.router.getATUC());
            WANMTUTextDisplay.setText(Main.router.getWANMTU());
            WANIPTextDisplay.setText(Main.router.getWANIP());
            ES24hTextDisplay.setText(Main.router.getES24h());
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
        setGDMTButton.set(Frame.Disabled, true);
        set2PlusButton.set(Frame.Disabled, true);
        setMultimodeButton.set(Frame.Disabled, true);
        repaint();
    }

    public void measuringFirstStart() {        
        statusDisplay.setStatusText(view.Main.language.firstMeasuring);
        statusDisplay.setDisplayed(true);
        repaint();
    }

    public void measuringStop(){
        startMeasureButton.set(Frame.Disabled, false);
        stopMeasureButton.set(Frame.Disabled, true);
        modemIPChangeButton.set(Frame.Disabled, false);
        exitButton.set(Frame.Disabled, false);
        scanEveryInput.set(Frame.Disabled, false);
        logCheckBox.set(Frame.Disabled, false);
        modemIPChangeButton.set(Frame.Disabled, false);
        setGDMTButton.set(Frame.Disabled, false);
        set2PlusButton.set(Frame.Disabled, false);
        setMultimodeButton.set(Frame.Disabled, false);
        connectingHide();
        repaint();
    }

    public void displayConnectionError() {
        statusDisplay.setStatusText(view.Main.language.cannotConnect);
        statusDisplay.setDisplayed(true);
        statusDisplay.repaint();
    }
}
