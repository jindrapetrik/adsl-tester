package view;

import eve.ui.MessageBox;

/**
 *
 * @author JPEXS
 */
public class Utils {
   public static void alert(String s){
       (new MessageBox(view.Main.language.invalid, s, MessageBox.MBOK)).execute();
    }
    public static void alert(int i){
       alert(""+i);
    }
}
