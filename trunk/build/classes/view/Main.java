package view;

import eve.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import view.desktop.DesktopView;
import view.language.CzechLanguage;
import view.language.CzechNoDiaLanguage;
import view.language.Language;
import view.mobile.MobileView;

/**
 *
 * @author JPEXS
 */
public class Main {
    public static Language language = new Language();
    public static ArrayList<Language> availableLanguages = new ArrayList<Language>();
    public static View view;
    public static boolean forceMobile = false;
    public static String langId = "en";

    public static void start() {
        availableLanguages.add(new Language());
        availableLanguages.add(new CzechLanguage());
        availableLanguages.add(new CzechNoDiaLanguage());

        for (int i = 0; i < availableLanguages.size(); i++) {
            Language lang = availableLanguages.get(i);
            if (lang.langId.equals(langId)) {
                language = lang;
                break;
            }
        }

        if (eve.sys.Device.isMobile() || forceMobile) {
            view = new MobileView();
        } else {
            view = new DesktopView();
        }

        view.showConfig();
    }
}
