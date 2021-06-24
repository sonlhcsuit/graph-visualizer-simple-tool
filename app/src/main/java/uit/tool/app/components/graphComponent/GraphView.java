package uit.tool.app.components.graphComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;


public class GraphView extends ScrollPane implements Loader {

	@FXML
	private AnchorPane graphArea;
	private Graph graph;
	private Callback<String, Void> writeLog;

	public GraphView() {
		Loader.loadFXML(this);
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setWriteLog(Callback<String, Void> writeLog) {
		this.writeLog = writeLog;
	}


	public void initialize() {
		System.out.println("Graph View init");
		this.setOnDragOver((DragEvent event) -> {
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});
		this.setOnDragDropped(this::handleDroppedEvent);

		this.setOnContextMenuRequested((ContextMenuEvent event) -> {
			this.getContextMenu().show(this, event.getScreenX(), event.getScreenY());
//			pass local position data into context menu, make handler can use it
			this.getContextMenu().setUserData(String.format("%f %f", event.getX(), event.getY()));
		});

		this.addEventFilter(VertexEvent.MOVE, this::handleVertexMove);
		this.addEventFilter(VertexEvent.REMOVE, this::vertexRemoveHandler);
		this.addEventFilter(VertexEvent.RENAME, this::vertexRenameHandler);
		this.addEventFilter(VertexEvent.ADD, this::vertexAddHandler);

////	debug purpose
//		this.setOnMouseClicked(event -> {
//			System.out.printf("Click X: %.2f Y: %.2f\n", event.getX(), event.getY());
//			System.out.printf("Size X: %.2f Y: %.2f\n", this.graphArea.getWidth(), this.graphArea.getHeight());
//			System.out.printf("Prefer Size X: %.2f Y: %.2f\n", this.getPrefWidth(), this.getPrefHeight());
//			System.out.printf("Viewport X: %.2f Y: %.2f \n", this.getViewportBounds().getWidth(), this.getViewportBounds().getHeight());
//			System.out.printf("Scrolled: X: %.2f Y: %.2f\n", this.getHvalue(), getVvalue()*(this.graphArea.getHeight() - this.getViewportBounds().getHeight()) );
//		});
	}

	private double syncAxeValue(double size, double viewport, double relative, double scrollPercent) {
		/**
		 * This method is using to compute absolute position of overflow object in clipped viewport in scrollpane
		 * The viewport will slide (pan) between the start point and end point of the line axe
		 * The viewport has it size, too. Scroll value is the difference of actual start point of the line content and the actual start
		 * point of viewport(measured in percent)
		 *
		 * Explain with example
		 *
		 *     |                          axe                              |
		 *     |-----------------------------------------------------------|
		 *     |  percent ||         |        ||
		 *                ||         X        ||
		 *                ||     viewport    ||
		 *
		 *
		 * @param size the size (how long) of the axe (the line axe)
		 * @param viewport the size(how long) of the viewport (the viewport line)
		 * @param relative the size(how long) from X to start point of the viewport
		 * @param scrollPercent the difference between start point of viewport and axe (measured in percent)
		 */

//		The different between actual edge vs relative edge (how many pixel was panned)
//		percentage is 1 as long as the current viewport can reach the lowest edge (end point of axe)
//		so we need to minus the viewport size to better accuracy

		double differenceAxe = (size - viewport) * scrollPercent;

		return differenceAxe + relative;
	}

	public void render() {
		if (this.graph == null) {
			return;
		}

		double maxOffsetX = 0;
		double maxOffsetY = 0;
		this.graphArea.getChildren().clear();
		ArrayList<Vertex> V = this.graph.getVertexes();

//		Render edge & weight on screen
		double[][] matrix = this.graph.adjacencyMatrix();
		int size = matrix.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != j && matrix[i][j] != 0) {
					EdgeView ev = new EdgeView(V.get(i), V.get(j));
					WeightedView wv = new WeightedView(V.get(i), V.get(j), matrix[i][j]);
					this.graphArea.getChildren().add(ev);
					this.graphArea.getChildren().add(wv);
				}
			}
		}
//		for (Edge e : E) {
//			EdgeView ev = new EdgeView(e.getSource(), e.getDestination());
//			WeightedView wv = new WeightedView(e.getSource(), e.getDestination(), e.getWeight());
//			this.graphArea.getChildren().add(ev);
//			this.graphArea.getChildren().add(wv);
//		}

		for (Vertex v : V) {
			this.graphArea.getChildren().add(new VertexView(v));
			maxOffsetX = Math.max(maxOffsetX, v.getX());
			maxOffsetY = Math.max(maxOffsetY, v.getY());
		}

		this.graphArea.setPrefWidth(maxOffsetX + 100);
		this.graphArea.setPrefHeight(maxOffsetY + 100);
	}

	private void handleDroppedEvent(DragEvent event) {
		/**
		 * Using this method to handle some computation when drop a vertex to Graph View
		 * @param e The drag event containing information
		 * @see VertexView
		 * @see Vertex
		 */

//		only allow vertex can be dragged & dropped, defined which Vertex are involved.
		VertexView vertex = (VertexView) event.getGestureSource();
		double relativeX = event.getX();
		double relativeY = event.getY();

		this.fireEvent(new VertexEvent(VertexEvent.MOVE, vertex, relativeX, relativeY));
		event.setDropCompleted(true);
		event.consume();

	}

	public void handleVertexMove(VertexEvent event) {
		/**
		 * This method is handle when a VertexView moved
		 * Update position of moved vertex by updating data in graph object then render new graph
		 * first by calculating absolute size of moved vertex
		 */
		Vertex vertex = event.getVertexView().getVertex();
		double absoluteX = syncAxeValue(
				this.graphArea.getWidth(), this.getViewportBounds().getWidth(),
				event.getRelativeX(), this.getHvalue()
		) - 20;
		double absoluteY = syncAxeValue(
				this.graphArea.getHeight(), this.getViewportBounds().getHeight(),
				event.getRelativeY(), this.getVvalue()
		) - 20;

		this.graph.updateVertexPosition(vertex, absoluteX, absoluteY);
		try {
			this.writeLog.call(String.format("Moved: %.2f %.2f", absoluteX, absoluteY));
		} catch (Exception e) {
			e.printStackTrace();
		}

		render();
	}

	public void vertexRemoveHandler(VertexEvent event) {
		try {
			System.out.println("Remove at graph View");
			this.graph.removeVertex(event.getVertexView().getVertex());
		} catch (IllegalStateException e) {
			event.consume();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void vertexRenameHandler(VertexEvent event) {
		try {
			String vertexName = getVertexNameFromUser("Vertex name", "Enter new name for the vertex");
			System.out.println(vertexName);
			this.graph.renameVertex(event.getVertexView().getVertex(), vertexName);
		} catch (IllegalStateException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public void vertexAddHandler(VertexEvent event) {
		try {
			String vertexName = getVertexNameFromUser("Vertex name", "Enter vertex name");
			System.out.println(vertexName);
			double absoluteX = syncAxeValue(
					this.graphArea.getWidth(), this.getViewportBounds().getWidth(),
					event.getRelativeX(), this.getHvalue()
			) - 20;
			double absoluteY = syncAxeValue(
					this.graphArea.getHeight(), this.getViewportBounds().getHeight(),
					event.getRelativeY(), this.getVvalue()
			) - 20;
			Vertex vertex = new Vertex(vertexName, absoluteX, absoluteY);
			this.graph.addVertex(vertex);

		} catch (IllegalStateException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	private String getVertexNameFromUser(String headerText, String contentText) throws IllegalStateException {
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(headerText);
		td.setContentText(contentText);
		td.showAndWait();
		String vertexName = td.getEditor().getText();
		if (vertexName.length() > 2) {
			throw new IllegalStateException("Vertex name must have maximum 2 letter");
		}
		if (vertexName.length() == 0) {
			throw new IllegalStateException("Vertex name must not be empty");
		}
		return vertexName;
	}

	public void addHandler(ActionEvent event) {
		String data = (String) this.getContextMenu().getUserData();
		double relativeX = Double.parseDouble(data.split(" ")[0]);
		double relativeY = Double.parseDouble(data.split(" ")[1]);
		this.fireEvent(new VertexEvent(VertexEvent.ADD, null, relativeX, relativeY));
	}

}
