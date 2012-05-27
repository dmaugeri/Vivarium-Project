import java.awt.Graphics2D;
import java.awt.Rectangle;

abstract public class Shape2D implements BoundingBox 
{
	private double xPos;
	private double yPos;
	private Rectangle boundingBox;
	public static final double DEFAULTWIDTH = 2;
	public static final double DEFAULTHEIGHT= 2;
	
	public Shape2D()
	{
		xPos = 0;
	    yPos = 0;
	    boundingBox = new Rectangle(0,0,0,0);
	}
	   
	public Shape2D(double x, double y)
	{
	   xPos = x;
	   yPos = y;
	   setBoundingBox(x, y, DEFAULTWIDTH, DEFAULTHEIGHT);
	}
	
   public Shape2D(double x, double y, double width, double height)
   {
	   xPos = x;
	   yPos = y;
	   boundingBox = new Rectangle(0,0,0,0);
	   setBoundingBox(x, y, width, height);
   }
   
   public final double getXPos()
   {
	   return xPos;
   }
   
   public final double getYPos()
   {
	   return yPos;
   }
   public void moveTo(double x, double y)
   {
      xPos = x;
      yPos = y;
      updateBoundingBox(xPos, yPos);
   }
   
   public Rectangle getBoundingBox()
   {
	   return boundingBox;
   }
   
   public double getBoundingBoxWidth()
   {
	   return boundingBox.getWidth();
   }
   
   public double getBoundingBoxHeight()
   {
	   return boundingBox.getHeight();
   }
   
   public double getBoundingBoxMaxX()
   {
	   return boundingBox.getMaxX();
   }
   
   public double getBoundingBoxMinX()
   {
	   return boundingBox.getMinX();
   }
   
   public double getBoundingBoxMaxY()
   {
	   return boundingBox.getMaxY();
   }
   
   public double getBoundingBoxMinY()
   {
	   return boundingBox.getMinY();
   }
   
   public void setBoundingBox(double xPos, double yPos, double width, double height)
   {
	   boundingBox.setRect(xPos, yPos, width, height);
   }
   
   public void updateBoundingBox(double xPos, double yPos)
   {
	  // boundingBox.setLocation((int)(xPos - getBoundingBoxWidth()/2),(int) (yPos - getBoundingBoxHeight()/2));
	   boundingBox.setRect(xPos, yPos, getBoundingBoxWidth(), getBoundingBoxHeight());
   }
   
   public boolean intersectsBoundingBox(Rectangle other)
   {
	   return boundingBox.intersects(other);
   }
   
   public void draw(Graphics2D g2d)
   {
	   g2d.draw(boundingBox);
   }
}