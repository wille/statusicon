package statusicon;

import java.awt.Graphics2D;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class StatusIcon {

	private int width;
	private int height;
	private TrayIcon icon;
	
	public StatusIcon(int width, int height, TrayIcon icon) {
		this.width = width;
		this.height = height;
		this.icon = icon;
	}
	
	public StatusIcon(TrayIcon icon) {
		this(16, 16, icon);
	}
	
	public StatusIcon() {
		this(16, 16, null);
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(update());
	}
	
	public BufferedImage update() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		return image;
	}
}
