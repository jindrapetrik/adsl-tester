package view.desktop;

import eve.fx.Color;
import eve.fx.Font;
import eve.fx.FontMetrics;
import eve.fx.Graphics;
import eve.fx.Rect;
import eve.ui.Panel;
import java.util.Random;

/**
 *
 * @author JPEXS
 */
public class Graph extends Panel {

    private int borderwidth = 20;
    private int itemHeight = 10;
    private int graphData[] = new int[512];
    private int leftOffset=10;

    public void setGraphData(int[] graphData) {
        this.graphData = graphData;
    }

    public Graph() {
        setFixedSize(580, borderwidth + 16 * itemHeight + borderwidth);
    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        super.doPaint(g, r);
        g.setColor(Color.Black);
        g.setFont(new Font("", 0, 12));
        FontMetrics fm = g.getFontMetrics(g.getFont());
        int lineLength = 4;
        int leftwidth = fm.getTextWidth("16") + 4 + lineLength;

        g.drawRect(leftOffset + leftwidth, borderwidth, 512 + 2, 16 * itemHeight + 2);
        for (int i = 0; i <= 500; i += 20) {
            if (i == 500) {
                i = 511;
            }
            g.drawText("" + i, leftOffset + leftwidth + i - fm.getTextWidth("" + i) / 2, borderwidth + 16 * itemHeight + lineLength);
            g.drawLine(leftOffset + leftwidth + i, borderwidth + 16 * itemHeight, leftOffset + leftwidth + i, borderwidth + 16 * itemHeight + lineLength);
        }

        for (int i = 0; i <= 16; i += 2) {
            g.drawText("" + i, leftOffset + fm.getTextWidth("16") - fm.getTextWidth("" + i), borderwidth + (16 - i) * itemHeight - fm.getHeight() / 2);
            g.drawLine(leftOffset + leftwidth, borderwidth + (16 - i) * itemHeight, leftOffset + leftwidth - lineLength, borderwidth + (16 - i) * itemHeight);
        }

        g.setColor(Color.Red);
        for (int i = 0; i < graphData.length; i++) {
            if (i == 64) {
                g.setColor(new Color(0, 0, 255));
            }
            if (graphData[i] == 0) {
                continue;
            }
            g.drawLine(leftOffset + 1 + leftwidth + i, borderwidth + (16 - graphData[i]) * itemHeight, leftOffset + 1 + leftwidth + i, borderwidth + 16 * itemHeight);
        }
    }
}
