package Client.Fisc.event.events.misc;

import Client.Fisc.event.Event;

public class EventKeyboard extends Event {

	private int k;

	public EventKeyboard(int k) {
		this.k = k;
	}

	public int getKey() {
		return this.k;
	}

}
