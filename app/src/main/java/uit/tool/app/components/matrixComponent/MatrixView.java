package uit.tool.app.components.matrixComponent;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;

public class MatrixView extends GridPane implements Loader {

	private Graph graph;

	public MatrixView() {
		Loader.loadFXML(this);
	}

	public void initialize() {
		this.graph = Graph.sampleGraph();

		renderMatrix();

	}

	public TextField makeCell(String name,boolean isDisabled) {
		TextField t = new TextField();
		t.setAlignment(Pos.CENTER);
		t.setDisable(isDisabled);
		t.setText(name);
		t.setPrefSize(40, 40);
		t.textProperty()
				.addListener(this::handlerTextChange);
		return t;
	}

	private void handlerTextChange(Observable observable) {
		System.out.println(observable);
	}

	public void renderMatrix() {
		double[][] matrix = this.graph.adjacencyMatrix();
		ArrayList<String> names = this.graph.getVertexNames();
		int size = matrix.length;
		for (int i = 0; i < size; i++) {
			this.add(makeCell(names.get(i),true), 0, i + 1);
			this.add(makeCell(names.get(i),true), i + 1, 0);
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.add(makeCell(String.format("%.2f", matrix[i][j]),false), j + 1, i + 1);
			}

		}
	}

}
