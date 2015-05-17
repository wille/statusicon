# statusicon

Small library for generating a tray icon showing progress of a task

Available in 3 modes

- **Images** - Will draw images in progressIcons depending on value
- **Progressbar** - Will draw progressbar with specified background, foreground and border color
- **Percentage** - Will draw progressbar and percentage in the middle of the icon

## Examples

We got 8 images that each represents a step in a progressbar etc
```java
Image image = ...
TrayIcon icon = new TrayIcon(image);
StatusIcon statusIcon = new StatusIcon(Mode.IMAGES, icon);
		    
for (int i = 1; i <= 8; i++) { // icons is named load1.png to load8.png
  statusIcon.getIcons().add(IconUtils.getIcon("load" + i).getImage());
}

// Default max value is 100
statusIcon.setValue(50); // Will display 50% progress

// If we do not have any value, set indeterminate
statusIcon.setIndeterminate(true);
```
