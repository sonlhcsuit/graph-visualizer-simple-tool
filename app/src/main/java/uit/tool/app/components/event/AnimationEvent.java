package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;

public class AnimationEvent extends Event {
	public static final EventType<AnimationEvent> DFS = new EventType<>(ANY,"ANIMATION_DFS");
	public static final EventType<AnimationEvent> BFS = new EventType<>(ANY,"ANIMATION_BFS");

	public AnimationEvent(EventType<AnimationEvent> type){
		super(type);
	}
}
