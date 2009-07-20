package view.desktop;

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


    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
        repaint();
    }



    public StatusDisplay(){        
        setFixedSize(180, 25);
    }



    @Override
    public void doPaint(Graphics g, Rect r) {
        if(displayed){
            g.setColor(new Color(255,0,0));
            g.fillRect(0, 0, getWidth(), getHeight());            
            Font mujfont=new Font("",0,15);
            g.setFont(mujfont);
            FontMetrics fm=g.getFontMetrics(mujfont);
            g.setColor(new Color(255,255,0));
            g.setForeground(new Color(255,255,0));
            g.drawText(statusText, 10, getHeight()/2-fm.getHeight()/2);
        }
        else{
            g.setColor(Color.LightGray);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        setPreferredSize(150, 25);
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        repaint();
    }



}
