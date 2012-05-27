import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;
/**
 * Pac creature subclass of Creature
 * @author Daniel Maugeri Student Number: 500404890
 *
 */
public class PacCreature extends Creature 
{
	private Arc2D.Double pac;
	private Ellipse2D.Double eye;
	final int EAST = 30;
	final int WEST = 210; 
	final int NORTH = 120;
	final int SOUTH = 300;
	final int WHOLE_TURN = 360;
	final int MOUTH_OPEN = 300;
	final int ROTATIONAL_SPEED = 5;
	private double movement;
	private double extent;
	private double x;
	private double y;
	private int direction;
	private int distance;
	private int countFlappingHead;
	private int countDir;
	private Random random;
	/**
	 * Constructor for the pac Creature class
	 * @param x2 xPosition of the bounding box of the pac creature
	 * @param y2 yPosition of the bounding box of the pac creature
	 * @param shapeWidth the width of the bounding box of the pac creature
	 * @param shapeHeight the height of the bounding box of the pac creature
	 * @param xSpeed the speed of the pac creature in the x direction
	 * @param ySpeed the speed of the pac creature in the y direction
	 * @param color the color of the direction
	 * @param life whether the creature is alive or not, true if alive, false if dead
	 */
	public PacCreature(double x2, double y2, int shapeWidth, int shapeHeight,
			double xSpeed, double ySpeed, Color color, boolean life) 
	{
		super(x2, y2, shapeWidth, shapeHeight, xSpeed, ySpeed, color, life);
		extent = 300;
		countFlappingHead = 0;
		countDir = 0;
		movement = 0;
		pac = new Arc2D.Double(getXPos(), getYPos(),  getBoundingBoxWidth(), getBoundingBoxHeight(),
				direction, extent, pac.PIE);
		
		eye = new Ellipse2D.Double();
		random = new Random();
		direction = random.nextInt(7);
		
		switch(direction)
		{
		case 0: direction = EAST; break;
		case 1: direction = NORTH; break;
		case 2: direction = WEST; break;
		case 3: direction = SOUTH; break;
		case 4: direction = EAST + WHOLE_TURN; break;
		case 5: direction = NORTH + WHOLE_TURN; break;
		case 6: direction = WEST + WHOLE_TURN; break;
		case 7: direction = SOUTH + WHOLE_TURN; break;
		}
		distance = random.nextInt(500) + 25 ;
	}
	/**
	 * Sets the animation movement
	 * @param movement the amount of time the animation movement occurs
	 */
	public void setMovement(double movement)
	{
		this.movement = movement;
	}
	/**
	 * moves the creature and controls the flapping of the head and all the shape components that make up the
	 * pac creature to move.
	 */
	public void move() 
	{
		x = getXPos();
		y = getYPos();
		
		if(movement > 0)
		{
			rotateClock();
			movement--;
		}
		else
		{
			countFlappingHead++;
			if(direction == 0 || direction == 720)
			{
				direction = random.nextInt(7);
				switch(direction)
				{
				case 0: direction = EAST; break;
				case 1: direction = NORTH; break;
				case 2: direction = WEST; break;
				case 3: direction = SOUTH; break;
				case 4: direction = EAST + WHOLE_TURN; break;
				case 5: direction = NORTH + WHOLE_TURN; break;
				case 6: direction = WEST + WHOLE_TURN; break;
				case 7: direction = SOUTH + WHOLE_TURN; break;
				}
			}
			if(countDir == 0)
			{
				distance = random.nextInt(500) + 25;
			}
			if(countDir < distance)
			{
				if(direction == EAST || direction == EAST + WHOLE_TURN)
				{
					eye.setFrame(getXPos() + (2*getBoundingBoxWidth() / 3), getYPos() + getBoundingBoxHeight() / 5,
							getBoundingBoxWidth() / 9 , getBoundingBoxHeight() / 9);

					if((x + getBoundingBoxWidth()) > environmentWidth)
					{
						direction = WEST;
						x -= xSpeed;
					}
					else
					{
						x += xSpeed;
					}
				}
				else if( direction == NORTH || direction == NORTH + WHOLE_TURN)
				{
					eye.setFrame(getXPos() + (3*getBoundingBoxWidth() / 4), getYPos() + getBoundingBoxHeight() / 3,
							getBoundingBoxWidth() / 9 , getBoundingBoxHeight() / 9);

					if(y <= 0)
					{	
						direction = SOUTH;
						y += ySpeed;
					}
					else
					{
						y = y - ySpeed;
					}
				}
				else if(direction == WEST || direction == WEST + WHOLE_TURN)
				{
					eye.setFrame(getXPos() + (getBoundingBoxWidth() / 3), getYPos() + getBoundingBoxHeight() / 5,
							getBoundingBoxWidth() / 9 , getBoundingBoxHeight() / 9);

					if(x <= 0)
					{
						direction = EAST;
						x += xSpeed;
					}
					else
					{
						x = x - xSpeed;
					}
				}
				else if(direction == SOUTH || direction == SOUTH + WHOLE_TURN)
				{
					eye.setFrame(getXPos() + (3*getBoundingBoxWidth() / 4), getYPos() + 2 * getBoundingBoxHeight() / 3,
							getBoundingBoxWidth() / 9 , getBoundingBoxHeight() / 9);
					if((y + getBoundingBoxHeight()) >= environmentHeight)
					{
						direction = NORTH;
						y = y - ySpeed;
					}
					else
					{
						y += ySpeed;
					}
				}
				else
				{
					rotateCounter();
				}
				countDir++;
			}
			else if(countDir == distance)
			{
				eye.setFrame(0, 0, 0, 0);
				if(direction < WHOLE_TURN)
				{
					rotateCounter();
					if(direction == EAST || direction == NORTH || direction == WEST || direction == SOUTH)
					{
						countDir = 0;
					}

				}
				else if (direction > WHOLE_TURN + ROTATIONAL_SPEED)
				{
					rotateClock();

					if(direction == (EAST + WHOLE_TURN) || direction == (NORTH + WHOLE_TURN) || direction == (WEST + WHOLE_TURN) || direction == (SOUTH + WHOLE_TURN))
					{
						countDir = 0;
					}
				}
				else if(direction == WHOLE_TURN + ROTATIONAL_SPEED)
				{
					direction = 0;
				}
				else if (direction == WHOLE_TURN)
				{
					direction = WHOLE_TURN + 360;
				}

			}
			if(countFlappingHead == 10)
			{
				countFlappingHead++;
				extent = WHOLE_TURN;
			}
			else if(countFlappingHead == 20)
			{
				extent = MOUTH_OPEN;
				countFlappingHead = 0;
			}
		}
			pac.setArc(x, y, getBoundingBoxWidth(), getBoundingBoxHeight(), direction, extent, pac.PIE);
			moveTo(x,y);
		
	}
		/**
		 * Collision response between the pac creature and other creatures
		 */
		public void collisionResponse(Moveable2DShape other) 
		{
			//if pac creature equals to another creature of the same type then make them switch directions

			if(this.equalsTo(other))
			{
				PacCreature pac = (PacCreature) other;
				int direction = pac.getDirection();
				pac.setDirection(this.getDirection());
				this.setDirection(direction);

				double x = this.getXPos();
				double y = this.getYPos();
				double x2 = pac.getXPos();
				double y2 = pac.getYPos();
				//after swap directions the move ahead away from each other
				if(this.getDirection() == EAST || this.getDirection() == EAST + WHOLE_TURN)
				{
					x = x + xSpeed;
				}
				else if (this.getDirection() == SOUTH || this.getDirection() == SOUTH + WHOLE_TURN)
				{
					y = y + ySpeed;
				}else if (this.getDirection() == NORTH || this.getDirection() == NORTH + WHOLE_TURN)
				{
					y = y - ySpeed;
				}
				else if (this.getDirection() == WEST || this.getDirection()  == WEST + WHOLE_TURN)
				{
					x = x - xSpeed;
				}
				else
				{
					checkOvershoot(x, y);
				}

				if(pac.getDirection() == EAST || pac.getDirection() == EAST + WHOLE_TURN)
				{
					x2 = x2 + xSpeed;
				}
				else if (pac.getDirection() == SOUTH || pac.getDirection() == SOUTH + WHOLE_TURN)
				{
					y2 = y2 + ySpeed;
				}else if (pac.getDirection() == NORTH || pac.getDirection() == NORTH + WHOLE_TURN)
				{
					y2 = y2 - ySpeed;
				}
				else if (pac.getDirection() == WEST || pac.getDirection() == WEST + WHOLE_TURN)
				{
					x2 = x2 - xSpeed;
				}
				else
				{
					checkOvershoot(x,y);
				}
				pac.moveTo(x2, y2);
				this.moveTo(x, y);
			}
		}
		/**
		 * Compares to objects to see if their equal by testing the area between two creatures
		 * @param other object that is being compared to
		 */
		public boolean equalsTo(Object other)
		{
			if(!super.equalsTo(other))
				return false;
			return true;
		}
		/**
		 * Sets the direction in which the pac creature is facing (North, East, West etc..)
		 * @param direction
		 */
		public void setDirection(int direction)
		{
			this.direction = direction;
		}
		/**
		 * Returns the direction in which the pacman is facing
		 * @return returns the direction in which the pacman is facing
		 */
		public int getDirection()
		{
			return direction;
		}
		/**
		 * rotates the pac creature counter clockwise
		 */
		public void rotateCounter()
		{
			direction += ROTATIONAL_SPEED;
		}
		/**
		 * rotates the pac creature clockwise
		 */
		public void rotateClock()
		{
			direction -= ROTATIONAL_SPEED;
		}
		/**
		 * draws the pac Creature
		 * @param g2 graphics variable used to draw all the parts of the creature
		 */
		public void draw(Graphics2D g2) 
		{
			g2.setColor(color);
			g2.fill(pac);
			g2.setColor(Color.BLACK);
			g2.fill(eye);
		}

	}
