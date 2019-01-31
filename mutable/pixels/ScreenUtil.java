/** Ben F Rayfield offers this software opensource MIT license */
package mutable.pixels;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
//import util.MathUtil;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ScreenUtil{
	private ScreenUtil(){}
	
	public static void moveToScreenCenter(Window w){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		w.setLocation((screen.width-w.getWidth())/2, (screen.height-w.getHeight())/2);
	}
	
	public static void moveToHorizontalScreenCenter(Window w){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		w.setLocation((screen.width-w.getWidth())/2, w.getLocation().y);
	}
	
	public static void paintStretched(Graphics g, Component c, Rectangle rect, BufferedImage img){
		double yMagnify = rect.getHeight()/img.getHeight();
		double xMagnify = rect.getWidth()/img.getWidth();
		if(g instanceof Graphics2D){ //stretch to panel size
			Graphics2D G = (Graphics2D)g;
			AffineTransform aftrans = new AffineTransform(xMagnify, 0, 0, yMagnify, 0, 0);
			G.drawImage(img, aftrans, c);
		}else{ //so you can see something but it will be very small
			g.drawImage(img, 0, 0, c);
		}
	}
	
	/*public static int color(float bright){
		return color(bright, bright, bright);
	}
	
	/** Unlike new Color(r,g,b).getRGB(), 1-epsilon rounds to brightest.
	Truncates red, green, and blue into range 0 to 1 if needed.
	*
	public static int color(float red, float green, float blue){
		return 0xff000000 |
			(MathUtil.holdInRange(0, (int)(red*0x100), 0xff) << 16) |
			(MathUtil.holdInRange(0, (int)(green*0x100), 0xff) << 8) |
			MathUtil.holdInRange(0, (int)(blue*0x100), 0xff);
	}
	
	public static int color(float alpha, float red, float green, float blue){
		return (MathUtil.holdInRange(0, (int)(alpha*0x100), 0xff) << 24) |
			(MathUtil.holdInRange(0, (int)(red*0x100), 0xff) << 16) |
			(MathUtil.holdInRange(0, (int)(green*0x100), 0xff) << 8) |
			MathUtil.holdInRange(0, (int)(blue*0x100), 0xff);
	}*/
	
	public static Window getWindow(Component c){
		while(!(c instanceof Window)) c = c.getParent();
		return (Window)c;
	}

}
