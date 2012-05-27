import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;
/**
 * ghostCreature class a sub class of the Creature class
 * @author Daniel Maugeri Student Number: 500404890
 */
public class GhostCreature extends Creature 
{
	private static final int LIMIT_HEIGHT = 67; 
	private static final int PACMAN_AREA = 2500;
	private Arc2D.Double headArc;
	private Rectangle body;
	private Ellipse2D.Double leftEye;
	private Ellipse2D.Double rightEye;
	private Ellipse2D.Double eyeballLeft;
	private Ellipse2D.Double eyeballRight;
	private Polygon bottomLeftTri;
	private Polygon bottomRightTri;
	private double countY;
	private double countX;
	private double height;
	private double randomMoveX;
	private Random random;
	/**
	 * Constructs a ghost creature with a polygon object, arcs, and rectangle
	 * @param xPos xPosition of the bounding box
	 * @param yPos yPosition of the bounding box
	 * @param shapeWidth the width of the bounding box and the creature
	 * @param shapeHeight the height of the bounding box and the creature
	 * @param xSpeed the speed of the creature in the x position
	 * @param ySpeed the speed of the creature in the y position
	 * @param color the color of the creature
	 * @param life the life of the creature whether it is dead or alive
	 */
	public GhostCreature(double xPos, double yPos, int shapeWidth, int shapeHeight,
			double xSpeed, double ySpeed, Color color, boolean life) 
	{
		super(xPos, yPos, shapeWidth, shapeHeight, xSpeed, ySpeed, color, life);
		headArc = new Arc2D.Double(getXPos(), getYPos(), getBoundingBoxWidth(), getBoundingBoxHeight(),
				0, 180, headArc.PIE);
		
		body = new Rectangle();
		random = new Random();
	
		height = random.nextInt(800) + 100;
		randomMoveX = random.nextInt(500) + 100;
		
		countY = 0;
		countX = 0;
		
		leftEye = new Ellipse2D.Double();
		rightEye = new Ellipse2D.Double();
		eyeballLeft = new Ellipse2D.Double();
		eyeballRight = new Ellipse2D.Double();
		bottomLeftTri = new Polygon();
		bottomRightTri = new Polygon();
		
		bottomLeftTri.addPoint((int)getXPos(), (int)getYPos() + (int)getBoundingBoxHeight());
		bottomLeftTri.addPoint((int)getXPos() + (int)(getBoundingBoxWidth() / 4), (int)getYPos() + 3*(int)getBoundingBoxHeight() / 4);
		bottomLeftTri.addPoint((int)getXPos() + (int)(getBoundingBoxWidth()/2), (int)getYPos() + (int)getBoundingBoxHeight());
		
		bottomRightTri.addPoint((int)getXPos() + (int)(getBoundingBoxWidth()/2), (int)getYPos() + (int)getBoundingBoxHeight());
		bottomRightTri.addPoint((int)getXPos() + (int)(3 * getBoundingBoxWidth() / 4), (int)getYPos() + 3*(int)getBoundingBoxHeight()/4);
		bottomRightTri.addPoint((int)getXPos() + (int)(getBoundingBoxWidth()),(int)getYPos() + (int)getBoundingBoxHeight());
	}
	/**
	 * Method to move the creature and updates the position relative to the bounding box
	 */
	public void move()
	{
		double x = getXPos();
		double y = getYPos();
		
		countY++;
		countX++;
		
		if(countY <= height/2)
		{
			y = y + ySpeed * Math.sin(Math.PI/2);
			countY++;
		}
		else if(countY < height)
		{
			y = y + ySpeed * Math.sin(3*Math.PI/2);
			countY++;
		}
		else
		{
			height = random.nextInt(800) + 100;
			countY = 0;
		}
		if(countX <= randomMoveX/2)
		{
			x = x + xSpeed * Math.sin(Math.PI/2);
			countX++;
			
		}
		else if (countX < randomMoveX)
		{
			x = x + xSpeed * Math.sin(3*Math.PI/2);
			countX++;
		}
		else
		{
			randomMoveX = random.nextInt(500) + 100;
			countX = 0;
		}
		
		//----------------------------------------------------------------------------
		bottomRightTri.reset();
		bottomLeftTri.reset();
		checkOvershoot(x, y);
		
		//Updates all the shapes relating to the creature
		
		
		bottomLeftTri.addPoint((int)x, (int)y + (int)getBoundingBoxHeight());
		bottomLeftTri.addPoint((int)x + (int)(getBoundingBoxWidth() / 4), (int)y + 3*(int)getBoundingBoxHeight() / 4);
		bottomLeftTri.addPoint((int)x + (int)(getBoundingBoxWidth()/2), (int)y + (int)getBoundingBoxHeight());
		
		bottomRightTri.addPoint((int)x + (int)(getBoundingBoxWidth()/2), (int)y + (int)getBoundingBoxHeight());
		bottomRightTri.addPoint((int)x + (int)(3 * getBoundingBoxWidth() / 4), (int)y + 3*(int)getBoundingBoxHeight()/4);
		bottomRightTri.addPoint((int)x + (int)(getBoundingBoxWidth()), (int)y + (int)getBoundingBoxHeight());
		bottomLeftTri.translate((int)xSpeed, (int)ySpeed);
		
		headArc = new Arc2D.Double(x, y, getBoundingBoxWidth(), getBoundingBoxHeight(),
				0, 180, headArc.PIE);
		body.setFrame(x, y + getBoundingBoxHeight() / 2,
				getBoundingBoxWidth(), getBoundingBoxHeight() / 2 );
		leftEye.setFrame(x + getBoundingBoxWidth() / 6, y + getBoundingBoxHeight() / 4,
				getBoundingBoxWidth() / 4, getBoundingBoxHeight() / 4);
		rightEye.setFrame(x + 3*getBoundingBoxWidth() / 5, y + getBoundingBoxHeight() / 4, 
				getBoundingBoxWidth() / 4, getBoundingBoxHeight()/4);
		
		eyeballLeft.setFrame(x + getBoundingBoxWidth() / 4, y + getBoundingBoxHeight() / 4,
				getBoundingBoxWidth() / 8, getBoundingBoxHeight() / 8);
		
		eyeballRight.setFrame((x + 2*getBoundingBoxWidth() / 3) /*- 1*/, (y + getBoundingBoxHeight() / 3) /*+ 3*/, 
				getBoundingBoxWidth() / 8, getBoundingBoxHeight()/8);
		moveTo(x,y);
		
	}
	/**
	 * Collision response for when this object collides with another object
	 * @param other the object in which this class collides with
	 */
	public void collisionResponse(Moveable2DShape other)
	{	//response if a ghostCreature hits another ghostCreature
		if(this.equalsTo(other))
		{
			double x = getXPos();
			double y = getYPos();
			x -= xSpeed;
			y -= ySpeed;
			moveTo(x,y);
			
		}
		
		//Uses the pacCreature area to compare whether a ghostCreature hits a pacCreature or not 
		if(!this.equalsTo(other) && (other.getBoundingBoxHeight() * other.getBoundingBoxWidth()) == PACMAN_AREA)
		{
			PacCreature pac = (PacCreature) other;
			
			pac.setLife(false);
			Vivarium.checkNewCreature = true;
			Vivarium.removeCreature(pac);
			
			if(this.getBoundingBoxHeight() < LIMIT_HEIGHT)
			{
				this.setBoundingBox(this.getXPos(), this.getYPos(), this.getBoundingBoxWidth() + (this.getBoundingBoxWidth()/6),
						this.getBoundingBoxHeight() + this.getBoundingBoxHeight()/6);
			
				bottomRightTri.reset();
				bottomLeftTri.reset();
			
				bottomLeftTri.addPoint((int)this.getXPos(), (int)this.getYPos() + (int)getBoundingBoxHeight());
				bottomLeftTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth() / 4), (int)this.getYPos() + 3*(int)getBoundingBoxHeight() / 4);
				bottomLeftTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()/2), (int)this.getYPos() + (int)getBoundingBoxHeight());
			
				bottomRightTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()/2), (int)this.getYPos() + (int)getBoundingBoxHeight());
				bottomRightTri.addPoint((int)this.getXPos() + (int)(3 * getBoundingBoxWidth() / 4), (int)this.getYPos() + 3*(int)getBoundingBoxHeight()/4);
				bottomRightTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()), (int)this.getYPos() + (int)getBoundingBoxHeight());
			
				Vivarium.checkNewCreature = true;   //sets the vivarium boolean true to break the main loop to be able to add a new creature
				Vivarium.addNewDot(this.getXPos(), this.getYPos());
			}
			else
			{
				this.setBoundingBox(this.getXPos(), this.getYPos(), 40, 50);
		
				bottomRightTri.reset();
				bottomLeftTri.reset();
		
				bottomLeftTri.addPoint((int)this.getXPos(), (int)this.getYPos() + (int)getBoundingBoxHeight());
				bottomLeftTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth() / 4), (int)this.getYPos() + 3*(int)getBoundingBoxHeight() / 4);
				bottomLeftTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()/2), (int)this.getYPos() + (int)getBoundingBoxHeight());
		
				bottomRightTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()/2), (int)this.getYPos() + (int)getBoundingBoxHeight());
				bottomRightTri.addPoint((int)this.getXPos() + (int)(3 * getBoundingBoxWidth() / 4), (int)this.getYPos() + 3*(int)getBoundingBoxHeight()/4);
				bottomRightTri.addPoint((int)this.getXPos() + (int)(getBoundingBoxWidth()), (int)this.getYPos() + (int)getBoundingBoxHeight());
				
				Vivarium.checkNewCreature = true;   //sets the vivarium boolean true to break the main loop to be able to add a new creature
				Vivarium.addNewDot(this.getXPos() + getBoundingBoxWidth()/2, this.getYPos() + getBoundingBoxHeight()/2);
				Vivarium.addNewGhost(this.getXPos(), this.getYPos());
			}		
		}
		
	}
	/**
	 * Checks if the ghostCreature equals to another of the same type
	 * @param other the other object being compared too
	 * @return whether the two objects are of the same class or not
	 */
	public boolean equalsTo(Object other)
	{
		if(!super.equalsTo(other))
			return false;
		return true;
		
	}
	/**
	 * draws the ghostCreature and all his parts
	 */
	public void draw(Graphics2D g2)
	{
		g2.setColor(color);
		g2.fill(headArc);
		g2.fill(body);
		g2.setColor(color.WHITE);
		g2.fill(leftEye);
		g2.fill(rightEye);
		g2.setColor(color.BLACK);
		g2.fill(eyeballLeft);
		g2.fill(eyeballRight);
		g2.setColor(Color.BLACK);
		g2.fill(bottomLeftTri);
		g2.fill(bottomRightTri);		
	}
}
