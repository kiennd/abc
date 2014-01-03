package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AnimationIcon extends JLabel {
	public AnimationIcon(String imageName) {
		ImageIcon imageIcon = new ImageIcon(AnimationIcon.this.getClass().getResource(
				imageName));
	}
}
