package jarticulator.tubemodel;

/**
 * Models the junction between two tube segments  
 */
public class Junction
{
	public TubeSegment tube1;
	public TubeSegment tube2;
	

	/**
	* Constructor that creates a junction between two tube segments
	*
	* @param t1	The first tube segment
	* @param t2	The second tube segment
	*/
	public Junction(TubeSegment t1, TubeSegment t2)
	{
		tube1=t1;
		tube2=t2;
	}
		
	/**
	* Simulates reflection of the sound wave in the junction between
	* two tube segments
	*/		
	public void calculate()
	{
		double a1=tube1.area;
		double a2=tube2.area;
		double ufin=tube1.ufout;
		double ubin=tube2.ubout;
		
		double d=(a1-a2)/(a1+a2);
		
		double ubout=(1-d)*ubin - d*ufin;
		double ufout=d*ubin + (1+d)*ufin;
		
		tube2.ufin=ufout;
		tube1.ubin=ubout;
		
	}	
}


