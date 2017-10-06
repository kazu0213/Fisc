package Client.Fisc.event.events.player;

import Client.Fisc.event.Event;

public class EventPlayerVelocity extends Event {

	public double mx, my;

	public EventPlayerVelocity(double x, double y) {
		this.mx = x;
		this.my = y;
	}

}
