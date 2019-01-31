/** Ben F Rayfield offers this software opensource MIT license */
package mousefeaturevectors.ui;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import mousefeaturevectors.Fourier2dGrid;
import mutable.pixels.ScreenUtil;
import mutable.util.Time;

public class Mouse1dFourier2dGrid extends DisplayFourier2dGrid{
	
	protected double prevTime;

	public Mouse1dFourier2dGrid(Fourier2dGrid grid) {
		super(grid);
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e){
				double now = Time.time();
				double dt = now-prevTime;
				prevTime = now;
				double truncatedDt = Math.min(.1, dt);
				double observeMouse = e.getY();
				grid.add(observeMouse, truncatedDt);
				repaint();
			}
			public void mouseDragged(MouseEvent e){
				mouseMoved(e);
			}
		});
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame(""+Mouse1dFourier2dGrid.class.getName());
		window.setSize(500,500);
		ScreenUtil.moveToScreenCenter(window);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		double[] freqs = new double[300];
		for(int f=0; f<freqs.length; f++){
			double fraction = f/(freqs.length-1.);
			//freqs[f] = 1+.1*fraction;
			freqs[f] = .1*Math.pow(2, fraction*7);
		}
		double[] decays = new double[300];
		for(int d=0; d<decays.length; d++){
			double fraction = d/(decays.length-1.);
			decays[d] = .001*Math.pow(2, fraction*10); //decay per second
		}
		Fourier2dGrid grid = new Fourier2dGrid(freqs, decays);
		window.add(new Mouse1dFourier2dGrid(grid));
		window.setVisible(true);
	}

}
