package Client.Fisc.module.render.hud.components;

import java.util.ArrayList;

import Client.Fisc.Fisc;
import Client.Fisc.event.EventTarget;
import Client.Fisc.event.events.render.EventRender2D;
import Client.Fisc.module.Module;
import Client.Fisc.module.render.hud.HUD;
import Client.Fisc.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Arraylist {

	@EventTarget
	public void onRender(EventRender2D e) {
		if (!Fisc.INSTANCE.SETTING_MANAGER
				.getSetting(Fisc.INSTANCE.MODULE_MANAGER.getModule(HUD.class), "ArrayList").getBooleanValue()) {
			return;
		}
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		ArrayList<Module> mods = Fisc.INSTANCE.MODULE_MANAGER.getToggledMods();

		mods.sort((o1, o2) -> fr.getStringWidth(o2.getName()) - fr.getStringWidth(o1.getName()));
		int y = 2;
		for (Module m : mods) {
			int mWidth = fr.getStringWidth(m.getName());
			fr.drawStringWithShadow(m.getName(), e.width - mWidth - 2, y, Colors.getColor());
			y += 9 + 2;
		}
	}

}
