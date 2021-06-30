package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;

public class AlgorithmEvent extends Event {
	public static final EventType<AlgorithmEvent> DFS = new EventType<>(ANY,"ANIMATION_DFS");
	public static final EventType<AlgorithmEvent> BFS = new EventType<>(ANY,"ANIMATION_BFS");
	public static final EventType<AlgorithmEvent> NEXT_ANIMATION = new EventType<>(ANY,"NEXT_ANIMATION");



	private int index;
	public AlgorithmEvent(EventType<AlgorithmEvent> type){
		super(type);
	}

	public AlgorithmEvent(EventType<AlgorithmEvent> type, int index){
		super(type);
		if (!NEXT_ANIMATION.equals(type)){
			return;
		}
		this.index = index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
