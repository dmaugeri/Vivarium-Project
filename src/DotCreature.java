import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;

/**
 * Dot Creature a subclass of creature
 * @author Daniel Maugeri Student Number: 500404890
 *
 */
public class DotCreature extends Creature
{
	private static final int PAC_AREA = 2500;
	private double count;
	private double height;
	private Random random;
	private Ellipse2D.Double ellipse1;
	private Ellipse2D.Double ellipse2;
	/**
	 * Constructor of a dot creature class
	 * @param xPos x position of the creature and bounding box
	 * @param yPos y position of the creature and bounding box
	 * @param shapeWidth the width of the bounding box and the creature
	 * @param shapeHeight the height of the bounding box and the creature
	 * @param xSpeed the speed of the creature in the x position
	 * @param ySpeed the speed of the creature in the y position
	 * @param color the color of the creature
	 * @param life whether the creature is alive or not
	 */
	public DotCreature(double xPos, double yPos, int shapeWidth, int shapeHeight,
			double xSpeed, double ySpeed, Color color, boolean life)
	{
		super(xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed, color, life);
		count = 0;
		random = new Random();
		height = random.nextInt(1200) + 100; 
		ellipse1 = new Ellipse2D.Double(getXPos(), getYPos(), getBoundingBoxWidth(), getBoundingBoxHeight());
		ellipse2 = new Ellipse2D.Double(getXPos(), getYPos(), getBoundingBoxWidth()/2, getBoundingBoxHeight()/2);
	}
	/**
	 * method to move the creature, overwriting the super class's move method
	 */
	public void move()
	{
		double x = getXPos();
		double y = getYPos();
		count++;
		if(count <= height/2)
		{
			x = x + xSpeed * Math.sin(Math.PI/2);
			count++;
		}
		else if(count < height)
		{
			x = x + xSpeed * Math.sin(3*Math.PI/2);
			count++;
		}
		else
		{
			height = random.nextInt(1200) + 100;
			count = 0;
		}
		y += ySpeed;
		checkOvershoot(x,y);
		ellipse1.setFrame(x, y, getBoundingBoxWidth(), getBoundingBoxHeight());
		ellipse2.setFrame(x + (getBoundingBoxWidth()/4), y + (getBoundingBoxHeight()/4), getBoundingBoxWidth() / 2, getBoundingBoxHeight()/2);
		moveTo(x,y);
	}
	/**
	 * The collision response between this object and the other moveable2dshape object
	 * @param other the other object in which it will have a response too
	 */
	public void collisionResponse(Moveable2DShape other)
	{
		if(!this.equalsTo(other) && (other.getBoundingBoxHeight() * other.getBoundingBoxWidth() == PAC_AREA))
		{
			PacCreature pac = (PacCreature) other;
			pac.setMovement(75);
			other.setBoundingBox(getXPos(), getYPos(), 50, 50);	
			Vivarium.checkNewCreature = true; //breaks main loop to add a new pacCreature
			Vivarium.addNewPac(other.getXPos() + other.getBoundingBoxWidth(), other.getYPos() + other.getBoundingBoxHeight());
			this.setLife(false);
			Vivarium.removeCreature(this);
		}
		else
		{
			double x = this.getXPos();
			double y = this.getYPos();
			x += this.xSpeed*5;
			y += this.ySpeed*5;
			moveTo(x,y);
		}
	}
	/**
	 * checking whether this class and the other class are the same class by calling the super class's equal method
	 * @return true whether or not the two classes are equal
	 */
	public boolean equals(Object other)
	{
		if(!super.equals(other))
		{
			return false;
		}
		return true;
	}
	/**
	 * draw method to draw all shapes of the Dot Creature.
	 */
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.YELLOW);
		g2.fill(ellipse1);
		g2.setColor(color);
		g2.fill(ellipse2);
	}
}
