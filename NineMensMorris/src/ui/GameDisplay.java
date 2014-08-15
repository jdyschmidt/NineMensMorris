package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Game;


public abstract class GameDisplay extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private SlotButton[][] positionButtons;
	private Game game;
	
	abstract public void fillSlot(int square, int location, int player);

	public SlotButton getPositionButton(int square, int location) {
		return positionButtons[square][location];
	}

	protected void setPositionButtons(SlotButton[][] positionButtons, ImageIcon buttonUnfocused, ImageIcon buttonFocused, ImageIcon buttonPressed) {
		this.positionButtons = positionButtons;
		for (int i = 0; i != positionButtons.length; i++) {
			for (int j = 0; j != positionButtons[i].length; j++) {
				this.positionButtons[i][j] = new SlotButton(buttonUnfocused, buttonFocused, buttonPressed);
				add(this.positionButtons[i][j]);
				this.positionButtons[i][j].setActionCommand(i+";"+j);
				this.positionButtons[i][j].addActionListener(this);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		int square = Integer.parseInt(e.getActionCommand().substring(0, e.getActionCommand().indexOf(";")));
		int location = Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf(";")+1, e.getActionCommand().length()));
		game.clickPosition(square, location);
	}

	protected void setGame(Game game) {
		this.game = game;
	}
	
	//1 for 1, 2 for 2, 4 for both
	public void setDisabled(int players) {
		if (!(1==players || players==2 || players==4)) {
			try {
				throw new Exception("Player value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (players==4) {
			for (int i = 0; i != positionButtons.length; i++) {
				for (int j = 0; j != positionButtons[i].length; j++) {
					if (positionButtons[i][j].getFilled()!=0)
						positionButtons[i][j].setEnabled(false);
				}
			}
		}
		else {
			for (int i = 0; i != positionButtons.length; i++) {
				for (int j = 0; j != positionButtons[i].length; j++) {
					if (positionButtons[i][j].getFilled()==players)
						positionButtons[i][j].setEnabled(false);
				}
			}
		}
	}
}
