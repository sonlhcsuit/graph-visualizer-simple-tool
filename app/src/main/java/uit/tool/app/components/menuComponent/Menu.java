package uit.tool.app.components.menuComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import uit.tool.app.components.Event.SettingEvent;
import uit.tool.app.components.Event.UserEvent;
import uit.tool.app.graph.Setting;
import uit.tool.app.interfaces.Loader;

public class Menu extends VBox implements Loader {

	@FXML
	private TextField nameTextField;
	@FXML
	private CheckBox weightCheckbox;
	@FXML
	private CheckBox directCheckbox;
	@FXML
	private Button saveButton;

	@FXML
	public AlgorithmButton sample;

	private Setting setting;

	public Menu() {
		Loader.loadFXML(this);
	}

	public void initialize() {


		this.nameTextField.textProperty().addListener((obs, oldV, newV) -> {
			this.setting.setName(newV);
		});
		this.saveButton.setOnMouseClicked((MouseEvent event) -> {
			this.fireEvent(new UserEvent(UserEvent.SAVE_GRAPH));
		});


		this.weightCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
			this.setting.setWeighted(newValue);
			this.fireEvent(new SettingEvent(SettingEvent.TOGGLE_WEIGHTED, null));
		}));

		this.directCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
			this.setting.setDirected(newValue);
			this.fireEvent(new SettingEvent(SettingEvent.TOGGLE_DIRECTED, null));
		}));
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
		this.nameTextField.setText(this.setting.getName());
		this.weightCheckbox.setSelected(this.setting.isWeighted());
		this.directCheckbox.setSelected(this.setting.isDirected());
	}

	public Setting getSetting() {
		return setting;
	}

}
