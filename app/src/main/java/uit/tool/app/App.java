package uit.tool.app;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.util.Callback;
import uit.tool.app.components.Logger;
import uit.tool.app.components.graphComponent.GraphView;


public class App extends BorderPane {

	private boolean isMenuOpen;
	@FXML
	private Label hehe;
	@FXML
	private ImageView img;
	@FXML
	private VBox side;
	@FXML
	private Logger logger;
	@FXML
	private GraphView graph;

	private Callback<String, Void> writeLog;

	public void initialize() {
		isMenuOpen = true;

		this.writeLog = (String message) -> {
			this.logger.writeLog(message);
			return null;
		};
		this.graph.setWriteLog(this.writeLog);

//		pane.setOnMouseClicked((MouseEvent e) -> {
//			System.out.printf("Width: %.3f Height: %.3f\n", pane.getWidth(), pane.getHeight());
//		});
	}

	public void click() {
		if (isMenuOpen) {
			this.side.setPrefWidth(64);
		} else {
			this.side.setPrefWidth(256);
		}
		isMenuOpen = !isMenuOpen;
	}

}
