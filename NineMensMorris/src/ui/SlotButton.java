package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class SlotButton extends JToggleButton {

	//Apparently I needed to add this for some reason
	private static final long serialVersionUID = 1L;
	//0 for none, 1 for player 1, 2 for player 2
	private int filled = 0;
	
	/*
	 * @param unfocused Normal icon displayed by the button
	 * @param focused Icon displayed when button is hovered over
	 * @param pressed Icon displayed when mouse is depressed over button 
	 */
	public SlotButton(ImageIcon unfocusedIcon, ImageIcon focusedIcon, ImageIcon pressedIcon, ImageIcon selectedIcon) {
		super(unfocusedIcon);
		setRolloverIcon(focusedIcon);
		setPressedIcon(pressedIcon);
		setSelectedIcon(selectedIcon);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/*
	 * Rewrite of super method for convenience
	 * @param d Left x value
	 * @param e Top y value
	 * @param i x size
	 * @param j y size
	 */
	public void setBounds(double d, double e, int i, int j) {
		super.setBounds((int)d, (int)e, i, j);
	}
	
	/*
	 * Overwrite of super method to be able to fill with pieces. May completely replace eventually
	 * No need to call manually
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (filled) {
		case 0:
			break;
		case 1:
			g.setColor(new Color(0, 0, 0));
			g.fillOval(10, 10, 20, 20);
			break;
		case 2:
			g.setColor(new Color(255, 0, 0));
			g.fillOval(10, 10, 20, 20);
			break;
		}
	}

	/*
	 * @return Value slot is filled by
	 */
	public int getFilled() {
		return filled;
	}

	/*
	 * @param val Value slot should be filled by. Required: 0 <= val <= 2
	 */
	public void setFilled(int val) {
		if (!(0<=val && val<=2)) {
			try {
				throw new Exception("Fill value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.filled = val;
	}
}
