package uit.tool.app;

import com.google.gson.JsonSyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import uit.tool.app.components.animation.VertexAnimation;
import uit.tool.app.components.animation.VisualAnimation;
import uit.tool.app.components.event.*;
import uit.tool.app.components.Logger;
import uit.tool.app.components.menuComponent.Menu;
import uit.tool.app.components.visualizerComponent.VisualizerView;
import uit.tool.app.components.navigationComponent.Navigation;
import uit.tool.app.graph.Algorithm;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class App extends BorderPane implements Loader {

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
		this.visualizerView.setLogger(this.logger);

//		render function
		this.addEventFilter(UserEvent.SAVE_GRAPH, this::saveGraphUserEventHandler);
		this.addEventFilter(UserEvent.NEW_GRAPH, this::newGraphUserEventHandler);
		this.addEventFilter(UserEvent.OPEN_GRAPH, this::openGraphUserEventHandler);
		this.addEventFilter(UserEvent.SAVE_AS_GRAPH, this::saveAsUserEventHandler);
		this.addEventFilter(UserEvent.SETTING, this::settingUserEventHandler);

//		render toggle
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

//		animation
		this.addEventFilter(AlgorithmEvent.BFS, this::BFS_Handler);
		this.addEventFilter(AlgorithmEvent.BFS, this::DFS_Handler);
		this.addEventFilter(AlgorithmEvent.DIJKSTRA, this::Dijkstra_Handler);
		this.addEventFilter(AlgorithmEvent.GREEDY, this::Greedy_Handler);
		try {
			this.setGraph(Graph.load("/Users/sonlh/sp.graph"));
		} catch (Exception e) {
			System.out.println("got bug");
		}
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
		if (this.graph != null) {
			ButtonType choice = showConfirm("Are you want to save the graph?");
			if (choice == ButtonType.YES) {
				saveGraphUserEventHandler(null);
			}
			if (choice == ButtonType.CANCEL || choice == ButtonType.CLOSE) {
				return;
			}
		}
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
		if (file == null) {
			return;
		}
		try {
			Graph g = Graph.load(file.getAbsolutePath());
			this.setGraph(g);
			System.out.println("Open a graph");
		} catch (IllegalStateException | IOException | JsonSyntaxException e) {
			showError(e.getMessage());
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
		if (f == null) {
			return;
		}
		try {
			String fp = f.getAbsolutePath();
			this.graph.getSetting().setFilepath(fp);
			Graph.save(this.graph);
		} catch (Exception e) {
			e.printStackTrace();
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

	private void showError(String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(content);
		alert.showAndWait();
	}

	private ButtonType showConfirm(String content) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Confirmation");
		alert.showAndWait();
		return alert.getResult();
	}

	//	Animation
	private void BFS_Handler(AlgorithmEvent event) {
		ArrayList<VisualAnimation> visited = Algorithm.BFS(this.graph);
		this.visualizerView.renderAnimation(visited);
	}

	private void DFS_Handler(AlgorithmEvent event) {
		ArrayList<VisualAnimation> visited = Algorithm.DFS(this.graph);
		this.visualizerView.renderAnimation(visited);
	}

	private void Dijkstra_Handler(AlgorithmEvent event) {
//		String from = visualizerView.getGraphView().getVertexNameFromUser("Enter vertex name", "Enter source vertex name");
//		String to = visualizerView.getGraphView().getVertexNameFromUser("Enter vertex name", "Enter destination vertex name");
//		ArrayList<VisualAnimation> visited = Algorithm.Dijkstra(this.graph, from, to);
		ArrayList<VisualAnimation> visited = Algorithm.Dijkstra(this.graph, "a", "h");

		//		this.visualizerView.renderAnimation(visited);
	}

	private void Greedy_Handler(AlgorithmEvent event) {
//		ArrayList<VisualAnimation> visited = Algorithm.BFS(this.graph);
//		this.visualizerView.renderAnimation(visited);
	}
}
