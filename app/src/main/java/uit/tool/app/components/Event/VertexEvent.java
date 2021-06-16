package uit.tool.app.components.Event;
import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.components.graphComponent.VertexView;

public class VertexEvent extends Event {
	public static final EventType<VertexEvent> MOVE = new EventType<>(ANY,"MOVE");
	private VertexView vertexView;
	public VertexEvent(EventType<? extends Event> type, VertexView v){
		super(type);
		this.vertexView = v;
	}

	public VertexView getVertexView() {
		return vertexView;
	}
}
