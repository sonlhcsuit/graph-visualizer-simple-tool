package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;

public class AlgorithmEvent extends Event {
	public static final EventType<AlgorithmEvent> DFS = new EventType<>(ANY,"ANIMATION_DFS");
	public static final EventType<AlgorithmEvent> BFS = new EventType<>(ANY,"ANIMATION_BFS");
	public static final EventType<AlgorithmEvent> DIJKSTRA = new EventType<>(ANY,"DIJKSTRA");
	public static final EventType<AlgorithmEvent> HAM_PATH = new EventType<>(ANY,"HAMILTONIAN_PATH");
	public static final EventType<AlgorithmEvent> HAM_CYCLE = new EventType<>(ANY,"HAMILTONIAN_CYCLE");
	public static final EventType<AlgorithmEvent> EULER_PATH = new EventType<>(ANY,"EULERIAN_PATH");
	public static final EventType<AlgorithmEvent> EULER_CYCLE = new EventType<>(ANY,"EULERIAN_CYCLE");



	public AlgorithmEvent(EventType<AlgorithmEvent> type){
		super(type);
	}

	public AlgorithmEvent(EventType<AlgorithmEvent> type, int index){
		super(type);
	}

}
