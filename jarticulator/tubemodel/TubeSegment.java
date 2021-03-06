package jarticulator.tubemodel;

/**
 * Models the sound propagation through a single tube segment with constant area  
 */
public class TubeSegment
{

	public double ufin; // forward signal input
	public double ubin; // backward signal input
	public double ufout; // forward signal out
	public double ubout; // backward signal out
	
	public double fs; // sampling frequency (Hz)
	
	public double length; // length of tube segment (m)
	public double area; // area of tube segment 
	public double[] bufferf; // buffer for forward delay
	public double[] bufferb; // buffer for backward delay

	public int t; // current sample
	
	/**
	* Constructor that creates a tube segment of a given length and area
	*
	* @param l	The length of the segment
	* @param a	The area of the segment
	*/	
	public TubeSegment(double l, double a)
	{
		length=l;
		area=a;
		
		fs=10000;
		bufferf=new double[100000];
		bufferb=new double[100000];
		
		ufin=0.0;
		ubin=0.0;
		ufout=0.0;
		ubout=0.0;
		
		t=0;
	}
		
	/**
	* Simulates the tube delay during one step
	*/		
	public void step()
	{
		bufferf[t]=ufin;
		//bufferb[t]=ubin;
		if(t>0)
		{
			bufferb[t-1]=ubin;	
		}
		
		double delay=length/340*fs;
		
		if((t-delay)>=0)
		{
			
			ufout=bufferf[(int)(t-delay)];
			ubout=bufferb[(int)(t-delay)];
			
			//interpolate between buffer values
			double d=delay-(int)delay;
			ufout=bufferf[(int)(t-delay)]*(1-d)+bufferf[(int)(t-delay)+1]*d;
			ubout=bufferb[(int)(t-delay)]*(1-d)+bufferb[(int)(t-delay)+1]*d;
		}
				
		t=t+1;
	}	


}




