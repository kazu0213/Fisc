package Client.Fisc.event.events.move;

import Client.Fisc.event.Event;

public class EventPostMotionUpdates extends Event {

	public float yaw, pitch;
	public boolean ground;
	public double y;

	public EventPostMotionUpdates(float yaw, float pitch, boolean ground, double y) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.ground = ground;
		this.y = y;
	}

}
