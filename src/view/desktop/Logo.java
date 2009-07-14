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
public class Logo extends Frame{
    private Font normalFont;
    private Font smallFont;

    public Logo(){
        setFixedSize(100, 60);
        normalFont = new Font("", 0, 55);
        smallFont = new Font("", Font.BOLD, 20);

    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        super.doPaint(g, r);
        int labelLeft=10;

        g.setColor(new Color(0, 0, 128));
        FontMetrics fm = g.getFontMetrics(normalFont);
        FontMetrics fmSmall = g.getFontMetrics(smallFont);
        g.setFont(normalFont);
        g.drawText("O", labelLeft, 0);
        g.setFont(smallFont);
        g.drawText("2", labelLeft+fm.getTextWidth("O")*95/100, fm.getHeight()-fmSmall.getHeight());
        g.setFont(normalFont);
    }



}
