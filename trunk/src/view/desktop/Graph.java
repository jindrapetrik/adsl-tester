package view.desktop;

import eve.fx.Color;
import eve.fx.Font;
import eve.fx.FontMetrics;
import eve.fx.Graphics;
import eve.fx.Rect;
import eve.ui.Panel;
import eve.ui.event.PenEvent;
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
    private int displayX=0;

    public void setGraphData(int[] graphData) {
        this.graphData = graphData;
    }

    public Graph() {
        setFixedSize(580, borderwidth + 16 * itemHeight + borderwidth+16);
        addListener(controller.Main.mainEventListener);
        PenEvent.wantPenMoved(this, PenEvent.WANT_PEN_MOVED_INSIDE, true);        
    }

    public void setRandom()
    {
        graphData = new int[512];
        Random rnd=new Random();
        for(int i=0;i<512;i++)
        {
            graphData[i]=rnd.nextInt(16);
        }
    }

    public void diplayPos(int x){
        displayX=x;
        repaint();
    }

    @Override
    public void doPaint(Graphics g, Rect r) {
        super.doPaint(g, r);
        g.setColor(Color.Black);
        g.setFont(new Font("", 0, 12));
        FontMetrics fm = g.getFontMetrics(g.getFont());
        int lineLength = 4;
        int leftwidth = fm.getTextWidth("16") + 4 + lineLength;

        if(displayX<=leftOffset + leftwidth){
            displayX=leftOffset + leftwidth+1;
        }
        if(displayX>leftOffset + leftwidth+511){
            displayX=leftOffset + leftwidth+1+511;
        }

        int displayCarrier=displayX-(leftOffset + leftwidth+1);

        g.setFont(new Font("", 0, 14));

        g.drawText(view.Main.language.carrier+":"+displayCarrier, leftOffset + leftwidth+80, 0);
        g.drawText(view.Main.language.bits+":"+graphData[displayCarrier], leftOffset + leftwidth+80+80, 0);

        double frequency=((double)((int)((4.3125*displayCarrier)*100)))/100;

        g.drawText(view.Main.language.frequency+":"+frequency+" kHz", leftOffset + leftwidth+150+80, 0);


        g.setColor(Color.DarkGray);
        g.drawLine(displayX, borderwidth-5, displayX, getHeight());
        g.setColor(Color.Black);

        g.setColor(Color.DarkGray);
        g.drawLine(leftOffset,borderwidth+(16 - graphData[displayCarrier]) * itemHeight, leftOffset + leftwidth,borderwidth+(16 - graphData[displayCarrier]) * itemHeight);
        g.setColor(Color.Black);


        g.setFont(new Font("", 0, 12));

        //carrier
        g.drawRect(leftOffset + leftwidth, borderwidth, 512 + 2, 16 * itemHeight + 2);
        for (int i = 0; i <= 500; i += 20) {
            if (i == 500) {
                i = 511;
            }
            g.drawText("" + i, leftOffset + leftwidth + i - fm.getTextWidth("" + i) / 2, borderwidth + 16 * itemHeight + lineLength);
            g.drawLine(leftOffset + leftwidth +1+ i, borderwidth + 16 * itemHeight, leftOffset + leftwidth + 1+i, borderwidth + 16 * itemHeight + lineLength);
        }
        g.drawText(view.Main.language.graphCar, 3, borderwidth + 16 * itemHeight+lineLength);

        //frequency
        g.setColor(Color.DarkGray);
        for (int k = 100; k <= 2200; k += 100) {
            int i=k*511/2200;
            g.drawText("" + k, leftOffset + leftwidth + i - fm.getTextWidth("" + i) / 2, borderwidth + 16 * itemHeight +16+ lineLength);
            g.drawLine(leftOffset + leftwidth +1+ i, borderwidth + 16 * itemHeight+16, leftOffset + leftwidth +1+ i, borderwidth + 16 * itemHeight + lineLength+16);
        }

        g.drawText(view.Main.language.graphFreq+"[kHz]", 3, borderwidth + 16 * itemHeight+lineLength+16);
        g.setColor(Color.Black);

        //bits
        for (int i = 0; i <= 16; i += 2) {
            g.drawText("" + i, leftOffset + fm.getTextWidth("16") - fm.getTextWidth("" + i), borderwidth + (16 - i) * itemHeight - fm.getHeight() / 2);
            g.drawLine(leftOffset + leftwidth, borderwidth + (16 - i) * itemHeight, leftOffset + leftwidth - lineLength, borderwidth + (16 - i) * itemHeight);
        }

        g.drawText(view.Main.language.graphBits, 3, borderwidth + 14 * itemHeight+5);

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
