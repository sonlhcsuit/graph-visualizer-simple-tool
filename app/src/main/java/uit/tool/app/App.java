package uit.tool.app;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.components.graphComponent.GraphView;
import uit.tool.app.components.matrixComponent.MatrixView;
import uit.tool.app.components.navigationComponent.Navigation;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
	private Navigation navigation;
	@FXML
	private Logger logger;
	@FXML
	private GraphView graphView;
	@FXML
	private MatrixView matrixView;

	private Graph graph;
	private Callback<String, Void> writeLog;


	public App() {
		Loader.loadFXML(this);
	}

	public void initialize() {
		isMenuOpen = true;

		this.graphView.setLogger(this.logger);
		this.matrixView.setLogger(this.logger);

//		Set up function for navigation
		this.navigation.getOpenFunc().setMenuFunction(this.openGraph);
		this.navigation.getSaveFunc().setMenuFunction(this.saveGraph);

//		Set event filter, whenever graph change, automatically render new view
		this.matrixView.addEventFilter(EdgeEvent.UPDATE_WEIGHT, this::weightHandler);
		this.graphView.addEventFilter(VertexEvent.REMOVE, this::removeEventHandler);
		this.graphView.addEventFilter(VertexEvent.RENAME, this::renameEventHandler);
		this.graphView.addEventFilter(VertexEvent.ADD, this::addEventFilter);

	}

	private void updateGraph(Graph g) {
		this.graph = g;
		this.graphView.setGraph(g);
		this.matrixView.setGraph(g);
		this.graphView.render();
		this.matrixView.render();
	}

	public void weightHandler(EdgeEvent event) {
		this.graph.updateEdge(event.getRow(), event.getCol(), event.getWeight());
		this.graphView.render();
	}

	private void addEventFilter(VertexEvent event) {
		render();
	}

	private void renameEventHandler(VertexEvent event) {
		render();
	}

	private void removeEventHandler(VertexEvent event) {
		render();
	}

	public void render() {
		this.graphView.render();
		this.matrixView.render();
	}


	private final Callback<Void, Void> openGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a new graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showOpenDialog(null);
		if (f != null) {
			try {
				Scanner scanner = new Scanner(f);
				StringBuilder stringBuilder = new StringBuilder();
				while (scanner.hasNextLine()) {
					stringBuilder.append(scanner.nextLine()).append("\n");
				}
				Graph g = Graph.parseFromFileString(stringBuilder.toString());
				this.updateGraph(g);
				Stage primStage = (Stage) getScene().getWindow();
				primStage.setTitle(String.format("GVST - %s", g.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	};

	private final Callback<Void, Void> saveGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showSaveDialog(null);
		if (f != null) {
			try {
				FileWriter fw = new FileWriter(f.getAbsolutePath());
				String data = Graph.convertToFileString(graph, "Graph A");
				fw.write(data);
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	};
}
