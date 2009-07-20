package model;

import java.io.IOException;

/**
 *
 * @author JPEXS
 */
public class NoAnswerException extends IOException{
    public NoAnswerException(){
        super("No answer from router");
    }
}
