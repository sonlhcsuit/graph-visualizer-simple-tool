package uit.tool.app.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.Objects;

public interface Loader {
	static <T extends Parent> void loadFXML(T component) {

		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(component);
		loader.setControllerFactory((t) -> component);
		String filename = String.format("%s.fxml", component.getClass().getSimpleName());
		try {
			loader.load(Objects.requireNonNull(component.getClass().getResourceAsStream(filename)));
		} catch (IOException e) {
			throw new RuntimeException(e);
//			throw new RuntimeException(String.format("Cannot load %s correctly", filename));
		}
	}
}
