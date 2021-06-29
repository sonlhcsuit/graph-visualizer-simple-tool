package uit.tool.app;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Event.SettingEvent;
import uit.tool.app.components.Event.UserEvent;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.components.menuComponent.Menu;
import uit.tool.app.components.visualizerComponent.VisualizerView;
import uit.tool.app.components.visualizerComponent.graphComponent.GraphView;
import uit.tool.app.components.visualizerComponent.matrixComponent.MatrixView;
import uit.tool.app.components.navigationComponent.Navigation;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class App extends BorderPane implements Loader {


	private boolean isMenuOpen;
	//	@FXML
//	private Label hehe;
//	@FXML
//	private ImageView img;
//	@FXML
//	private VBox side;
	@FXML
	private VisualizerView visualizerView;
	@FXML
	private Navigation navigation;
	@FXML
	private Logger logger;
	@FXML
	private Menu menu;

	private Graph graph;


	public App() {
		Loader.loadFXML(this);
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		this.visualizerView.setGraph(this.graph);
		this.menu.setSetting(this.graph.getSetting());
	}

	public void initialize() {
		isMenuOpen = true;
		this.visualizerView.setLogger(this.logger);

//		Graph g = Graph.sampleGraph();
//		this.graph = g;
//		this.visualizerView.setGraph(g);
//		this.menu.setSetting(g.getSetting());


		this.addEventFilter(UserEvent.SAVE_GRAPH, this::saveGraphUserEventHandler);
		this.addEventFilter(UserEvent.NEW_GRAPH, this::newGraphUserEventHandler);
		this.addEventFilter(UserEvent.OPEN_GRAPH, this::openGraphUserEventHandler);
		this.addEventFilter(UserEvent.SAVE_AS_GRAPH, this::saveAsUserEventHandler);
		this.addEventFilter(UserEvent.SETTING, this::settingUserEventHandler);


		this.addEventFilter(SettingEvent.TOGGLE_DIRECTED, (SettingEvent event) -> {
			this.visualizerView.render();
		});

		this.addEventFilter(SettingEvent.TOGGLE_WEIGHTED, (SettingEvent event) -> {
			this.visualizerView.render();
		});

//		Set event filter, whenever graph change, automatically render new view
		this.addEventHandler(EdgeEvent.UPDATE_WEIGHT, this::weightHandler);
		this.addEventHandler(VertexEvent.REMOVE, this::removeEventHandler);
		this.addEventHandler(VertexEvent.RENAME, this::renameEventHandler);
		this.addEventHandler(VertexEvent.ADD, this::addEventFilter);
	}


	public void weightHandler(EdgeEvent event) {
		this.graph.updateEdge(event.getRow(), event.getCol(), event.getWeight());
		this.visualizerView.render();
	}

	private void addEventFilter(VertexEvent event) {
		this.visualizerView.render();
	}

	private void renameEventHandler(VertexEvent event) {
		this.visualizerView.render();
	}

	private void removeEventHandler(VertexEvent event) {
		this.visualizerView.render();
	}


	private void newGraphUserEventHandler(UserEvent event) {
		Graph g = new Graph();
		this.setGraph(g);
	}

	private void openGraphUserEventHandler(UserEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a new graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File file = fc.showOpenDialog(null);
		try {
			Graph g = Graph.load(file.getAbsolutePath());
			this.setGraph(g);
			System.out.println("Open a graph");
		} catch (IllegalStateException | IOException | JsonSyntaxException e) {
			showError("Error", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveAsUserEventHandler(UserEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save graph as");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showSaveDialog(null);
		if (f != null) {
			try {
				String fp = f.getAbsolutePath();
				this.graph.getSetting().setFilepath(fp);
				Graph.save(this.graph);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void saveGraphUserEventHandler(UserEvent event) {
		try {
			if (this.graph == null) {
				System.out.println("graph is null");
			}
			if (this.graph.getSetting().getFilepath() == null) {
				saveAsUserEventHandler(null);
			} else {
				Graph.save(this.graph);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void settingUserEventHandler(UserEvent event) {

	}

	private void showError(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
