package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import javax.swing.ImageIcon;

import engine.NineGame;


public class NineGameDisplay extends GameDisplay {

	private static final long serialVersionUID = 1L;
	
	private Rectangle bigSquare, medSquare, lilSquare;
	private final NineGame game;
	
	public NineGameDisplay(NineGame game) {
		this.game = game;
		setLayout(null);
		this.setBounds(0,0,600,600);
		bigSquare = new Rectangle(65, 65, 480, 480);
		medSquare = new Rectangle(bigSquare);
		medSquare.grow(-80, -80);
		lilSquare = new Rectangle(medSquare);
		lilSquare.grow(-80, -80);
		
		ImageIcon buttonUnfocused = new ImageIcon("images/ButtonUnfocused.png");
		ImageIcon buttonFocused = new ImageIcon("images/ButtonFocused.png");
		ImageIcon buttonPressed = new ImageIcon("images/ButtonPressed.png");
		
		setPositionButtons(new SlotButton[3][8], buttonUnfocused, buttonFocused, buttonPressed);
		setGame(this.game);
		
		//Set all buttons manually aaah
		getPositionButton(0, 0).setBounds(bigSquare.getMinX()-20, bigSquare.getMinY()-20, 40, 40);
		getPositionButton(0, 1).setBounds(bigSquare.getCenterX()-20, bigSquare.getMinY()-20, 40, 40);
		getPositionButton(0, 2).setBounds(bigSquare.getMaxX()-20, bigSquare.getMinY()-20, 40, 40);
		getPositionButton(0, 3).setBounds(bigSquare.getMaxX()-20, bigSquare.getCenterY()-20, 40, 40);
		getPositionButton(0, 4).setBounds(bigSquare.getMaxX()-20, bigSquare.getMaxY()-20, 40, 40);
		getPositionButton(0, 5).setBounds(bigSquare.getCenterX()-20, bigSquare.getMaxY()-20, 40, 40);
		getPositionButton(0, 6).setBounds(bigSquare.getMinX()-20, bigSquare.getMaxY()-20, 40, 40);
		getPositionButton(0, 7).setBounds(bigSquare.getMinX()-20, bigSquare.getCenterY()-20, 40, 40);
		getPositionButton(1, 0).setBounds(medSquare.getMinX()-20, medSquare.getMinY()-20, 40, 40);
		getPositionButton(1, 1).setBounds(medSquare.getCenterX()-20, medSquare.getMinY()-20, 40, 40);
		getPositionButton(1, 2).setBounds(medSquare.getMaxX()-20, medSquare.getMinY()-20, 40, 40);
		getPositionButton(1, 3).setBounds(medSquare.getMaxX()-20, medSquare.getCenterY()-20, 40, 40);
		getPositionButton(1, 4).setBounds(medSquare.getMaxX()-20, medSquare.getMaxY()-20, 40, 40);
		getPositionButton(1, 5).setBounds(medSquare.getCenterX()-20, medSquare.getMaxY()-20, 40, 40);
		getPositionButton(1, 6).setBounds(medSquare.getMinX()-20, medSquare.getMaxY()-20, 40, 40);
		getPositionButton(1, 7).setBounds(medSquare.getMinX()-20, medSquare.getCenterY()-20, 40, 40);
		getPositionButton(2, 0).setBounds(lilSquare.getMinX()-20, lilSquare.getMinY()-20, 40, 40);
		getPositionButton(2, 1).setBounds(lilSquare.getCenterX()-20, lilSquare.getMinY()-20, 40, 40);
		getPositionButton(2, 2).setBounds(lilSquare.getMaxX()-20, lilSquare.getMinY()-20, 40, 40);
		getPositionButton(2, 3).setBounds(lilSquare.getMaxX()-20, lilSquare.getCenterY()-20, 40, 40);
		getPositionButton(2, 4).setBounds(lilSquare.getMaxX()-20, lilSquare.getMaxY()-20, 40, 40);
		getPositionButton(2, 5).setBounds(lilSquare.getCenterX()-20, lilSquare.getMaxY()-20, 40, 40);
		getPositionButton(2, 6).setBounds(lilSquare.getMinX()-20, lilSquare.getMaxY()-20, 40, 40);
		getPositionButton(2, 7).setBounds(lilSquare.getMinX()-20, lilSquare.getCenterY()-20, 40, 40);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 610, 610);
		g2.setColor(Color.BLUE);
		g2.draw(bigSquare);
		g2.draw(medSquare);
		g2.draw(lilSquare);
		Line2D line = new Line2D.Double(lilSquare.getCenterX(), lilSquare.getMaxY(), bigSquare.getCenterX(), bigSquare.getMaxY());
		g2.draw(line);
		line.setLine(lilSquare.getCenterX(), lilSquare.getMinY(), bigSquare.getCenterX(), bigSquare.getMinY());
		g2.draw(line);
		line.setLine(lilSquare.getMinX(), lilSquare.getCenterY(), bigSquare.getMinX(), bigSquare.getCenterY());
		g2.draw(line);
		line.setLine(lilSquare.getMaxX(), lilSquare.getCenterY(), bigSquare.getMaxX(), bigSquare.getCenterY());
		g2.draw(line);
	}

	public void fillSlot(int square, int location, int player) {
		getPositionButton(square, location).setFilled(player);
	}
}
