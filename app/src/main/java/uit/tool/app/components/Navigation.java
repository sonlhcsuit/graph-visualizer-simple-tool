package uit.tool.app.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import uit.tool.app.interfaces.Loader;


public class Navigation extends VBox implements Loader {
	private boolean isMenuOpen;
	@FXML
	private Circle avatarClip;
	@FXML
	private ImageView logo;
	@FXML
	private ImageView avatar;
	@FXML
	public Button btnNew;

	@FXML
	public HBox btnGroupNew;

	public Navigation() {
		Loader.loadFXML(this);
		System.out.println("hehe");
	}

	public void initialize() {
		isMenuOpen = true;
		logo.setOnMouseClicked((MouseEvent e) -> {
			toggleMenu();
		});
	}

	public void toggleMenu() {
		if (isMenuOpen) {
			closeMenu();
		} else {
			openMenu();
		}
		isMenuOpen = !isMenuOpen;
	}

	public void openMenu() {
		btnGroupNew.getChildren().add(1, btnNew);
	}

	public void closeMenu() {
		try {
			btnGroupNew.getChildren().remove(1);

		} catch (Exception e) {

		}
		System.out.println(btnNew);
	}
}
