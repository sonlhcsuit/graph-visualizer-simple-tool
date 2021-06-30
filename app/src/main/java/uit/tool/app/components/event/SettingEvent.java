package uit.tool.app.components.event;

import javafx.event.Event;
import javafx.event.EventType;
import uit.tool.app.graph.Setting;


public class SettingEvent extends Event {
	public static final EventType<SettingEvent> TOGGLE_DIRECTED = new EventType<>(Event.ANY,"TOGGLE_DIRECTED");
	public static final EventType<SettingEvent> TOGGLE_WEIGHTED = new EventType<>(Event.ANY,"TOGGLE_WEIGHTED");
	public static final EventType<SettingEvent> SAVE_GRAPH = new EventType<>(Event.ANY,"SAVE_GRAPH_1");


	private Setting setting;
	public SettingEvent(EventType<? extends Event> eventType, Setting setting) {
		super(eventType);
		this.setting = setting;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
}
