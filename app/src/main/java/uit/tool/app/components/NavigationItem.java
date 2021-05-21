package uit.tool.app.components;

import javafx.beans.DefaultProperty;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

	public NavigationItem(
			@NamedArg("text") String text, @NamedArg("icon") String icon
	) {
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

	}

	public void toggle(boolean isCollapsed) {
		if (isCollapsed) {
//			trigger to close
			collapse();
		} else {
//			trigger to open
			decollapse();
		}
		this.isCollapsed = isCollapsed;
	}

	public void collapse() {
		if (!isCollapsed) {
			container.getChildren().remove(1);
		}
	}

	public void decollapse() {
		if (isCollapsed) {
			container.getChildren().add(btn);
		}
	}
}
