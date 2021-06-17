package uit.tool.app.components.graphComponent;

import javafx.scene.shape.Line;
import uit.tool.app.graph.Vertex;

public class EdgeView extends Line{


	EdgeView(Vertex source, Vertex destination ){
		super();
		double startX,startY,endX,endY;
		startX = source.getX()+ 20;
		startY = source.getY() + 20;
		endX = destination.getX() + 20;
		endY = destination.getY() + 20;
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);
		this.getStyleClass().add("line");
		this.setStrokeWidth(2);
	}
	EdgeView(Vertex source,Vertex destination,double weighted){
		this(source,destination);
		System.out.println(weighted);
	}

}
