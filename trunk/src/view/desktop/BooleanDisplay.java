
package view.desktop;

import eve.fx.Graphics;
import eve.fx.Rect;
import eve.ui.Frame;
import eve.fx.Color;
import eve.fx.Font;
import eve.fx.FontMetrics;

/**
 *
 * @author JPEXS
 */
public class BooleanDisplay extends Frame{
    private boolean on=false;
    private String caption="";   


    public BooleanDisplay(String caption){
        this.caption=caption;
        font=new Font("",0,12);
        setPreferredSize(100, 30);
        setBorder(Frame.EDGE_RAISED, 2);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        repaint();
    }


    @Override
    public void doPaint(Graphics g, Rect r) {
        super.doPaint(g, r);
        if(on){
            g.setColor(new Color(0,169,0));
        }else{
            g.setColor(Color.LightGray);
        }
        g.fillRect(2, 2, getWidth()-4, getHeight()-4);
        g.setColor(Color.White);
        g.setFont(font);
        FontMetrics fm=g.getFontMetrics(font);
        g.drawText(caption, getWidth()/2-fm.getTextWidth(caption)/2, getHeight()/2-fm.getHeight()/2);
    }






    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
        repaint();
    }


}
