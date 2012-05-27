import java.awt.Color;

import javax.swing.JFrame;


public class VivariumViewer 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();	
		Vivarium vivariumComponent = new Vivarium();
		frame.setTitle("Vivarium");
		frame.setBounds(0, 0, 900, 550);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(vivariumComponent);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
	}
}
