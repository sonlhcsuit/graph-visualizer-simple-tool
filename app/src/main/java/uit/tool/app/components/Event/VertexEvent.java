package uit.tool.app.components.Event;
import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.components.graphComponent.VertexView;

public class VertexEvent extends Event {
	public static final EventType<VertexEvent> MOVE = new EventType<>(ANY,"MOVE");
	private VertexView vertexView;
	private double newX;
	private double newY;
	public VertexEvent(EventType<? extends Event> type, VertexView v){
		super(type);
		this.vertexView = v;
	}
	public VertexEvent(EventType<? extends Event> type, VertexView v, double X,double Y){
		this(type,v);
		this.newX = X;
		this.newY = Y;
	}

	public double getNewX() {
		return newX;
	}
	public double getNewY(){
		return newY;
	}

	public VertexView getVertexView() {
		return vertexView;
	}
}
