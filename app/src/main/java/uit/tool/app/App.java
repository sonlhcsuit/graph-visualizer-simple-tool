package uit.tool.app;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;

public class App {

	private boolean isMenuOpen;
	@FXML
	private Label hehe;
	@FXML
	private ImageView img;
	@FXML
	private VBox side;

	public void initialize() {
		isMenuOpen = true;
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
