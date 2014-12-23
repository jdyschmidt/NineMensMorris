package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Game;
import engine.Slot;


public abstract class GameDisplay extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private SlotButton[][] slotButtons;
	private Game game;
	private Point selectedSlot;
	
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
	protected void setSlotButtons(SlotButton[][] slotButtons, ImageIcon buttonUnfocused, ImageIcon buttonFocused, ImageIcon buttonPressed, ImageIcon buttonSelected) {
		this.slotButtons = slotButtons;
		for (int i = 0; i != slotButtons.length; i++) {
			for (int j = 0; j != slotButtons[i].length; j++) {
				this.slotButtons[i][j] = new SlotButton(buttonUnfocused, buttonFocused, buttonPressed, buttonSelected);
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
		g.drawString("Captured:", 410, 25);
		g.drawString("Captured:", 410, 595);
		g.drawString("Turn:", 532, 27);
		g.drawString("Turn:", 532, 593);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(game.getCaptured(1)), 470, 25);
		for (int i=0; i!=game.getPieces(1); i++)
			g.fillOval(10 + i*30, 10, 20, 20);
		g.setColor(Color.RED);
		g.drawString(String.valueOf(game.getCaptured(2)), 470, 595);
		for (int i=0; i!=game.getPieces(2); i++)
			g.fillOval(10 + i*30, 580, 20, 20);
		g.setColor(Color.YELLOW);
		g.fillOval(570, (game.getActivePlayer().getVal()==1?8:573), 30, 30);
	}
	
	/*
	 * Disable all buttons of a certain player (or both)
	 * Hardcoded to never disable selected slot
	 * @param players 1 for player one, 2 for player two, 4 for empty slots. Add them for combinations
	 */
	public void setDisabled(int players) {
		if (!(players>0 && players<8)) {
			try {
				throw new Exception("Player value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i != slotButtons.length; i++) {
			for (int j = 0; j != slotButtons[i].length; j++)
				slotButtons[i][j].setEnabled(true);
		}
		
		if (players>=4) {
			for (int i = 0; i != slotButtons.length; i++) {
				for (int j = 0; j != slotButtons[i].length; j++) {
					if (slotButtons[i][j].getFilled()==0)
						slotButtons[i][j].setEnabled(false);
				}
			}
			players -= 4;
		}
		if (players>=2) {
			for (int i = 0; i != slotButtons.length; i++) {
				for (int j = 0; j != slotButtons[i].length; j++) {
					if (slotButtons[i][j].getFilled()==2)
						slotButtons[i][j].setEnabled(false);
				}
			}
			players -= 2;
		}
		if (players>=1) {
			for (int i = 0; i != slotButtons.length; i++) {
				for (int j = 0; j != slotButtons[i].length; j++) {
					if (slotButtons[i][j].getFilled()==1)
						slotButtons[i][j].setEnabled(false);
				}
			}
		}
		enableSelectedSlot();
	}
	
	public void clearSelectedSlots() {
		for (int i = 0; i != slotButtons.length; i++) {
			for (int j = 0; j != slotButtons[i].length; j++) {
				if (slotButtons[i][j].isSelected())
					slotButtons[i][j].setSelected(false);
			}
		}
		selectedSlot = null;
	}
	
	public void enableSelectedSlot() {
		if (selectedSlot != null)
			slotButtons[selectedSlot.x][selectedSlot.y].setEnabled(true);
	}
	
	public void selectSlot(int square, int location) {
		slotButtons[square][location].setSelected(true);
		selectedSlot = new Point(square, location);
	}
}
