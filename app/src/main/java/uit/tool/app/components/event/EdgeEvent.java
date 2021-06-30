package uit.tool.app.components.event;


import javafx.event.Event;
import javafx.event.EventType;

public class EdgeEvent extends Event {
	public final static EventType<EdgeEvent> UPDATE_WEIGHT = new EventType<>(ANY, "UPDATE");

	private int row, col;
	private double weight;

	public EdgeEvent(EventType<EdgeEvent> type, int row, int col, double weight) {
		super(type);
		this.row = row;
		this.col = col;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
}
