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
public class MiniLogo extends Frame{
    private Font normalFont;
    private Font smallFont;

    public MiniLogo(){
        setFixedSize((int)(25*view.Main.view.zoom), (int)(25*view.Main.view.zoom));
        normalFont = new Font("", 0, (int)(18*view.Main.view.zoom));
        smallFont = new Font("", Font.BOLD, (int)(10*view.Main.view.zoom));

    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        super.doPaint(g, r);
        int labelLeft=5;

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
