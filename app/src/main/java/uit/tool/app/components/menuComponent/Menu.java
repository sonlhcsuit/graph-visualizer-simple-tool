package uit.tool.app.components.menuComponent;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uit.tool.app.interfaces.Loader;

public class Menu extends VBox implements Loader {
	public TextField name;
	public Button btn;

	public Menu(){
		Loader.loadFXML(this);
	}
}
