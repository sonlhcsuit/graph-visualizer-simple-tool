package uit.tool.app.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import uit.tool.app.interfaces.Loader;

public class Logger extends TextArea implements Loader {

	public Logger() {
		Loader.loadFXML(this);
	}

	/**
	 * write log in the bottom component
	 * @param message message to be log in the log
	 */
	public void writeLog(String message) {
		Label l = new Label();
		l.setText(message);
		setText(getText() + message + "\n");
		setScrollTop(Integer.MAX_VALUE);
	}
}
