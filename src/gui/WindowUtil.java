package gui;

import java.awt.*;

public class WindowUtil
{

    public WindowUtil()
    {
    }

    public static void centerWindow(Window w)
    {
        Dimension ws = w.getSize();
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)((ss.getWidth() - ws.getWidth()) / 2D);
        int y = (int)((ss.getHeight() - ws.getHeight()) / 2D);
        w.setLocation(x, y);
    }

    public static void attachToFocus(Window jdc)
    {
        Component tmp = DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        attachToCom(tmp, jdc);
    }

    public static void attachToCom(Component tmp, Window jdc)
    {
        if(tmp == null)
        {
            centerWindow(jdc);
        } else
        {
            Point p = tmp.getLocationOnScreen();
            p.translate(0, tmp.getHeight());
            int dy = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - p.getY());
            int dx = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - p.getX());
            if(dy < 210)
            {
                if(dx < 180)
                {
                    p.translate(dx - 180, -(tmp.getHeight() + 210));
                } else
                {
                    p.translate(0, -(tmp.getHeight() + 210));
                }
            } else
            if(dx < 180)
            {
                p.translate(dx - 180, 0);
            }
            jdc.setLocation(p);
        }
    }
}