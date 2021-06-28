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
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;


public class VertexView extends StackPane implements Loader {
	@FXML
	Label vName;
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

	private Image setUpImage() {
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

			try {
				db.setDragView(setUpImage());

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
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
}
