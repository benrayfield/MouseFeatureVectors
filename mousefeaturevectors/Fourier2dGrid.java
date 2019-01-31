/** Ben F Rayfield offers this software opensource MIT license */
package mousefeaturevectors;

/** This uses the normal speed decaying fourier instead of the one
thats faster than sine (see Freqing.java in micentangle experiment)
which calculated it this way (but instead here we use sine and cosine
so it can do sparse time (such as mouse movements
<br><br>
Freqing.java and its superclass DecayCnum.java
(important parts for decaying realtime fourier faster than sine):
double fr = fourierReal*multReal - fourierImaginary*multImaginary;
double fi = fourierReal*multImaginary + fourierImaginary*multReal;
double radiusSquared = fr*fr + fi*fi;
//since radiusSquared is already near 1,
//next radiusSquared is (more often than not) closer to 1,
//so approximate norm by derivative instead of expensive sqrt.
//Over log number of cycles this will reach the same accuracy as sqrt,
//except for roundoff from log multiplies of fourierReal+i*fourierImaginary.
double approxRadius = (1+radiusSquared)/2;
fourierReal = fr/approxRadius; //fixes roundoff. converges toward radius=1.
fourierImaginary = fi/approxRadius;
double inRotatedReal = inReal*fourierReal - inImaginary*fourierImaginary;
double inRotatedImaginary = inImaginary*fourierReal + inReal*fourierImaginary;
imaginary = imaginary*(1-decay) + decay*inRotatedImaginary;
real = real*(1-decay) + decay*inRotatedReal;
*/
public class Fourier2dGrid{
	
	/** 2d grid of complexnum. [freqIndex][decayIndex] */
	public double[][] real;
	
	/** 2d grid of complexnum. [freqIndex][decayIndex] */
	public double[][] imaginary;
	
	/** [freqIndex] */
	public double[] freq;
	
	/** [decayIndex] */
	public double[] decay;
	
	/** Increases by about 1 per second continuously but does not increase while paused */
	public double sumTime;
	
	/** arrays can be different sizes. Each is 1 of 2 dims in the other arrays. */
	public Fourier2dGrid(double[] freq, double[] decay){
		this.freq = freq;
		this.decay = decay;
		this.real = new double[freq.length][decay.length];
		this.imaginary = new double[freq.length][decay.length];
	}
	
	/** Same as add(double,double,double) except...
	a real num and change in time. Uses 0 imaginary part of complexnum
	but fourier still works. Would do this if you only have 1 dimension of movement
	such as varying mouseY dimension, mouseY speed, or microphone amplitude.
	*/
	public void add(double observeReal, double dt){
		add(observeReal, 0, dt);
	}
	
	/** Updates real[][] and imaginary[][] and sumTime. A complexnum and change in time.
	This may give NaNs or Infinities soon if dt*decay[anyDecayIndex] is bigger than 1,
	or if negative, and should be 0 to .1 for meaningful signal, the closer to 0 the better.
	*/
	public void add(double observeReal, double observeImaginary, double dt){
		sumTime += dt;
		double circle = 2*Math.PI;
		for(int f=0; f<freq.length; f++){
			double angle = sumTime*freq[f]*circle;
			double rotatingReal = Math.cos(angle);
			double rotatingImaginary = Math.sin(angle);
			double inRotatedReal = observeReal*rotatingReal - observeImaginary*rotatingImaginary;
			double inRotatedImaginary = observeImaginary*rotatingReal + observeReal*rotatingImaginary;
			for(int d=0; d<decay.length; d++){
				double decay = dt*this.decay[d];
				imaginary[f][d] = imaginary[f][d]*(1-decay) + decay*inRotatedReal;
				real[f][d] = real[f][d]*(1-decay) + decay*inRotatedImaginary;
			}
		}
	}

}