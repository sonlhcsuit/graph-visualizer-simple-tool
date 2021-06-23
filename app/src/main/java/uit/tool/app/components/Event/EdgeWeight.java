package uit.tool.app.components.Event;


import javafx.event.Event;
import javafx.event.EventType;

public class EdgeWeight extends Event {
	public final static EventType<EdgeWeight> UPDATE = new EventType<>(ANY, "UPDATE");

	private int row, col;
	private double weight;

	public EdgeWeight(EventType<EdgeWeight> type, int row, int col, double weight) {
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
