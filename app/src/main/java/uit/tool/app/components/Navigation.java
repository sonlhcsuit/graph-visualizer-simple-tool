package uit.tool.app.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import uit.tool.app.interfaces.Loader;

import uit.tool.app.components.NavigationItem;

public class Navigation extends VBox implements Loader {
	private boolean isMenuOpen;
	
	@FXML
	private HBox navBrand;
	@FXML 
	private Label navBrandName;
	@FXML
	private ImageView navBrandIcon;
	@FXML
	private ImageView avatar;

	@FXML
	private VBox navGroup;

	public Navigation() {
		Loader.loadFXML(this);
	}

	public void initialize() {
		isMenuOpen = true;
		navBrandIcon.setOnMouseClicked((MouseEvent e) -> {
			toggleMenu();
		});
		toggleMenu();
	}
	public void toggleAvatar(boolean isSmall){
		if (isSmall){
			Scale small = new Scale();
			small.setX(0.5);
			small.setY(0.5);
			small.setPivotX(32);
			small.setPivotY(32);
			avatar.getTransforms().add(small);
		}else{
			avatar.getTransforms().clear();
		}
	}
	public void toggleBrand(boolean isSmall){
		if(isSmall){
			navBrand.getChildren().remove(1);
		}else{
			navBrand.getChildren().add(navBrandName);
		}
	}

	public void toggleNavigationSize(boolean isSmall){
		if(isSmall){
			this.setPrefWidth(64);
		}else{
			this.setPrefWidth(256);

		}
	}
	public void toggleMenu() {
		for (int i = 0; i < this.navGroup.getChildren().size(); i++) {
			NavigationItem navItem = (NavigationItem) this.navGroup.getChildren().get(i);
			navItem.toggle(isMenuOpen);
//			System.out.println(navItem.getCollapsed());
		}
		toggleAvatar(isMenuOpen);
		toggleBrand(isMenuOpen);
		toggleNavigationSize(isMenuOpen);
		isMenuOpen = !isMenuOpen;

	}

//	private


}
