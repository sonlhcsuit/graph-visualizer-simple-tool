package uit.tool.app.components.graphComponent;

import javafx.beans.DefaultProperty;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;


public class VertexView extends StackPane implements Loader {
	@FXML
	Label vName;

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


	}
}
