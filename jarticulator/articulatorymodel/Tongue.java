package jarticulator.articulatorymodel;

/**
 * Models the lower part of the vocal tract  
 */
public class Tongue
{

  	public double points[][] = {
		{13.52,	1.95},
		{14.38,	3.08},
		{15.25,	4.22},
		{15.51,	5.18},
		{15.18,	6.27},
		{14.84,	7.36},
		{15.07,	8.33},
		{15.41,	9.28},
		{15.65,	10.25},
		{15.7,	11.26},
		{15.62,	12},
		{15.45,	12.69},
		{15.2,	13.33},
		{14.88,	13.92},
		{14.49,	14.49},
		{14.02,	15.02},
		{13.47,	15.54},
		{12.8,	16.01},
		{12,	16.37},
		{11.1,	16.53},
		{10.15,	16.46},
		{9.2,	16.15},
		{8.31,	15.67},
		{7.48,	15.09},
		{6.65,	14.46},
		{5.82,	13.85},
		{4.98,	13.27},
		{3.65,	13.74},
		{2.35,	13.74}};
 
  
  		
  		
  	public Tongue()
	{
	}
	
	public double[][] getClonedPoints()
	{
		double clonedPoints[][] = {
			{13.52,	1.95},
			{14.38,	3.08},
			{15.25,	4.22},
			{15.51,	5.18},
			{15.18,	6.27},
			{14.84,	7.36},
			{15.07,	8.33},
			{15.41,	9.28},
			{15.65,	10.25},
			{15.7,	11.26},
			{15.62,	12},
			{15.45,	12.69},
			{15.2,	13.33},
			{14.88,	13.92},
			{14.49,	14.49},
			{14.02,	15.02},
			{13.47,	15.54},
			{12.8,	16.01},
			{12,	16.37},
			{11.1,	16.53},
			{10.15,	16.46},
			{9.2,	16.15},
			{8.31,	15.67},
			{7.48,	15.09},
			{6.65,	14.46},
			{5.82,	13.85},
			{4.98,	13.27},
			{3.65,	13.74},
			{2.35,	13.74}};
		
		return clonedPoints;
	}
	
}