package jarticulator;
import java.awt.*;


import javax.swing.*;


public class JAView extends JComponent
{
	static final long serialVersionUID=1L;
	protected double mOffsetX;
	protected double mOffsetY;
	protected double mScale = 1.0f;
	protected double mGhostOffsetX = 0;
	protected double mGhostOffsetY = 0;
	protected double mOriginalOffsetX = 0;
	protected double mOriginalOffsetY = 0;

	protected int mDrawControl = 0;
	protected double theta=30;
	protected double zeta=45;	

	protected double[][] mRotationMatrix;

	double BORDER = 2;	//% of width

	double mLastWidth = 800;
	double mLastHeight = 600;

	Rectangle mZoomRectangle = new Rectangle();
	boolean mDrawZoomRectangle;

	boolean mHasMouse = false;

	Point mScreenCoord = new Point();  //  @jve:decl-index=0:

	Font mBrdCoordFont = new Font("Dialog", Font.PLAIN, 12);
	String mBrdCoordString = new String();  //  @jve:decl-index=0:

	
	Font mSlidingInfoFont = new Font("Dialog", Font.PLAIN, 12);
	String mSlidingInfoString = new String();  //  @jve:decl-index=0:

	
	double mCurvatureScale = 500;
	double mVolumeDistributionScale = 0.1;
	
	Image mBackgroundImage = null;
//	AffineTransform mBackgroundImageTransform = new AffineTransform();
	double mBackgroundImageOffsetX = 0;
	double mBackgroundImageOffsetY = 0;
	double mBackgroundImageScale = 1;
	double mBackgroundImageRot = 0;

	JPopupMenu mPopupMenu = null;
	
	boolean mIsLifeSize = false;
	static double mLifeSizeScale = 1.0;
	protected double mLifeSizeOffsetX=0.0;
	protected double mLifeSizeOffsetY=0.0;
	double mPreviousScale = 1.0;
	protected double mPreviousOffsetX=0.0;
	protected double mPreviousOffsetY=0.0;
	
	public JAView()
	{
		super();

		double[][] m1 = {{Math.cos(-theta*3.1415/180.0), 0.0, -Math.sin(-theta*3.1415/180.0)},
				{0.0, 1.0, 0.0},
		                {Math.sin(-theta*3.1415/180.0), 0.0, Math.cos(-theta*3.1415/180.0)}}; 
		double[][] m2 = {{1.0, 0.0, 0.0},
				{0.0, Math.cos(-zeta*3.1415/180.0), -Math.sin(-zeta*3.1415/180.0)},
		                {0.0, Math.sin(-zeta*3.1415/180.0), Math.cos(-zeta*3.1415/180.0)}}; 

		mRotationMatrix=cross_product(m1,m2);

//		Hint at good sizes for this component.

		setPreferredSize(new Dimension(800, 600));

		setMinimumSize(new Dimension(400, 300));

//		Request a black line around this component.

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);


	}
	public void add(JPopupMenu menu)
	{
		mPopupMenu = menu;
	}
	
	public double getOffsetX()
	{
		return mOffsetX;
	}

	public void setOffsetX(double newOffsetX)
	{
		mOffsetX = newOffsetX;
	}

	public double getOffsetY()
	{
		return mOffsetY;
	}

	public void setOffsetY(double newOffsetY)
	{
		mOffsetY = newOffsetY;
	}

	public double getScale()
	{
		return mScale;
	}

	public void setScale(double scale)
	{
		mScale = scale;
	}

	public void setCurrentAsLifeSizeScale()
	{
		mLifeSizeScale = mScale;
		mLifeSizeOffsetX = mOffsetX;
		mLifeSizeOffsetY = mOffsetY;
	}
	
	public boolean isLifeSize()
	{
		return mIsLifeSize;
	}

	public void setLifeSize(boolean lifeSize)
	{
		if(mIsLifeSize == lifeSize)
			return;
		
		mIsLifeSize = lifeSize;

		if(mIsLifeSize)
		{
			mPreviousScale = mScale;
			mPreviousOffsetX = mOffsetX;
			mPreviousOffsetY = mOffsetY;

			mScale = mLifeSizeScale;
			mOffsetX = mLifeSizeOffsetX;
			mOffsetY = mLifeSizeOffsetY;
		}
		else
		{
			mLifeSizeOffsetX = mOffsetX;
			mLifeSizeOffsetY = mOffsetY;			
		}
	}
	
	public void resetToPreviousScale()
	{
		mScale = mPreviousScale;
		mOffsetX = mPreviousOffsetX;
		mOffsetY = mPreviousOffsetY;
	}

	public void setDrawZoomRectangle(Point corner1, Point corner2)
	{
		mZoomRectangle.setFrameFromDiagonal(corner1, corner2);
		mDrawZoomRectangle = true;
	}

	public void disableDrawZoomRectangle()
	{
		mDrawZoomRectangle = false;

	}
	

	public void adjustScaleAndOffset()
	{
		double currentWidth = getWidth();
		double currentHeight = getHeight();

		double widthChange = (currentWidth/mLastWidth);
		double heightChange = (currentHeight/mLastHeight);

		if(!mIsLifeSize)
		{
			mScale *= widthChange;
			mLifeSizeOffsetX *= widthChange;
			mLifeSizeOffsetY *= heightChange;
		}
		else
		{
			mPreviousScale *= widthChange;
			mPreviousOffsetX *= widthChange;
			mPreviousOffsetY *= heightChange;
		}

		mOffsetX *= widthChange;
		mOffsetY *= heightChange;

		mLastWidth = currentWidth;
		mLastHeight = currentHeight;
	}


	
	private double[][] cross_product(double[][] m1, double[][] m2)
	{
		double[][] m=new double[3][3];
		
		m[0][0]=m1[0][0]*m2[0][0]+m1[1][0]*m2[0][1]+m1[2][0]*m2[0][2];
		m[1][0]=m1[0][0]*m2[1][0]+m1[1][0]*m2[1][1]+m1[2][0]*m2[1][2];
		m[2][0]=m1[0][0]*m2[2][0]+m1[1][0]*m2[2][1]+m1[2][0]*m2[2][2];

		m[0][1]=m1[0][1]*m2[0][0]+m1[1][1]*m2[0][1]+m1[2][1]*m2[0][2];
		m[1][1]=m1[0][1]*m2[1][0]+m1[1][1]*m2[1][1]+m1[2][1]*m2[1][2];
		m[2][1]=m1[0][1]*m2[2][0]+m1[1][1]*m2[2][1]+m1[2][1]*m2[2][2];

		m[0][2]=m1[0][2]*m2[0][0]+m1[1][2]*m2[0][1]+m1[2][2]*m2[0][2];
		m[1][2]=m1[0][2]*m2[1][0]+m1[1][2]*m2[1][1]+m1[2][2]*m2[1][2];
		m[2][2]=m1[0][2]*m2[2][0]+m1[1][2]*m2[2][1]+m1[2][2]*m2[2][2];
		
		return m;
	}
	
	public void paintComponent(Graphics g) 
	{

		adjustScaleAndOffset();

		/**
		 * Copy the graphics context so we can change it.
		 * Cast it to Graphics2D so we can use antialiasing.
		 */

		Graphics2D g2d = (Graphics2D)g.create();

		// Turn on antialiasing, so painting is smooth.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Paint the background.
		Color bkgColor = Color.lightGray;

		g2d.setColor(bkgColor);
		g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		// Draw contents
		
		Stroke stroke=new BasicStroke(2.0f);
		drawPart(g2d, Color.BLUE, stroke);		

	}    

	public void drawPart(Graphics2D g, Color color, Stroke stroke) 
	{
	}

}


