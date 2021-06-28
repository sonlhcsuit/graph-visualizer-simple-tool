package uit.tool.app.components.menuComponent;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uit.tool.app.components.Event.SettingEvent;
import uit.tool.app.interfaces.Loader;

public class Menu extends VBox implements Loader {
	public TextField name;
	public Button btn;
	public AlgorithmButton sample;

	public Menu(){
		Loader.loadFXML(this);
	}
	public void initialize(){
		System.out.println("init");
		this.addEventFilter(SettingEvent.CHANGE_NAME,(SettingEvent event)->{
			System.out.println("filter name");
			name.setText("naan");
		});
		this.addEventHandler(SettingEvent.CHANGE_NAME,(SettingEvent event)->{
			System.out.println("handler name");
			name.setText("naan");
		});
		this.sample.setOnMouseClicked((MouseEvent event)->{
			System.out.println("clicked");
			this.fireEvent(new SettingEvent(SettingEvent.CHANGE_NAME));
		});
	}
}
