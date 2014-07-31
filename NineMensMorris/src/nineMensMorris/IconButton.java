package nineMensMorris;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IconButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public IconButton(ImageIcon normal, ImageIcon focused, ImageIcon pressed) {
		super(normal);
		setRolloverIcon(focused);
		setPressedIcon(pressed);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	public void setBounds(double d, double e, int i, int j) {
		super.setBounds((int)d,(int)e,i,j);
	}
	
	

}
