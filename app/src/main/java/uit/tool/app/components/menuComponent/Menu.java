package uit.tool.app.components.menuComponent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import uit.tool.app.components.event.AlgorithmEvent;
import uit.tool.app.components.event.SettingEvent;
import uit.tool.app.components.event.UserEvent;
import uit.tool.app.graph.Setting;
import uit.tool.app.interfaces.Loader;

public class Menu extends VBox implements Loader {

	@FXML
	private AlgorithmButton GT_BFS;
	@FXML
	private AlgorithmButton GT_DFS;
	@FXML
	private AlgorithmButton SP_Dijkstra;
	@FXML
	private AlgorithmButton SP_A_star;
	@FXML
	private AlgorithmButton SP_Greedy;
	@FXML
	private AlgorithmButton HP_Backtracking;


	@FXML
	private TextField nameTextField;
	@FXML
	private CheckBox weightCheckbox;
	@FXML
	private CheckBox directCheckbox;
	@FXML
	private Button saveButton;

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

		this.GT_DFS.setOnMouseClicked(this::emitter);
		this.GT_BFS.setOnMouseClicked(this::emitter);
		this.SP_Dijkstra.setOnMouseClicked(this::emitter);
		this.SP_Greedy.setOnMouseClicked(this::emitter);
		this.HP_Backtracking.setOnMouseClicked(this::emitter);



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

	public void emitter(MouseEvent event) {
		Object source = event.getSource();

		if (GT_DFS.equals(source)) {
			this.fireEvent(new AlgorithmEvent(AlgorithmEvent.DFS));
		} else if (GT_BFS.equals(source)) {
			this.fireEvent(new AlgorithmEvent(AlgorithmEvent.BFS));
		} else if (SP_Dijkstra.equals(source)) {
			this.fireEvent(new AlgorithmEvent(AlgorithmEvent.DIJKSTRA));
		} else if (SP_Greedy.equals(source)) {
			this.fireEvent(new AlgorithmEvent(AlgorithmEvent.GREEDY));

		}else if (HP_Backtracking.equals(source)) {
			this.fireEvent(new AlgorithmEvent(AlgorithmEvent.HAM_PATH));

		}
	}
}
