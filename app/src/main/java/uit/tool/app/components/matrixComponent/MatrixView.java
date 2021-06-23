package uit.tool.app.components.matrixComponent;

import javafx.scene.layout.GridPane;
import uit.tool.app.interfaces.Loader;

public class MatrixView extends GridPane implements Loader {

	public MatrixView(){
		Loader.loadFXML(this);
	}
}
