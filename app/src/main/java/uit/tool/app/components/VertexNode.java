package uit.tool.app.components;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import uit.tool.app.interfaces.Loader;


public class VertexNode extends StackPane implements Loader {
	@FXML
	Circle cir;
	public VertexNode(){
		Loader.loadFXML(this);
	}
	public void initialize(){

		this.setOnDragDetected((MouseEvent event) -> {
			System.out.println("Circle 1 drag detected");

			Dragboard db = this.startDragAndDrop(TransferMode.ANY);

			ClipboardContent content = new ClipboardContent();
			content.putString("Circle source text");
			db.setContent(content);
		});
		this.setOnMouseDragged((MouseEvent event) -> {
			event.setDragDetect(true);
		});


//		this.setOnDragOver(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				if (event.getGestureSource() != this && event.getDragboard().hasString()) {
//					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//				}
//
//				event.consume();
//			}
//		});
//		this.setOnDragDropped((DragEvent event) -> {
//			Dragboard db = event.getDragboard();
//			if (db.hasString()) {
//				System.out.println("Dropped: " + db.getString());
//				event.setDropCompleted(true);
//			} else {
//				event.setDropCompleted(false);
//			}
//			event.consume();
//		});


//		this.setOnmou(e->{
//			System.out.println("click");
//		});
////		setOnMousePressed((MouseEvent e)->{
////			e.getX()
////		});
//		this.setOnMousePressed((MouseEvent e)->{
////			System.out.println(cir.getRadius());
////			System.out.println(this.getWidth());
////			System.out.println(this.getHeight());
////			System.out.printf("%f %f",e.getX(),e.getY());
////			AnchorPane.setTopAnchor(this,e.getY()+10);
////			AnchorPane.setLeftAnchor(this,e.getX()+10);
//			System.out.println("Start");
////			e.consume();
//		});
//		this.setOnMouseReleased((MouseEvent e)->{
////			System.out.println(cir.getRadius());
////			System.out.println(this.getWidth());
////			System.out.println(this.getHeight());
////			System.out.printf("%f %f",e.getX(),e.getY());
//			AnchorPane.setTopAnchor(this,600.0);
//			AnchorPane.setLeftAnchor(this,600.0);
//			System.out.println("trigger");
////			e.consume();
//		});
	}
}
