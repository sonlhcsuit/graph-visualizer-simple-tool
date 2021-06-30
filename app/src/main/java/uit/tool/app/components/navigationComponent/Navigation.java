package uit.tool.app.components.navigationComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import uit.tool.app.components.event.UserEvent;
import uit.tool.app.interfaces.Loader;

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
	@FXML
	public NavigationItem newItem;
	@FXML
	public NavigationItem openItem;
	@FXML
	public NavigationItem saveAsItem;
	@FXML
	public NavigationItem settingItem;


	public Navigation() {
		Loader.loadFXML(this);
	}

	public void initialize() {
		isMenuOpen = true;
		navBrandIcon.setOnMouseClicked((MouseEvent e) -> {
			toggleMenu();
		});
		toggleMenu();
		this.newItem.setOnMouseClicked(this::newItemHandler);
		this.openItem.setOnMouseClicked(this::openFunction);
		this.saveAsItem.setOnMouseClicked(this::saveAsHandler);
		this.settingItem.setOnMouseClicked(this::settingItemHandler);
	}


	public void newItemHandler(MouseEvent event) {
		this.fireEvent(new UserEvent(UserEvent.NEW_GRAPH));
	}

	public void openFunction(MouseEvent event) {
		this.fireEvent(new UserEvent(UserEvent.OPEN_GRAPH));
	}


	public void saveAsHandler(MouseEvent event) {
		this.fireEvent(new UserEvent(UserEvent.SAVE_AS_GRAPH));
	}

	public void settingItemHandler(MouseEvent event) {
		this.fireEvent(new UserEvent(UserEvent.SETTING));
	}

	public void toggleAvatar(boolean isSmall) {
		if (isSmall) {
			Scale small = new Scale();
			small.setX(0.5);
			small.setY(0.5);
			small.setPivotX(32);
			small.setPivotY(32);
			avatar.getTransforms().add(small);
		} else {
			avatar.getTransforms().clear();
		}
	}

	public void toggleBrand(boolean isSmall) {
		if (isSmall) {
			navBrand.getChildren().remove(1);
		} else {
			navBrand.getChildren().add(navBrandName);
		}
	}

	public void toggleNavigationSize(boolean isSmall) {
		if (isSmall) {
			this.setPrefWidth(64);
		} else {
			this.setPrefWidth(256);

		}
	}

	public void toggleMenu() {
		for (int i = 0; i < this.navGroup.getChildren().size(); i++) {
			NavigationItem navItem = (NavigationItem) this.navGroup.getChildren().get(i);
			navItem.toggle(isMenuOpen);
		}
		toggleAvatar(isMenuOpen);
		toggleBrand(isMenuOpen);
		toggleNavigationSize(isMenuOpen);
		isMenuOpen = !isMenuOpen;
	}

}
