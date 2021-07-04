package uit.tool.app.components.navigationComponent;

import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import uit.tool.app.interfaces.Loader;

public class NavigationItem extends HBox implements Loader {

	@FXML
	private Button btn;
	@FXML
	private ImageView icon;
	@FXML
	HBox container;
	private boolean isCollapsed;


	/**
	 * create the navigation item
	 * @param text the text present on the component
	 * @param icon the image present on the component
	 */
	public NavigationItem(@NamedArg("text") String text, @NamedArg("icon") String icon) {
		Loader.loadFXML(this);
		Image image = new Image(icon);
		this.icon.setImage(image);
		this.btn.setText(text);
		isCollapsed = false;
	}


	public void setCollapsed(boolean collapsed) {
		isCollapsed = collapsed;
	}

	public boolean getCollapsed() {
		return isCollapsed;
	}

	public void initialize() {
		this.btn.onMouseClickedProperty().bind(this.onMouseClickedProperty());
	}

	/**
	 * Toggle whenever the value of navigation item
	 * @param isCollapsed value of the navigation, it must be a value, true mean open - false mean close
	 */
	public void toggle(boolean isCollapsed) {
		if (isCollapsed) {
//			trigger to close
			collapse();
		} else {
//			trigger to open
			unCollapse();
		}
		setCollapsed(isCollapsed);
	}

	/**
	 * expand the navigation item itself
	 */
	public void collapse() {
		if (!isCollapsed) {
			container.getChildren().remove(1);
		}
	}

	/**
	 * shrink the navigation item itself
	 */
	public void unCollapse() {
		if (isCollapsed) {
			container.getChildren().add(btn);
		}
	}
}
