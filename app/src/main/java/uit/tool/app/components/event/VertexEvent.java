package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;

public class VertexEvent extends Event {

	public static final EventType<VertexEvent> MOVE = new EventType<>(ANY, "MOVE");
	public static final EventType<VertexEvent> ADD = new EventType<>(ANY, "ADD");
	public static final EventType<VertexEvent> RENAME = new EventType<>(ANY, "RENAME");
	public static final EventType<VertexEvent> REMOVE = new EventType<>(ANY, "REMOVE");

	private final VertexView vertexView;
	private double relativeX;
	private double relativeY;

	/**
	 * An event was trigger when a user want to rename, remove or add an existed vertex
	 */
	public VertexEvent(EventType<? extends Event> type, VertexView vw) {
		super(type);
		this.vertexView = vw;
	}

	/**
	 * An event was trigger when a vertex moved. Remember that because of graph view need to resize so the
	 * actual size bigger than viewport size, so when drag & drop, position of the cursor just relative
	 * (with top & left edges of viewport, not actual pane)
	 *
	 * @param type the type of event
	 * @param relativeX the distance from left edge of graph view to the cursor (dropped). Must provided if type is MOVE
	 * @param relativeY the distance from top edge of graph view to the cursor (dropped). Must provided if type is MOVE
	 *
	 * @see javafx.scene.control.ScrollPane
	 */
	public VertexEvent(EventType<? extends Event> type, VertexView vw, double relativeX, double relativeY) {
		this(type, vw);
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}


	public double getRelativeX() {
		return relativeX;
	}

	public double getRelativeY() {
		return relativeY;
	}

	public VertexView getVertexView() {
		return vertexView;
	}
}
