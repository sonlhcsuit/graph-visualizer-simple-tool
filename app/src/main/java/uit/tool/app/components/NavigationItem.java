package uit.tool.app.components;

import javafx.beans.NamedArg;
import javafx.scene.layout.HBox;
import uit.tool.app.interfaces.Loader;

public class NavigationItem extends HBox implements Loader {
	public NavigationItem(@NamedArg()){
		Loader.loadFXML(this);
	}
}
