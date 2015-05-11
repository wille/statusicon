package statusicon;

import java.awt.Graphics2D;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class StatusIcon {

	private TrayIcon icon;
	
	public StatusIcon(TrayIcon icon) {
		this.icon = icon;
	}
	
	public StatusIcon() {
		
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(update());
	}
	
	public BufferedImage update() {
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		return image;
	}
}
