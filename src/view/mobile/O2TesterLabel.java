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
public class O2TesterLabel extends Frame {

    private Font smallFont;
    private Font normalFont;

    public O2TesterLabel() {
        super();
        setFixedSize(100, 15);
        normalFont = new Font("", Font.BOLD, 12);
        smallFont = new Font(normalFont.getName(), normalFont.getStyle(), normalFont.getSize() * 2 / 3);
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
        g.drawText("2", labelLeft+fm.getTextWidth("O"), fm.getHeight() /2);
        g.setFont(normalFont);
        g.drawText(" tester", labelLeft+fm.getTextWidth("O")+fmSmall.getTextWidth("2"), 0);

    }
}
