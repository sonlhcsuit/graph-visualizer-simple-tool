package uit.tool.app.components.visualizerComponent.matrixComponent;


import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;

public class MatrixView extends GridPane implements Loader {

	private Logger logger;
	private Graph graph;

	public MatrixView() {
		Loader.loadFXML(this);
	}

	public void initialize() {
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

	public TextField makeCell(String name, boolean isDisabled) {
		TextField t = new TextField();
		t.setAlignment(Pos.CENTER);
		t.setDisable(isDisabled);
		t.setText(name);
		t.setPrefSize(40, 40);

		t.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					double newW = Double.parseDouble(t.getText());
					int colIndex = getColumnIndex(t);
					int rowIndex = getRowIndex(t);
					this.fireEvent(new EdgeEvent(EdgeEvent.UPDATE_WEIGHT, rowIndex - 1, colIndex - 1, newW));
					this.logger.writeLog(String.format("Changes weight to %.2f", newW));
					t.setText(String.format("%.2f", newW));

				} catch (NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Weight is invalid(must be a number!");
					alert.showAndWait();
				}
			}

		});

		return t;
	}


	public void render() {
		if (graph == null) {
			return;
		}
		getChildren().clear();
		double[][] matrix = this.graph.adjacencyMatrix();
		ArrayList<String> names = new ArrayList<>(this.graph.getVertexNames());
		int size = matrix.length;
		for (int i = 0; i < size; i++) {
			this.add(makeCell(names.get(i), true), 0, i + 1);
			this.add(makeCell(names.get(i), true), i + 1, 0);
		}
		for (int i = 0; i < size;
			 i++) {
			for (int j = 0; j < size; j++) {
				this.add(makeCell(String.format("%.2f", matrix[i][j]), i == j), j + 1, i + 1);
			}
		}
	}


}
