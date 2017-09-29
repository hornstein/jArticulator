package jarticulator.articulatorymodel;

/**
 * Simulates tongue movement for principal component 1  
 */
public class Tongue1Movement extends Movement
{
	
  	public double tongueTransform[][] = {
		{0,	0},
		{-0.02,	0},
		{-0.03,	0.01},
		{-0.16,	0.03},
		{-0.5,	0.1},
		{-0.71,	0.14},
		{-0.69,	0.14},
		{-0.69,	0.14},
		{-0.69,	0.14},
		{-0.7,	0.14},
		{-0.7,	0},
		{-0.66,	-0.13},
		{-0.58,	-0.24},
		{-0.47,	-0.31},
		{-0.33,	-0.33},
		{-0.18,	-0.27},
		{-0.05,	-0.13},
		{0.02,	0.1},
		{0,	0.38},
		{-0.12,	0.6},
		{-0.29,	0.69},
		{-0.31,	0.74},
		{-0.31,	0.74},
		{-0.28,	0.68},
		{-0.24,	0.59},
		{-0.21,	0.5},
		{-0.18,	0.44},
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
  		
  		
  	public Tongue1Movement()
	{
		position=0.0;
	}
	

	
}
