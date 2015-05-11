package statusicon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class StatusIcon {
	
	public static enum Mode {
		IMAGES,
		PROGRESSBAR,
		PERCENTAGE;
	}

	private Mode mode;
	private int width;
	private int height;
	private TrayIcon icon;
	private int max = 100;
	private int value;
	private List<Image> progressIcons = new ArrayList<Image>();
	
	public StatusIcon(Mode mode, int width, int height, TrayIcon icon, List<Image> progressIcons) {
		this.mode = mode;
		this.width = width;
		this.height = height;
		this.icon = icon;
		this.progressIcons = progressIcons;
	}
	
	public StatusIcon(Mode mode, int width, int height, TrayIcon icon) {
		this(mode, width, height, icon, new ArrayList<Image>());
	}
	
	public StatusIcon(Mode mode, TrayIcon icon) {
		this(mode, 16, 16, icon, new ArrayList<Image>());
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(update());
	}
	
	public BufferedImage update() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		if (mode == Mode.IMAGES) {
			Image current = progressIcons.get((int) (((float) value / (float) max) * progressIcons.size()));
			g.drawImage(current, 0, 0, width, height, null);						
		} else if (mode == Mode.PROGRESSBAR) {
			int w = (int) (((float) value / (float) max) * width);
			
			g.setColor(Color.black);
			g.drawRect(0, 0, width, height);
			
			g.setColor(Color.green);
			g.drawRect(0, 0, w, height);
		}
		
		if (icon != null) {
			icon.setImage(image);
		}
		
		return image;
	}
	
	public Mode getMode() {
		return this.mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public List<Image> getIcons() {
		return this.progressIcons;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
		update();
	}
	
	public int getMaximum() {
		return this.max;
	}
	
	public void setMaximum(int max) {
		this.max = max;
	}
}
