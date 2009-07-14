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
    public static Language language=new Language();
    public static ArrayList<Language> availableLanguages=new ArrayList<Language>();
    public static View view;
    public static boolean forceMobile=false;

    public static void start(){
        availableLanguages.add(new Language());
        availableLanguages.add(new CzechLanguage());
        availableLanguages.add(new CzechNoDiaLanguage());

        File settingsFile=new File("o2tester.cfg");
        if(settingsFile.exists()){
            try {
                InputStream is = settingsFile.toReadableStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String s="";
                while((s=br.readLine())!=null){
                    if(s.startsWith("language=")){
                        String langId=s.substring(s.indexOf("=")+1);
                        for(int i=0;i<availableLanguages.size();i++){
                            Language lang=availableLanguages.get(i);
                            if(lang.langId.equals(langId)){
                                language=lang;
                                break;
                            }
                        }
                    }
                    if(s.startsWith("debugMode=1")){
                        model.Main.setDebugMode(true);
                    }
                    if(s.startsWith("forceMobileView=1")){
                        forceMobile=true;
                    }
                }
                is.close();
            } catch (IOException ex) {

            }
        }

        if(eve.sys.Device.isMobile()||forceMobile)
        {
            view=new MobileView();
        }else{
            view=new DesktopView();
        }
        
        view.showConfig();
    }
}
