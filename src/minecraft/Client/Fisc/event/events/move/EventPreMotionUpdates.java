package Client.Fisc.event.events.move;

import Client.Fisc.event.Event;

public class EventPreMotionUpdates extends Event {

	private boolean cancel;
	public float yaw, pitch;
	public double y;

	public EventPreMotionUpdates(float yaw, float pitch, double y) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
	}

}
