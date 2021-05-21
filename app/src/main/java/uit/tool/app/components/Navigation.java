package uit.tool.app.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import uit.tool.app.interfaces.Loader;

import uit.tool.app.components.NavigationItem;

public class Navigation extends VBox implements Loader {
	private boolean isMenuOpen;
	@FXML
	private Circle avatarClip;
	@FXML
	private ImageView logo;
	@FXML
	private ImageView avatar;

	@FXML
	private VBox navGroup;

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
		for (int i = 0; i < this.navGroup.getChildren().size(); i++) {
			NavigationItem navItem = (NavigationItem) this.navGroup.getChildren().get(i);
			navItem.toggle(isMenuOpen);
//			System.out.println(navItem.getCollapsed());
		}
		isMenuOpen = !isMenuOpen;

	}


}
