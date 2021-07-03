package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;

public class UserEvent extends Event {
	public static final EventType<UserEvent> OPEN_GRAPH = new EventType<>(ANY, "OPEN");
	public static final EventType<UserEvent> NEW_GRAPH = new EventType<>(ANY, "NEW_GRAPH");
	public static final EventType<UserEvent> SAVE_GRAPH = new EventType<>(ANY, "SAVE_GRAPH");
	public static final EventType<UserEvent> SAVE_AS_GRAPH = new EventType<>(ANY, "SAVE_AS_GRAPH");
	public static final EventType<UserEvent> SETTING = new EventType<>(ANY, "SETTING");
	public static final EventType<UserEvent> CLEAR_GRAPH = new EventType<>(ANY, "CLEAR_GRAPH");
	public UserEvent(EventType<UserEvent> type) {
		super(type);
	}
}
