package uit.tool.app.components.Event;

import javafx.event.Event;
import javafx.event.EventType;

public class SettingEvent extends Event {
	public static final EventType<SettingEvent> CHANGE_NAME = new EventType<>(Event.ANY,"CHANGE_NAME");
	public SettingEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}
}
