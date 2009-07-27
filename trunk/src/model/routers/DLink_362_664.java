package model.routers;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author JPEXS
 */
public class DLink_362_664 extends DLink_584_684{

    private static boolean firstHash=true;
    
     @Override
    public ArrayList<String> readLines() throws IOException {
        firstHash=false;
        return super.readLines();
    }

    @Override
    public String readLine() throws IOException {
        firstHash=false;
        return super.readLine();
    }





    @Override
    public boolean checkRouterHeader(String header) {

        if(header.equals("# ")){
            firstHash=!firstHash;
            if(!firstHash)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "D-link 362/664";
    }
}
