package uit.tool.app.components.Event;

import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;

public class VertexEvent extends Event {
	/**
	 *
	 */
	public static final EventType<VertexEvent> MOVE = new EventType<>(ANY, "MOVE");
	public static final EventType<VertexEvent> ADD = new EventType<>(ANY, "ADD");
	public static final EventType<VertexEvent> RENAME = new EventType<>(ANY, "RENAME");
	public static final EventType<VertexEvent> REMOVE = new EventType<>(ANY, "REMOVE");

	private VertexView vertexView;
	private double relativeX;
	private double relativeY;


	public VertexEvent(EventType<? extends Event> type, VertexView vw) {
		/**
		 * An event was trigger when a user want to rename, remove or add an existed vertex
		 */
		super(type);
		this.vertexView = vw;
	}

	public VertexEvent(EventType<? extends Event> type, VertexView vw, double relativeX, double relativeY) {
		/**
		 * An event was trigger when a vertex moved. Remember that because of graph view need to resize so the
		 * actual size bigger than viewport size, so when drag & drop, position of the cursor just relative
		 * (with top & left edges of viewport, not actual pane)
		 *
		 * @param relativeX the distance from left edge of graph view to the cursor (dropped)
		 * @param relativeY the distance from top edge of graph view to the cursor (dropped)
		 *
		 * @see javafx.scene.control.ScrollPane
		 */

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
