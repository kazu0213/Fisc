package Client.Fisc.module.render.hud;

import Client.Fisc.Fisc;
import Client.Fisc.module.Module;
import Client.Fisc.module.render.hud.components.TabGUI;
import Client.Fisc.setting.Setting;
import org.lwjgl.input.Keyboard;

import Client.Fisc.module.render.hud.components.Arraylist;

public class HUD extends Module {

	private Arraylist ar;
	private TabGUI tab;

	public HUD() {
		super("HUD", Keyboard.KEY_F, Category.RENDER);
		Fisc.INSTANCE.SETTING_MANAGER.addSetting(new Setting(this, "ArrayList", true));
		Fisc.INSTANCE.SETTING_MANAGER.addSetting(new Setting(this, "TabGUI", true));
		this.ar = new Arraylist();
		this.tab = new TabGUI();
	}

	public void onEnable() {
		super.onEnable();
		Fisc.INSTANCE.EVENT_MANAGER.register(ar);
		Fisc.INSTANCE.EVENT_MANAGER.register(tab);
	}

	public void onDisable() {
		super.onDisable();
		Fisc.INSTANCE.EVENT_MANAGER.unregister(ar);
		Fisc.INSTANCE.EVENT_MANAGER.unregister(tab);
	}


}
