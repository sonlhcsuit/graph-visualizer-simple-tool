package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import uit.tool.app.components.event.VertexEvent;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.Objects;


public class VertexView extends StackPane implements Loader {
	@FXML
	Label vName;
	@FXML
	Circle circle;
	ContextMenu ctm;
	private Vertex vertex;

	public VertexView(@NamedArg(value = "x", defaultValue = "0") double x,
					  @NamedArg(value = "y", defaultValue = "0") double y,
					  @NamedArg(value = "name", defaultValue = "A") String name) {
		Loader.loadFXML(this);
		AnchorPane.setLeftAnchor(this, x);
		AnchorPane.setTopAnchor(this, y);
		vName.setText(name);
	}

	public VertexView(Vertex vertex) {
		this(vertex.getX(), vertex.getY(), vertex.getName());
		this.vertex = vertex;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	/**
	 * create black square as a image
	 *
	 * @return square black image
	 */
	private Image blackSquare() {
		WritableImage wImage = new WritableImage(40, 40);
		PixelWriter writer = wImage.getPixelWriter();
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				writer.setColor(i, j, Color.BLACK);
			}
		}
		return wImage;
	}

	public void initialize() {
		this.ctm = createContextMenu();
		this.setOnDragDetected((MouseEvent event) -> {
//			For drag & drop vertex
			Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
			db.setDragView(blackSquare());
			ClipboardContent content = new ClipboardContent();
			content.putString("");
			db.setContent(content);
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			event.setDragDetect(true);
		});

		this.setOnContextMenuRequested(event -> {
			ctm.show(this, event.getScreenX(), event.getScreenY());
			event.consume();

		});
	}

	/**
	 * create a context menu when click on a vertex view, allow user rename the vertex, remove the vertex (it must be alone - no any edge )
	 *
	 * @return the context menu it self
	 */
	public ContextMenu createContextMenu() {
		ContextMenu ctm = new ContextMenu();

		MenuItem rename = new MenuItem("Rename");
		MenuItem remove = new MenuItem("Remove");

		rename.setOnAction((ActionEvent) -> {
			this.fireEvent(new VertexEvent(VertexEvent.RENAME, this));
		});
		remove.setOnAction((ActionEvent) -> {
			this.fireEvent(new VertexEvent(VertexEvent.REMOVE, this));

		});
		// Add MenuItem to ContextMenu
		ctm.getItems().addAll(rename, remove);
		return ctm;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VertexView that = (VertexView) o;
		return Objects.equals(vertex, that.vertex);
	}

}
