package view.mobile;

import eve.fx.Font;
import eve.ui.Application;
import eve.ui.Button;
import eve.ui.CheckBox;
import eve.ui.Form;
import eve.ui.Frame;
import eve.ui.Input;
import eve.ui.Label;
import eve.ui.Menu;
import eve.ui.Panel;
import eve.ui.SoftKeyBar;
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
    private Button setGDMTButton=new Button("GDMT");
    private Button set2PlusButton=new Button("2Plus");
    private Button setMultimodeButton=new Button("Multi");
    private Frame mainButtonsFrame = new Frame();
    private Button modemIPChangeButton = new Button(view.Main.language.modemIPChange);
    private Button exitButton = new Button(view.Main.language.exit);
    private StatusDisplay statusDisplay = new StatusDisplay();

    private MiniLogo logo=new MiniLogo();
    private TextDisplay versionTextDisplay=new TextDisplay();
    private Label sysVersionLabel=new Label(view.Main.language.sysVersion);
    private TextDisplay sysVersionTextDisplay = new TextDisplay();
    private Label syncNumLabel=new Label(view.Main.language.syncNumber);
    private TextDisplay syncNumTextDisplay = new TextDisplay();
    private Label bootBaseVersionLabel = new Label(view.Main.language.bootBaseVersion);
    private TextDisplay bootBaseVersionTextDisplay = new TextDisplay();

    private Button tabLineMeasureButton=new Button(view.Main.language.lineMeasure);
    private Button tabModemSettingsButton=new Button(view.Main.language.modemSettings);
    private Frame tabLineMeasureFrame=new Frame();
    private Frame tabModemSettingsFrame=new Frame();
    private Label ATUCLabel = new Label(view.Main.language.ATUC);
    private TextDisplay ATUCTextDisplay = new TextDisplay();

    private Label WANMTULabel = new Label(view.Main.language.WANMTU);
    private TextDisplay WANMTUTextDisplay = new TextDisplay();

    private Label WANIPLabel = new Label(view.Main.language.WANIP);
    private TextDisplay WANIPTextDisplay = new TextDisplay();

    private Label ES24hLabel = new Label(view.Main.language.ES24h);
    private TextDisplay ES24hTextDisplay = new TextDisplay();

    public MainForm() {

        

        int fieldHeight=(int)(19*view.Main.view.zoom);
        int fullFieldWidth=(int)(150*view.Main.view.zoom);
        int halfFieldWidth=(int)(85*view.Main.view.zoom);
        int twoFieldsWidth=(int)(80*view.Main.view.zoom);

        tabLineMeasureFrame.setBorder(Frame.EDGE_RAISED, 2);
        tabLineMeasureFrame.setPreferredSize((int)(235*view.Main.view.zoom), (int)(220*view.Main.view.zoom));
        tabModemSettingsFrame.setPreferredSize((int)(235*view.Main.view.zoom), (int)(220*view.Main.view.zoom));
        tabModemSettingsFrame.setBorder(Frame.EDGE_RAISED, 2);
        tabLineMeasureButton.setFixedSize((int)(100*view.Main.view.zoom), (int)(25*view.Main.view.zoom));
        tabModemSettingsButton.setFixedSize((int)(100*view.Main.view.zoom), (int)(25*view.Main.view.zoom));


        tabLineMeasureButton.action="TAB0";
        tabLineMeasureButton.addListener(controller.Main.mainEventListener);
        tabModemSettingsButton.action="TAB1";
        tabModemSettingsButton.addListener(controller.Main.mainEventListener);
        Panel tabHeadersPanel=new Panel();
        tabHeadersPanel.setFixedSize((int)(240*view.Main.view.zoom), (int)(27*view.Main.view.zoom));
        tabHeadersPanel.addNext(tabModemSettingsButton);
        tabHeadersPanel.addNext(tabLineMeasureButton);
        tabHeadersPanel.addLast(logo);
        addLast(tabHeadersPanel);

        setFont(new Font("",0,(int)(12*view.Main.view.zoom)));
        scanEveryInput.textCase=Input.CASE_NUMBERS;
        scanEveryInput.setText(""+model.Main.scanInterval);
        title = view.Main.language.mainTitle+" - "+model.Main.version;
        exitButton.setAction("EXIT");
        exitButton.addListener(controller.Main.mainEventListener);
        modemIPChangeButton.setAction("CHANGEIP");
        modemIPChangeButton.addListener(controller.Main.mainEventListener);
        modemIPChangeButton.setFixedSize((int)(90*view.Main.view.zoom), (int)(16*view.Main.view.zoom));
        startMeasureButton.setAction("START");
        startMeasureButton.addListener(controller.Main.mainEventListener);
        stopMeasureButton.setAction("STOP");
        stopMeasureButton.addListener(controller.Main.mainEventListener);
        
        //addNext(O2ModemTestLabel,Frame.CENTER,Frame.CENTER);        
        tabLineMeasureFrame.addNext(new Panel());
        tabLineMeasureFrame.addNext(downStreamLabel);
        downStreamLabel.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addLast(upStreamLabel);
        upStreamLabel.setFixedSize(halfFieldWidth, fieldHeight);

        speedDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        speedUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(speedLabel);
        tabLineMeasureFrame.addNext(speedDownTextDisplay);
        tabLineMeasureFrame.addLast(speedUpTextDisplay);

        maxSpeedDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        maxSpeedUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(maxSpeedLabel);
        tabLineMeasureFrame.addNext(maxSpeedDownTextDisplay);
        tabLineMeasureFrame.addLast(maxSpeedUpTextDisplay);
        

        marginDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        marginUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(marginLabel);
        tabLineMeasureFrame.addNext(marginDownTextDisplay);
        tabLineMeasureFrame.addLast(marginUpTextDisplay);
        

        attenuationDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        attenuationUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(attenuationLabel);
        tabLineMeasureFrame.addNext(attenuationDownTextDisplay);
        tabLineMeasureFrame.addLast(attenuationUpTextDisplay);

        powerDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        powerUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(powerLabel);
        tabLineMeasureFrame.addNext(powerDownTextDisplay);
        tabLineMeasureFrame.addLast(powerUpTextDisplay);

        
        CRCDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        CRCUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(CRCLabel);
        tabLineMeasureFrame.addNext(CRCDownTextDisplay);
        tabLineMeasureFrame.addLast(CRCUpTextDisplay);

        FECDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        FECUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(FECLabel);
        tabLineMeasureFrame.addNext(FECDownTextDisplay);
        tabLineMeasureFrame.addLast(FECUpTextDisplay);
        

        HECDownTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        HECUpTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        tabLineMeasureFrame.addNext(HECLabel);
        tabLineMeasureFrame.addNext(HECDownTextDisplay);
        tabLineMeasureFrame.addLast(HECUpTextDisplay);
        
        Panel group2=new Panel();


        modemTypeTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        group2.addNext(modemTypeLabel);
        group2.addLast(modemTypeTextDisplay);
        Panel group21=new Panel();
        dslStandardTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        group2.addNext(dslStandardLabel);
        group21.addNext(dslStandardTextDisplay);
        group21.addLast(encapsulationLabel);
        group2.addLast(group21);
        
        Panel group22=new Panel();
        protocolTextDisplay.setFixedSize(halfFieldWidth, fieldHeight);
        group2.addNext(protocolLabel);
        group22.addNext(protocolTextDisplay);

        

        encapsulationTextDisplay.setFixedSize((int)(70*view.Main.view.zoom), fieldHeight);
        
        group22.addLast(encapsulationTextDisplay);
        group2.addLast(group22);
        vpivciTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        group2.addNext(vpivciLabel);
        group2.addLast(vpivciTextDisplay);


        sysVersionTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        group2.addNext(sysVersionLabel);
        group2.addLast(sysVersionTextDisplay);
       

        bootBaseVersionTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        group2.addNext(bootBaseVersionLabel);
        group2.addLast(bootBaseVersionTextDisplay);
        group2.addNext(modemIPAddressLabel);
        modemIPAddressTextDisplay.setFixedSize(fullFieldWidth, fieldHeight);
        group2.addLast(modemIPAddressTextDisplay);

        uptimeTextDisplay.setFixedSize(twoFieldsWidth, fieldHeight);
        syncNumTextDisplay.setFixedSize((int)(40*view.Main.view.zoom), fieldHeight);
        statusTextDisplay.setFixedSize((int)(40*view.Main.view.zoom), fieldHeight);

        tabModemSettingsFrame.addLast(group2);

        Panel group1=new Panel();
        group1.addNext(uptimeLabel);
        group1.addNext(uptimeTextDisplay);
        group1.addNext(statusLabel);
        group1.addLast(statusTextDisplay);
        
        
        

        WANMTUTextDisplay.setFixedSize((int)(40*view.Main.view.zoom), fieldHeight);
        WANIPTextDisplay.setFixedSize(twoFieldsWidth, fieldHeight);
        group1.addNext(WANIPLabel);
        group1.addNext(WANIPTextDisplay);
        group1.addNext(WANMTULabel);
        group1.addLast(WANMTUTextDisplay);


        
        ES24hTextDisplay.setFixedSize(twoFieldsWidth, fieldHeight);
        group1.addNext(ES24hLabel);
        group1.addNext(ES24hTextDisplay);
        group1.addNext(syncNumLabel);
        group1.addLast(syncNumTextDisplay);

        Panel group0=new Panel();
        ATUCTextDisplay.setFixedSize((int)(40*view.Main.view.zoom), fieldHeight);
        group0.addNext(ATUCLabel);
        group0.addNext(ATUCTextDisplay);
        nameTextDisplay.setFixedSize((int)(50*view.Main.view.zoom), fieldHeight);
        group0.addNext(nameLabel);
        group0.addNext(nameTextDisplay);
        passwordTextDisplay.setFixedSize((int)(50*view.Main.view.zoom), fieldHeight);
        group0.addNext(passwordLabel);
        group0.addLast(passwordTextDisplay);


        tabModemSettingsFrame.addLast(group1);
        tabModemSettingsFrame.addLast(group0);
        addLast(tabLineMeasureFrame);
        addLast(tabModemSettingsFrame);



        startMeasureButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
        stopMeasureButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
        exitButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
        Frame buttonsFrame=new Frame();
        if(model.Main.router instanceof StandardChangeable){
            setGDMTButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
            set2PlusButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
            setMultimodeButton.setFixedSize((int)(75*view.Main.view.zoom), (int)(20*view.Main.view.zoom));
            setGDMTButton.addListener(controller.Main.mainEventListener);
            setGDMTButton.setAction("SETGDMT");
            set2PlusButton.addListener(controller.Main.mainEventListener);
            set2PlusButton.setAction("SET2PLUS");
            setMultimodeButton.addListener(controller.Main.mainEventListener);
            setMultimodeButton.setAction("SETMULTI");
            buttonsFrame.addNext(setGDMTButton);
            buttonsFrame.addNext(set2PlusButton);
            buttonsFrame.addLast(setMultimodeButton);
        }
        buttonsFrame.addNext(startMeasureButton);
        buttonsFrame.addNext(stopMeasureButton);        
        buttonsFrame.addLast(exitButton);


        Panel measurePanel=new Panel();
        //measurePanel.addNext(logCheckBox);
        //scanEveryInput.setFixedSize(30, fieldHeight);
        //measurePanel.addNext(scanEveryLabel);
        //measurePanel.addNext(scanEveryInput,Frame.RIGHT,Frame.RIGHT);        
        if (SoftKeyBar.getType() == SoftKeyBar.TYPE_NONE){
            measurePanel.addNext(statusDisplay);
            measurePanel.addLast(modemIPChangeButton);
        }else{
            measurePanel.addLast(statusDisplay);
        }
        addLast(measurePanel);

        

   



        if (SoftKeyBar.getType() == SoftKeyBar.TYPE_NONE){
            addLast(buttonsFrame);
        }else{
                            SoftKeyBar sk = new SoftKeyBar();
                            Menu left = new Menu();
                            left.addItem(sk.createMenuItem(startMeasureButton));
                            left.addItem(sk.createMenuItem(stopMeasureButton));
                            if(model.Main.router instanceof StandardChangeable){
                                left.addItem(sk.createMenuItem(setGDMTButton));
                                left.addItem(sk.createMenuItem(set2PlusButton));
                                left.addItem(sk.createMenuItem(setMultimodeButton));
                            }
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

    public void setTab(int index){
        if(index==0){
            tabModemSettingsFrame.setHidden(true, false);
            tabLineMeasureFrame.setHidden(false, true);
            
        }
        if(index==1){
            tabLineMeasureFrame.setHidden(true, false);
            tabModemSettingsFrame.setHidden(false, true);            
        }
        (new Thread(){

            @Override
            public void run() {
                repaint();
                //tabLineMeasureFrame.repaint();
                //tabModemSettingsFrame.repaint();
            }

        }).start();
    }

    public void display() {
        stopMeasureButton.set(Frame.Disabled, true);
        show();
        tabModemSettingsFrame.setHidden(false, true);
        tabLineMeasureFrame.setHidden(true, true);
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
            ATUCTextDisplay.setText(Main.router.getATUC());
            WANMTUTextDisplay.setText(Main.router.getWANMTU());
            WANIPTextDisplay.setText(Main.router.getWANIP());
            ES24hTextDisplay.setText(Main.router.getES24h());
            bootBaseVersionTextDisplay.setText(Main.router.getBootBaseVersion());
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
        statusDisplay.setType(StatusDisplay.TYPE_YELLOW);
        statusDisplay.setStatusText(view.Main.language.connecting);
        statusDisplay.setDisplayed(true);
        statusDisplay.repaint();
    }

    public void connectingHide() {
        statusDisplay.setDisplayed(false);
    }

    public void loggingInDisplay() {
        statusDisplay.setType(StatusDisplay.TYPE_YELLOW);
        statusDisplay.setStatusText(view.Main.language.loggingIn);
        statusDisplay.setDisplayed(true);
        statusDisplay.repaint();
    }

    public void loggingInHide() {
        statusDisplay.setDisplayed(false);
    }

    public void measuringStart() {
        statusDisplay.setType(StatusDisplay.TYPE_GREEN);
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

    public void measuringFirstStart() {
        statusDisplay.setStatusText(view.Main.language.firstMeasuring);
        statusDisplay.setDisplayed(true);
        repaint();
    }

    public void displayConnectionError() {
        statusDisplay.setType(StatusDisplay.TYPE_RED);
        //(new MessageBox(view.Main.language.error, view.Main.language.cannotConnect, MessageBox.MBOK)).execute();
        statusDisplay.setStatusText(view.Main.language.cannotConnect);
        statusDisplay.setDisplayed(true);
        repaint();
    }
}
