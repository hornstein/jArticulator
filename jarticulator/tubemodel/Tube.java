package jarticulator.tubemodel;

/**
 * Simulates how sound propagates through a tube.  
 */
public class Tube
{

	public int nrOfSegments; // nr of tube segments
	public double[] length; // length of each tube segment
	public double[] area; // area of each tube segment
	public int Fs; // sampling frequency	
	public int t; // current sample
	
	public TubeSegment[] tubeSegment;
	public Junction[] junction;
	
	/**
	* Constructor that creates a tube from a number of concatenated 
	* tubesegments.
	*
	* @param n	Number of tube segments in the model
	* @param fs	The sampling frequency
	*/
	public Tube(int n, int fs)
	{
		nrOfSegments=n;
		Fs=fs;
		
		length=new double[nrOfSegments];
		area=new double[nrOfSegments];
		tubeSegment=new TubeSegment[nrOfSegments];
		junction=new Junction[nrOfSegments];
		
		t=0;
		
		for(int i=0; i<nrOfSegments; i++)
		{
			length[i]=0.0;
			area[i]=0.0;
			
			tubeSegment[i]=new TubeSegment(length[i], area[i]);
			tubeSegment[i].fs=Fs;
		}
		
		for(int i=0; i<nrOfSegments-1; i++)
		{
			junction[i]=new Junction(tubeSegment[i], tubeSegment[i+1]);
		}

	}

	/**
	* Sets the area of the tube
	*
	* @param A   	Array containing the area of each tube segment
	*/	
	public void setArea(double[] A)
	{
		for(int i=0; i<nrOfSegments; i++)
		{
			tubeSegment[i].area=A[i];
		}		
	}	
		
	/**
	* Sets the length of the tube
	*
	* @param l   	Array containing the length of each tube segment
	*/	
	public void setLength(double[] l)
	{
		for(int i=0; i<nrOfSegments; i++)
		{
			tubeSegment[i].length=l[i];
		}		
	}	
	
	/**
	* Simulates the propagation of sound in the tube during one sample step
	*
	* @param y   	A single sample of the source signal
	* @return	double	A single sample of the synthesized signal
	*/	
	public double step(double y)
	{
		// update source
		tubeSegment[0].ufin=y-tubeSegment[0].ubout;
		
		// update mouth
		tubeSegment[nrOfSegments-1].ubin=0.3*tubeSegment[nrOfSegments-1].ufout;

		// update junctions		
		for(int i=0; i<nrOfSegments-1; i++)
		{
			junction[i].calculate();
		}

		// update segments
		for(int i=0; i<nrOfSegments; i++)
		{
			tubeSegment[i].ufout=tubeSegment[i].ufin;
			tubeSegment[i].ubout=tubeSegment[i].ubin;
		}
 
		t=t+1;
		
		return tubeSegment[nrOfSegments-1].ufout;
	}	


}




