package jarticulator.articulatorymodel;

/**
 * Simulates tongue movement for principal component 2  
 */
public class Tongue3Movement extends Movement
{
	
  	public double tongueTransform[][] = {
		{0,	0},
		{-0.04,	0.01},
		{-0.07,	0.01},
		{-0.26,	0.05},
		{-0.67,	0.13},
		{-0.4,	0.08},
		{-0.22,	0.04},
		{-0.14,	0.03},
		{-0.11,	0.02},
		{-0.09,	0.02},
		{-0.09,	0},
		{-0.08,	-0.02},
		{-0.07,	-0.03},
		{-0.06,	-0.04},
		{-0.05,	-0.05},
		{-0.04,	-0.06},
		{-0.04,	-0.09},
		{-0.03,	-0.14},
		{0,	-0.19},
		{0.04,	-0.21},
		{0.09,	-0.22},
		{0.1,	-0.25},
		{0.12,	-0.29},
		{0.14,	-0.34},
		{0.16,	-0.39},
		{0.2,	-0.48},
		{0.22,	-0.52},
		{0,	0},
		{0,	0}};


  		
  	public double palateTransform[][] = {
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0},
		{0,	0}};

 
	public double position;
  		
  		
  	public Tongue3Movement()
	{
		position=0.0;
	}
	

	
}