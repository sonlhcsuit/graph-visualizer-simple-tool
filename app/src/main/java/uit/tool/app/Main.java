package uit.tool.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.gson.Gson;

import java.util.Objects;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = new App();
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb-app.fxml")));
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		String versionString = String.format("Hello, JavaFX %s, running on Java %s.", javafxVersion, javaVersion);
		System.out.println(versionString);

		setUserAgentStylesheet(STYLESHEET_CASPIAN);
		primaryStage.setTitle("GVST - Untitled");
		primaryStage.setScene(new Scene(root, 1080, 720));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		launch(args);
	}

}
