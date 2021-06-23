package uit.tool.app.components.Event;

import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.components.graphComponent.VertexView;

public class VertexMove extends Event {
	/**
	 *
	 */
	public static final EventType<VertexMove> MOVE = new EventType<>(ANY, "MOVE");
	public static final EventType<VertexMove> WEIGHT = new EventType<>(ANY,"WEIGHT");
	private VertexView vertexView;
	private double relativeX;
	private double relativeY;


	public VertexMove(EventType<? extends Event> type, VertexView v) {
		super(type);
		this.vertexView = v;
	}

	public VertexMove(EventType<? extends Event> type, VertexView v, double relativeX, double relativeY) {
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

		this(type, v);
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
