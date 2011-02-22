package view.mobile;

import eve.fx.Color;
import eve.fx.Font;
import eve.fx.FontMetrics;
import eve.fx.Graphics;
import eve.fx.Rect;
import eve.ui.Frame;

/**
 *
 * @author JPEXS
 */
public class StatusDisplay extends Frame{
    private boolean displayed=false;
    private String statusText="";
    private int type=0;

    public static final int TYPE_RED=0;
    public static final int TYPE_YELLOW=1;
    public static final int TYPE_GREEN=2;

    public void setType(int type){
        this.type=type;
    }


    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
        repaint();
    }



    public StatusDisplay(){        
        setFixedSize((int)(120*view.Main.view.zoom), (int)(15*view.Main.view.zoom));
    }



    @Override
    public void doPaint(Graphics g, Rect r) {
        if(displayed){
            switch(type){
                case 0:
                    g.setColor(new Color(255,0,0));
                    break;
                case 1:
                    g.setColor(new Color(255,255,0));
                    break;
                case 2:
                    g.setColor(new Color(0,128,0));
                    break;
            }
            g.fillRect(0, 0, getWidth(), getHeight());            
            Font myfont=new Font("",0,(int)(12*view.Main.view.zoom));
            g.setFont(myfont);
            FontMetrics fm=g.getFontMetrics(myfont);
            switch(type){
                case 0:
                    g.setColor(new Color(255,255,255));
                    break;
                case 1:
                    g.setColor(new Color(0,0,0));
                    break;
                case 2:
                    g.setColor(new Color(255,255,0));
                    break;
            }
            g.setForeground(new Color(255,255,0));
            g.drawText(statusText, (int)(10*view.Main.view.zoom), getHeight()/2-fm.getHeight()/2);
        }
        else{
            g.setColor(Color.LightGray);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        repaint();
    }



}
