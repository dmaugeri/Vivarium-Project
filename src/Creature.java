import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
/**
 * Creature class that is a subclass of Moveable2DShape
 * @author Daniel Maugeri Student Number: 500404890
 *
 */
public class Creature extends Moveable2DShape 
{
	Ellipse2D.Double head;
	static final int BWIDTH = 900;
	static final int BHEIGHT = 525;
	Color color;
	double size;
	boolean life;
	/**
	 * A general creature class and superclass of the creatures
	 * @param xPos x-position of the bounding box and creature
	 * @param yPos y-position of the bounding box and the creature
	 * @param shapeWidth the width of the creature and bounding box
	 * @param shapeHeight the height of the creature and the bounding box
	 * @param xSpeed the speed in the x direction of the creature
	 * @param ySpeed the speed in the y direction of the creature
	 * @param color the color of the creature
	 * @param life whether the creature is alive or dead
	 */
	public Creature(double xPos, double yPos, int shapeWidth, int shapeHeight,double xSpeed, double ySpeed, Color color, boolean life)
	{
		super(xPos, yPos, shapeWidth, shapeHeight, BWIDTH, BHEIGHT, xSpeed, ySpeed);
		head = new Ellipse2D.Double();
		this.color = color;
		this.life = life;
		
	}
	/**
	 * Get method to return the creature's color
	 * @return the creature's color
	 */
	public Color getColor()
	{
		return color;
	}
	/**
	 * Get method to see if the creature is alive or not
	 * @return whether the creature is alive or not 
	 */
	public boolean isAlive()
	{
		return life;
	}
	/**
	 * Set method to set the color of the creature
	 * @param color the color of the creature
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	/**
	 * Set method to set whether the creature is alive or dead
	 * @param life boolean value of whether it is alive or not
	 */
	public void setLife(boolean life)
	{
		this.life = life;
		
	}
	/**
	 * Default move method for creatures
	 */
	public void move()
	{
		double x = getXPos();
		double y = getYPos();
		x += xSpeed;
		y += ySpeed;
		checkOvershoot(x, y);
		head.setFrame(getXPos(), getYPos(), getBoundingBoxWidth(), getBoundingBoxHeight());
	}
	/**
	 * Detect's Collision between to creature objects
	 * @param other the other shape in which it is detecting the collision with this class
	 * @return whether the creature has collided with another creature or not
	 */
	public boolean collisionDetect(Moveable2DShape other) {
		//Makes sure that it does not detect a collision with itself
		if(this.equals(other))
		{
			return false;
		}
		//return's true if the bounding box intersects another bounding box
		 if(this.intersectsBoundingBox(other.getBoundingBox()))
		{
			return true;
		}
		
		return false;
	}
	/**
	 * Default collision response between two creatures
	 * @param other the object that it responds to after they intersects
	 */
	public void collisionResponse(Moveable2DShape other) 
	{
		double x = getXPos();
		double y = getYPos();
		x += 2*xSpeed;
		y += 2*ySpeed;
		moveTo(x,y);
	}
	/**
	 * Checks if the creature class is the same class as another creature class
	 * @param other the object being compared to this class
	 * @return whether the two objects are of the same class
	 */
	public boolean equalsTo(Object other)
	{
		if(other == null)
		{
			return false;
		}
		if(this.getClass() != other.getClass())
		{
			return false;
		}
		return true;
	}
	/**
	 * Default creature draw method to draw the creature
	 * @param g2 graphics parameter used to draw or fill the shapes
	 */
	public void draw(Graphics2D g2)
	{
		super.draw(g2);
		g2.setColor(color);
		g2.fill(head);
	}
}
