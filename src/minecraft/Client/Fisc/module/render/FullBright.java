package Client.Fisc.module.render;

import Client.Fisc.event.EventTarget;
import Client.Fisc.module.Module;
import org.lwjgl.input.Keyboard;

import Client.Fisc.event.events.update.EventUpdate;

public class FullBright extends Module {

	private float gamma;
	private float targetGamma;

	public FullBright() {
		super("FullBright", Keyboard.KEY_G, Category.RENDER);
		this.targetGamma = 100F;
	}

	public void onEnable() {
		super.onEnable();
		this.gamma = mc.gameSettings.gammaSetting;
	}

	public void onDisable() {
		super.onDisable();
		mc.gameSettings.gammaSetting = this.gamma;
	}

	@EventTarget
	public void onRender(EventUpdate e) {
		if (mc.gameSettings.gammaSetting < this.targetGamma)
			mc.gameSettings.gammaSetting += 0.2F;
	}

}
