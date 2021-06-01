package uit.tool.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;

import java.util.ArrayList;
import java.util.Objects;

public class Main
//        extends Application
{

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        System.out.println();
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("App.fxml")));
////        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb-app.fxml")));
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        String version_check = String.format("Hello, JavaFX %s, running on Java %s.", javafxVersion, javaVersion);
//        System.out.println(version_check);
//
//        setUserAgentStylesheet(STYLESHEET_CASPIAN);
//        primaryStage.setTitle("GVST");
//        primaryStage.setScene(new Scene(root, 1080, 720));
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }

	public static void main(String[] args) {
		Vertex a = new Vertex("a", 0.0, 0.0);
		Vertex b = new Vertex("b", 0.0, 0.0);
		Vertex c = new Vertex("c", 0.0, 0.0);
		Vertex d = new Vertex("d", 0.0, 0.0);
		Vertex e = new Vertex("e", 0.0, 0.0);

		ArrayList<Vertex> V = new ArrayList<>();
		V.add(a);
		V.add(b);
		V.add(c);
		V.add(d);
		V.add(e);

		Edge ab = new Edge(a, b);
		Edge bc = new Edge(b, c);
		Edge de = new Edge(d, e);
		Edge ea = new Edge(e, a);
		Edge be = new Edge(b, e);

		ArrayList<Edge> E = new ArrayList<>();
		E.add(ab);
		E.add(bc);
		E.add(de);
		E.add(ea);
		E.add(be);
		Graph graph = new Graph(V, E);
		System.out.println(graph.getVertexNames());
//        launch(args);
//        Graph gr = new Graph();
//        System.out.println(gr.toString());
	}

}
