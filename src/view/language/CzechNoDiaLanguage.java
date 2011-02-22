package view.language;

/**
 *
 * @author JPEXS
 */
public class CzechNoDiaLanguage extends Language {

    public CzechNoDiaLanguage() {
        langId="cs-ascii";
        langName="Czech ASCII";
        mode = "Mod";
        protocol = "Protokol";
        modemSettings = "Nastaveni modemu";
        vpivci = "VPI/VCI";
        name = "Jmeno";
        password = "Heslo";
        lineMeasure = "Mereni linky";
        marginDown = "Margin down";
        attenuationDown = "Utlum down";
        attenuationUp = "Utlum up";
        dslStandard = "DSL standard";
        speedDown = "Rychlost down";
        speedUp = "Rychlost up";
        errors = "Chyby";
        modemIPAddress = "IP modemu";
        modemType = "Typ modemu";
        scanEveryXSec = "Snimat (s)";
        doLog = "Logovat";
        startMeasure = "START mereni";
        stopMeasure = "STOP mereni";
        modemIPChange = "Zmenit modem";
        connecting = "Pripojovani...";
        measuring = "Mereni...";
        error = "Chyba";
        cannotConnect = "Nelze se pripojit!";
        telnetPort = "Telnet port";
        ok = "OK";
        cancel = "Storno";
        mainTitle = "O2 Modem tester";
        parameters = "Parametry";
        invalid = "Neplatne";
        invalidParameters = "Neplatna adresa ci port";
        logHeader = "Mereni modemem 1 dne:2 na IP adrese:3\r\nDSL Standard;Rychlost down;Rychlost up;up time;power up;utlum up;power down;utlum down;fd FEC;fd CRC;fd HEC;id FEC;id CRC;id HEC;fu FEC;fu CRC;fu HEC;iu FEC;iu CRC;iu HEC;margin up;margin down;cas;\r\n";

        speed = "Rychlost";
        attenuation = "Utlum";
        margin = "Margin";
        power = "Power";
        maxSpeedDown = "Max rychlost down";
        maxSpeedUp = "Max rychlost up";
        maxSpeed = "Max rychlost";
        sysVersion = "Verze SW";
        syncNumber = "Pocet sync";

        firstMeasuring="Prvni mereni...";
        connectionPassword="Heslo";
        connectionUserName="Uzivatelske jmeno";
        advancedConfig="Rozsirene";
        actions="Akce";
        bootBaseVersion="Bootbase v.";
        inpDown="INP down";
        inpUp="INP up";

        loggingIn="Prihlasuji...";

    }
}
