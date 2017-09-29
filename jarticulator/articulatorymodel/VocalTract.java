package jarticulator.articulatorymodel;

import jarticulator.tubemodel.*;

/**
 * Models the vocal tract and controls the length and area of the tube model  
 */
public class VocalTract
{
	public Tube tube;
	public Tongue tongue;
	public Palate palate;
	public JawMovement jawMovement;
	public Tongue1Movement tongue1Movement;
	public Tongue2Movement tongue2Movement;
	public Tongue3Movement tongue3Movement;
	public Lip1Movement lip1Movement;
	public Lip2Movement lip2Movement;

	/**
	* Constructor that creates an articulatory model controlling an 
	* underlying tube model
	*
	* @param tubemodel	The tube model to control
	*/
   	public VocalTract(Tube tubemodel)
	{
		tube=tubemodel;
		
		tongue=new Tongue();
		palate=new Palate();
		jawMovement=new JawMovement();
		tongue1Movement=new Tongue1Movement();
		tongue2Movement=new Tongue2Movement();
		tongue3Movement=new Tongue3Movement();
		lip1Movement=new Lip1Movement();
		lip2Movement=new Lip2Movement();
		
	}

	/**
	* Sets the position of the jaw
	* @param pos	Position of the jaw
	*/
   	public void setJawPosition(double pos)
   	{
   		jawMovement.position=pos;
   	}

	/**
	* Sets the position of the first tongue parameter
	* @param pos	Position 
	*/
   	public void setTongue1Position(double pos)
   	{
   		tongue1Movement.position=pos;
   	}   	

	/**
	* Sets the position of the second tongue parameter
	* @param pos	Position 
	*/
   	public void setTongue2Position(double pos)
   	{
   		tongue2Movement.position=pos;
   	}   	

	/**
	* Sets the position of the third tongue parameter
	* @param pos	Position 
	*/
   	public void setTongue3Position(double pos)
   	{
   		tongue3Movement.position=pos;
   	}   	

	/**
	* Sets the position of the first lip parameter
	* @param pos	Position 
	*/
   	public void setLip1Position(double pos)
   	{
   		lip1Movement.position=pos;
   	}   	

	/**
	* Sets the position of the second lip parameter
	* @param pos	Position 
	*/
   	public void setLip2Position(double pos)
   	{
   		lip2Movement.position=pos;
   	}   	
	
	/**
	* Returns the points of the tongue model for visualization
	* @return	Points on the tongue
	*/
   	public double[][] getTonguePoints()
	{
		double tpoints[][]=tongue.getClonedPoints();
		
		for(int i=0; i<tpoints.length; i++)
		{
			tpoints[i][0]=tpoints[i][0]+jawMovement.tongueTransform[i][0]*jawMovement.position+
						tongue1Movement.tongueTransform[i][0]*tongue1Movement.position+
						tongue2Movement.tongueTransform[i][0]*tongue2Movement.position+
						tongue3Movement.tongueTransform[i][0]*tongue3Movement.position+
						lip1Movement.tongueTransform[i][0]*lip1Movement.position+
						lip2Movement.tongueTransform[i][0]*lip2Movement.position;
						
			tpoints[i][1]=tpoints[i][1]+jawMovement.tongueTransform[i][1]*jawMovement.position+
						tongue1Movement.tongueTransform[i][1]*tongue1Movement.position+
						tongue2Movement.tongueTransform[i][1]*tongue2Movement.position+
						tongue3Movement.tongueTransform[i][1]*tongue3Movement.position+
						lip1Movement.tongueTransform[i][1]*lip1Movement.position+
						lip2Movement.tongueTransform[i][1]*lip2Movement.position;			
		}
		
		return tpoints;
	}
	
	/**
	* Returns the points of the palate model for visualization
	* @return	Points on the palate
	*/
	public double[][] getPalatePoints()
	{
		double ppoints[][]=palate.getClonedPoints();

		for(int i=0; i<ppoints.length; i++)
		{
			ppoints[i][0]=ppoints[i][0]+jawMovement.palateTransform[i][0]*jawMovement.position+
						tongue1Movement.palateTransform[i][0]*tongue1Movement.position+
						tongue2Movement.palateTransform[i][0]*tongue2Movement.position+
						tongue3Movement.palateTransform[i][0]*tongue3Movement.position+
						lip1Movement.palateTransform[i][0]*lip1Movement.position+
						lip2Movement.palateTransform[i][0]*lip2Movement.position;
			
			ppoints[i][1]=ppoints[i][1]+jawMovement.palateTransform[i][1]*jawMovement.position+
						tongue1Movement.palateTransform[i][1]*tongue1Movement.position+
						tongue2Movement.palateTransform[i][1]*tongue2Movement.position+
						tongue3Movement.palateTransform[i][1]*tongue3Movement.position+
						lip1Movement.palateTransform[i][1]*lip1Movement.position+
						lip2Movement.palateTransform[i][1]*lip2Movement.position;	
						
		}
		
		return ppoints;
	}
	
	/**
	* Calculate the vocal tract area at each segment of the underlying
	* tube model
	*
	* @return	Area of each segment
	*/
	public double[] getArea()
	{
		double tpoints[][]=getTonguePoints();
		double ppoints[][]=getPalatePoints();
		
		double area[]=new double[ppoints.length];
		
		double xorigo=0;
		double yorigo=0;
		double dist1=0.0;
		double dist2=0.0;
		
		for(int i=0; i<ppoints.length; i++)
		{
			// check for collision
			dist1=Math.sqrt( (tpoints[i][0]-xorigo)*(tpoints[i][0]-yorigo) + 
					(tpoints[i][1]-xorigo)*(tpoints[i][1]-yorigo));
			dist2=Math.sqrt( (ppoints[i][0]-xorigo)*(ppoints[i][0]-yorigo) + 
					(ppoints[i][1]-xorigo)*(ppoints[i][1]-yorigo));
			
			if(dist1>dist2)
			{
				area[i]=0.0;
			}
			else
			{		
				area[i]=Math.sqrt( (tpoints[i][0]-ppoints[i][0])*(tpoints[i][0]-ppoints[i][0]) + 
					(tpoints[i][1]-ppoints[i][1])*(tpoints[i][1]-ppoints[i][1]));
			}
		}
		
		return area;
		
	}
	
} 	  
