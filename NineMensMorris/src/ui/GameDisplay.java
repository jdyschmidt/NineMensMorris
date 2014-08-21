package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Game;


public abstract class GameDisplay extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private SlotButton[][] slotButtons;
	private Game game;
	
	/*
	 * Called by engine to update slot in display when appropriate.
	 * @param square Square in which slot is located
	 * @param location Location in square in which slot is located
	 * @param player Value with which to fill slot
	 */
	public void fillSlot(int square, int location, int player) {
		getSlotButton(square, location).setFilled(player);
	}

	/*
	 * Used by subclass
	 * @param square Square in which slot is located
	 * @param location Location in square in which slot is located
	 * @return Corresponding SlotButton to that place
	 */
	protected SlotButton getSlotButton(int square, int location) {
		return slotButtons[square][location];
	}

	/*
	 * Creating new set of SlotButtons. Only called on init.
	 * @param slotButtons Empty matrix of given dimensions
	 * @param buttonUnfocused Icon to be used for button normally
	 * @param buttonFocused Icon to be used for button when hovered over
	 * @param buttonPressed Icon to be used for button when mouse is depressed over
	 */
	protected void setSlotButtons(SlotButton[][] slotButtons, ImageIcon buttonUnfocused, ImageIcon buttonFocused, ImageIcon buttonPressed) {
		this.slotButtons = slotButtons;
		for (int i = 0; i != slotButtons.length; i++) {
			for (int j = 0; j != slotButtons[i].length; j++) {
				this.slotButtons[i][j] = new SlotButton(buttonUnfocused, buttonFocused, buttonPressed);
				add(this.slotButtons[i][j]);
				this.slotButtons[i][j].setActionCommand(i+";"+j);
				this.slotButtons[i][j].addActionListener(this);
			}
		}
	}
	
	/*
	 * Catches events by the buttons. Not called manually
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		int square = Integer.parseInt(e.getActionCommand().substring(0, e.getActionCommand().indexOf(";")));
		int location = Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf(";")+1, e.getActionCommand().length()));
		game.clickPosition(square, location);
	}

	/*
	 * @param game Engine with which to interact. Only set once, from subclass, from engine
	 */
	protected void setGame(Game game) {
		this.game = game;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 610, 610);
		g.setColor(Color.BLUE);
		g.drawString("Captured:", 400, 25);
		g.drawString("Captured:", 400, 595);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(game.getCaptured(1)), 470, 25);
		for (int i=0; i!=game.getPieces(1); i++)
			g.fillOval(10 + i*30, 10, 20, 20);
		g.setColor(Color.RED);
		g.drawString(String.valueOf(game.getCaptured(2)), 470, 595);
		for (int i=0; i!=game.getPieces(2); i++)
			g.fillOval(10 + i*30, 580, 20, 20);
		g.setColor(Color.YELLOW);
		g.fillOval(570, (game.getActivePlayerVal()==1?5:580), 30, 30);
	}
	
	/*
	 * Disable all buttons of a certain player (or both)
	 * Should implement binary logic sometime.
	 * @param players 1 for player one, 2 for player two, 4 for both.
	 */
	public void setDisabled(int players) {
		if (!(1==players || players==2 || players==4)) {
			try {
				throw new Exception("Player value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (players==4) {
			for (int i = 0; i != slotButtons.length; i++) {
				for (int j = 0; j != slotButtons[i].length; j++) {
					if (slotButtons[i][j].getFilled()!=0)
						slotButtons[i][j].setEnabled(false);
				}
			}
		}
		else {
			for (int i = 0; i != slotButtons.length; i++) {
				for (int j = 0; j != slotButtons[i].length; j++) {
					if (slotButtons[i][j].getFilled()==players)
						slotButtons[i][j].setEnabled(false);
				}
			}
		}
	}
}
