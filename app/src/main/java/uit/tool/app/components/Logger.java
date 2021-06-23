package uit.tool.app.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import uit.tool.app.interfaces.Loader;

public class Logger extends ScrollPane implements Loader {
	@FXML
	private VBox log;
	public Logger(){
		Loader.loadFXML(this);
	}

	public void writeLog(String message){
		System.out.println("Logger ");
		System.out.println(message);
		Label l = new Label();
		l.setText(message);
		this.log.getChildren().add(l);
		this.setVvalue(1);
	}
}
