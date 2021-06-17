package uit.tool.app;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;


public class App {

	private boolean isMenuOpen;
	@FXML
	private Label hehe;
	@FXML
	private ImageView img;
	@FXML
	private VBox side;
//	@FXML
//	private ScrollPane pane;

	public void initialize() {
		isMenuOpen = true;
//		pane.setOnMouseClicked((MouseEvent e) -> {
//			System.out.printf("Width: %.3f Height: %.3f\n", pane.getWidth(), pane.getHeight());
//		});
	}

	public void click() {
		if (isMenuOpen) {
			side.setPrefWidth(64);
		} else {
			side.setPrefWidth(256);
		}
		isMenuOpen = !isMenuOpen;
		System.out.println(side.getWidth());
		System.out.println("click");

	}

}
