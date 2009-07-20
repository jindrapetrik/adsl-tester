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
        modemSettings = "Nastavení modemu";
        vpivci = "VPI/VCI";
        name = "Jméno";
        password = "Heslo";
        lineMeasure = "Mìøení linky";
        marginDown = "Margin down";
        attenuationDown = "Útlum down";
        attenuationUp = "Útlum up";
        dslStandard = "DSL standard";
        speedDown = "Rychlost down";
        speedUp = "Rychlost up";
        errors = "Chyby";
        modemIPAddress = "IP modemu";
        modemType = "Typ modemu";
        scanEveryXSec = "Snímat (s)";
        doLog = "Logovat";
        startMeasure = "START mìøení";
        stopMeasure = "STOP mìøení";
        modemIPChange = "Zmìnit modem";
        connecting = "Pøipojování...";
        measuring = "Mìøení...";
        error = "Chyba";
        cannotConnect = "Nelze se pøipojit!";
        telnetPort = "Telnet port";
        ok = "OK";
        cancel = "Storno";
        mainTitle = "O2 Modem tester";
        parameters = "Parametry";
        invalid = "Neplatné";
        invalidParameters = "Neplatná adresa èi port";
        logHeader = "Mìøení modemem 1 dne:2 na IP adrese:3\r\nDSL Standard;Rychlost down;Rychlost up;up time;power up;útlum up;power down;útlum down;fd FEC;fd CRC;fd HEC;id FEC;id CRC;id HEC;fu FEC;fu CRC;fu HEC;iu FEC;iu CRC;iu HEC;margin up;margin down;èas;\r\n";

        speed = "Rychlost";
        attenuation = "Útlum";
        margin = "Margin";
        power = "Power";
        maxSpeedDown = "Max rychlost down";
        maxSpeedUp = "Max rychlost up";
        maxSpeed = "Max rychlost";
        sysVersion = "Verze SW";
        syncNumber = "poèet sync";

        firstMeasuring="První mìøení...";
        connectionPassword="Heslo";
        connectionUserName="Uživatelské jméno";
        advancedConfig="Rozšíøené";
        actions="Akce";
    }
}
