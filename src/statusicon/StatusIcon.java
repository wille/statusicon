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
		/**
		 * Will draw images in progressIcons depending on value
		 */
		IMAGES,
		
		/**
		 * Will draw progressbar, check fields background, foreground and border
		 */
		PROGRESSBAR,
		
		/**
		 * Will draw progressbar and percentage in the middle of the icon
		 */
		PERCENTAGE;
	}

	private Mode mode;
	private int width;
	private int height;
	private TrayIcon icon;
	private int max = 100;
	private int value;
	private List<Image> progressIcons = new ArrayList<Image>();
	private boolean indeterminate;
	
	private Color background = Color.gray;
	private Color foreground = Color.green.darker();
	private Color border = Color.black;
	
	private IndeterminateThread thread;
	
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
		} else if (mode == Mode.PROGRESSBAR || mode == Mode.PERCENTAGE) {
			int w = (int) (((float) value / (float) max) * width);
			
			g.setColor(background);
			g.fillRect(0, 0, width, height);
			
			g.setColor(foreground);
			g.fillRect(0, 0, w, height);
			
			if (border != null) {
				g.setColor(border);
				g.drawRect(0, 0, width - 1, height - 1);
			}
			
			if (mode == Mode.PERCENTAGE) {
				String val = (int) (((float) value / (float) max) * 100) + "";
				
				g.setColor(Color.white);
				g.drawString(val, width / 2 - g.getFontMetrics().stringWidth(val) / 2, height / 2 + g.getFontMetrics().getHeight() / 4);
			}
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

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}
	
	public void setIndeterminate(boolean b) {
		this.indeterminate = b;
		
		if (b) {
			thread = new IndeterminateThread();
			thread.start();
		} else {
			thread.interrupt();
			value = 0;
		}
	}
	
	public boolean isIndeterminate() {
		return this.indeterminate;
	}
	
	private class IndeterminateThread extends Thread {
		
		@Override
		public void run() {
			while (indeterminate) {
				try {
					value++;
					if (value >= max) {
						value = 0;
					}
					Thread.sleep(500L / progressIcons.size());
					update();
				} catch (InterruptedException ex) {
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
