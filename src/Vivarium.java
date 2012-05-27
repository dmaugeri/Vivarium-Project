import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JComponent;

/**
 * Vivarium class subclass of JComponent
 * @author Daniel Maugeri Student Number: 500404890
 */
public class Vivarium extends JComponent
{	
	private static ArrayList<Creature> creatures;
	private static Random random;
	public static boolean checkNewCreature;
	private static final int GHOST_HEIGHT = 50;
	private static final int PAC_HEIGHT = 50;
	private static final int GHOST_WIDTH = 40;
	private static final int PAC_WIDTH = 50;
	private static final int GHOST_AND_PAC_SPEED = 1;
	private static Timer time;
	/**
	 * Constructor for the Vivarium, when the vivarium is created it populates the vivarium, and checks for 
	 * collision responses
	 */
	public Vivarium()
	{
		creatures = new ArrayList<Creature>();
		populateVivarium();
		
		class StartActionListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				checkNewCreature = false;
				for(Creature creature : creatures)
				{
					if( creature.isAlive())
					{	
						repaint();
						creature.move();
						for(Creature creature1 : creatures)
						{
							if(creature1.isAlive()  && creature1.collisionDetect(creature))
							{
								creature1.collisionResponse(creature);
								//If new creature is added due to the Collision it breaks the loop
								//to add the creature to the array list
								if(checkNewCreature) 
								{                    
									break;
								}
							}
						}
						if(checkNewCreature)
						{
							break;
						}
					}	
				}
			}	
		}
		ActionListener listener = new StartActionListener();
		time = new Timer(10, listener);
		time.start();		
	}
	/**
	 * Populates the vivarium with creatures	
	 */
	public void populateVivarium()
	{
		random = new Random();
		Color color;
		for(int i = 0; i < 1; i++)
		{
			color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			int yPosition2 = random.nextInt(300);
			int xPosition2 = random.nextInt(300) + 300;
			Creature ghost = new GhostCreature(xPosition2, yPosition2, GHOST_WIDTH, GHOST_HEIGHT, GHOST_AND_PAC_SPEED, GHOST_AND_PAC_SPEED
					, color, true);
			ghost.moveTo(xPosition2, yPosition2);
			creatures.add(ghost);
		}
		for(int i = 0; i < 4; i++)
		{
			color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			int yPosition3 = random.nextInt(300);
			int xPosition3 = random.nextInt(300) + 400;
			Creature dot = new DotCreature(xPosition3, yPosition3, 20, 20, 1, 1, color, true);
			dot.moveTo(xPosition3, yPosition3);
			creatures.add(dot);
		}
		for(int i=0; i < 7; i++)
		{
			color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			int yPosition = random.nextInt(300);
			int xPosition = random.nextInt(300);
			int tempY = yPosition;
			int tempX = xPosition;
			if(tempY == yPosition)
				yPosition = random.nextInt(300);
			if(tempX == xPosition)
				xPosition = random.nextInt(300);

			Creature creature = new PacCreature(xPosition,yPosition, PAC_WIDTH, PAC_HEIGHT, GHOST_AND_PAC_SPEED
					, GHOST_AND_PAC_SPEED, color, true);
			creature.moveTo(xPosition, yPosition);
			creatures.add(creature);
		}
	}
	/**
	 * Paint Component calls all the draw methods of the different creatures if the creature is alive
	 * @param g variable to pass graphics to the draw methods of all the creature objects
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for(Creature creature : creatures)
		{
			if(creature.isAlive())
			{
				creature.draw(g2);
			}
		}
	}
	public static void addNewDot(double x, double y)
	{
		if(checkNewCreature)
		{
			time.stop();
			Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			Creature dot = new DotCreature(x, y, 20, 20, GHOST_AND_PAC_SPEED
					, GHOST_AND_PAC_SPEED, color, true);
			dot.moveTo(x, y);
			creatures.add(dot);
			time.start();
		}
	}
	/**
	 * Adds a new pacCreature depending on if the checkNewCreature variable is true so it makes sure that the main loop
	 * has ended before adding a new pacCreature to the vivarium
	 * @param x the x position of the new pacCreature
	 * @param y the y position of the new pacCreature
	 */
	public static void addNewPac(double x, double y)
	{
		if(checkNewCreature)
		{
			time.stop();
			Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			Creature pac = new PacCreature(x, y, PAC_WIDTH, PAC_HEIGHT, GHOST_AND_PAC_SPEED
					, GHOST_AND_PAC_SPEED, color, true);
			PacCreature pac2  = (PacCreature) pac;
			pac2.moveTo(x, y);
			creatures.add(pac);
			pac2.setMovement(75);
			time.start();
		}
	}
	/**
	 * Adds a ghost creature to the array list only if the main loop has been broken
	 * @param x the x position of the ghost
	 * @param y the y position of the ghost
	 */
	public static void addNewGhost(double x, double y)
	{
		if(checkNewCreature)
		{
			time.stop();
			Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			Creature ghost = new GhostCreature(x, y, GHOST_WIDTH, GHOST_HEIGHT, GHOST_AND_PAC_SPEED
					, GHOST_AND_PAC_SPEED, color, true);
			ghost.moveTo(x, y);
			creatures.add(ghost);
			time.start();
		}
	}
	/**
	 * A general removing creature method to remove a creature out of the array list only if the checkNewCreature
	 * variable is true
	 * @param other the creature to be removed
	 */
	public static void removeCreature(Creature other)
	{
		if(checkNewCreature)
		{
			time.stop();
			creatures.remove(other);
			time.start();
		}
	}
}
