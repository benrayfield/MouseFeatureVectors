/** Ben F Rayfield offers this software opensource MIT license */
package mousefeaturevectors.ui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import mousefeaturevectors.Fourier2dGrid;
import mutable.pixels.ScreenUtil;

/** doesnt update the Fourier2dGrid, just displays it. Change it in subclasses, for example. */
public class DisplayFourier2dGrid extends JPanel{
	
	public final Fourier2dGrid grid;
	
	protected BufferedImage img;
	
	public DisplayFourier2dGrid(Fourier2dGrid grid){
		this.grid = grid;
		img = new BufferedImage(grid.freq.length, grid.decay.length, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void paint(Graphics g){
		int h = getHeight(), w = getWidth();
		int freqs = grid.freq.length, decays = grid.decay.length;
		/*double maxRadiusSquared = 0;
		for(int f=0; f<freqs; f++){
			for(int d=0; d<decays; d++){
				double real = grid.real[f][d], imaginary = grid.imaginary[f][d];
				double radiusSquared = real*real + imaginary*imaginary;
				maxRadiusSquared = Math.max(maxRadiusSquared, radiusSquared);
			}
		}
		double maxRadius = Math.sqrt(maxRadiusSquared);
		*/
		double[] maxRadiusPerDecayIndex = new double[decays];
		for(int d=0; d<decays; d++){
			double maxRadiusSquared = 0;
			for(int f=0; f<freqs; f++){
				double real = grid.real[f][d], imaginary = grid.imaginary[f][d];
				double radiusSquared = real*real + imaginary*imaginary;
				maxRadiusSquared = Math.max(maxRadiusSquared, radiusSquared);
			}
			maxRadiusPerDecayIndex[d] = Math.max(maxRadiusPerDecayIndex[d], maxRadiusSquared);
		}
		for(int f=0; f<freqs; f++){
			for(int d=0; d<decays; d++){
				double real = grid.real[f][d], imaginary = grid.imaginary[f][d];
				double radius = Math.sqrt(real*real + imaginary*imaginary);
				//double maxRadius = maxRadiusPerDecayIndex[d];
				//double maxRadius = (d==7 ? 1 : .01)*100;
				double maxRadius = 10;
				double normedRadius = radius/maxRadius;
				int radiusColorByte = ((int)(normedRadius*255.99999999))&0xff;
				int colorARGB = 0xff000000 | (radiusColorByte*0x10101);
				//int colorARGB = 0xffabcdef+f+256*d;
				img.setRGB(f, d, colorARGB);
			}
		}
		ScreenUtil.paintStretched(g, this, getBounds(), img);
	}

}