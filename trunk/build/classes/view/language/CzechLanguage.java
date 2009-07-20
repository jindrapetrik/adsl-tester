package view.language;

/**
 *
 * @author JPEXS
 */
public class CzechLanguage extends Language {

    public CzechLanguage() {
        langId="cs-win1250";
        langName="Czech Win1250";
        mode = "Mod";
        protocol = "Protokol";
        modemSettings = "Nastaven� modemu";
        vpivci = "VPI/VCI";
        name = "Jm�no";
        password = "Heslo";
        lineMeasure = "M��en� linky";
        marginDown = "Margin down";
        attenuationDown = "�tlum down";
        attenuationUp = "�tlum up";
        dslStandard = "DSL standard";
        speedDown = "Rychlost down";
        speedUp = "Rychlost up";
        errors = "Chyby";
        modemIPAddress = "IP modemu";
        modemType = "Typ modemu";
        scanEveryXSec = "Sn�mat (s)";
        doLog = "Logovat";
        startMeasure = "START m��en�";
        stopMeasure = "STOP m��en�";
        modemIPChange = "Zm�nit modem";
        connecting = "P�ipojov�n�...";
        measuring = "M��en�...";
        error = "Chyba";
        cannotConnect = "Nelze se p�ipojit!";
        telnetPort = "Telnet port";
        ok = "OK";
        cancel = "Storno";
        mainTitle = "O2 Modem tester";
        parameters = "Parametry";
        invalid = "Neplatn�";
        invalidParameters = "Neplatn� adresa �i port";
        logHeader = "M��en� modemem 1 dne:2 na IP adrese:3\r\nDSL Standard;Rychlost down;Rychlost up;up time;power up;�tlum up;power down;�tlum down;fd FEC;fd CRC;fd HEC;id FEC;id CRC;id HEC;fu FEC;fu CRC;fu HEC;iu FEC;iu CRC;iu HEC;margin up;margin down;�as;\r\n";

        speed = "Rychlost";
        attenuation = "�tlum";
        margin = "Margin";
        power = "Power";
        maxSpeedDown = "Max rychlost down";
        maxSpeedUp = "Max rychlost up";
        maxSpeed = "Max rychlost";
        sysVersion = "Verze SW";
        syncNumber = "po�et sync";

        firstMeasuring="Prvn� m��en�...";
        connectionPassword="Heslo";
        connectionUserName="U�ivatelsk� jm�no";
        advancedConfig="Roz���en�";
        actions="Akce";
    }
}
