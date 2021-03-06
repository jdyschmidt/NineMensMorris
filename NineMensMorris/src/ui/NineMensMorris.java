package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

import engine.Game;
import engine.NineGame;

public class NineMensMorris {

	//Frame used
	protected static JFrame frame;
	//Game engine
	private static Game game;
	//Background, to set size
	private static JLabel bgLabel;
	
	/*
	 * Start everything up
	 */
	private static void initGUI() {
        //Create and set up the window.
        frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(100, 100, 100));
        menuBar.setPreferredSize(new Dimension(600, 20));
        
        game = new NineGame();
        frame.setContentPane(game.getDisplay());
        
        bgLabel = new JLabel();
		bgLabel.setOpaque(true);
		bgLabel.setBackground(new Color(0, 0, 0));
		bgLabel.setPreferredSize(new Dimension(600, 600));
		frame.add(bgLabel);
		frame.setPreferredSize(new Dimension(620, 660));
		
        //Add components
        frame.setJMenuBar(menuBar);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	/*
	 * I'm not sure If I'll ever need to add anything else to this
	 */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGUI();
            }
        });
    }

}
