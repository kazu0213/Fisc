package Client.Fisc.module;

import Client.Fisc.Fisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;

public class Module {

	/** Common variables for every module. **/
	protected static Minecraft mc = Minecraft.getMinecraft();
	protected static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	protected static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

	/** Name of the module. **/
	private String name;

	/** Holds the key that the module is currently bounded to. **/
	private int keyCode;

	/** Holds weather the module is toggled or not. **/
	private boolean toggled;

	/** Holds what category the module is in. **/
	private Category category;

	public Module(String name, int keyCode, Category category) {
		this.name = name;
		this.keyCode = keyCode;
		this.category = category;
		this.toggled = false;
	}

	/** All the valid categories for every module. **/
	public enum Category {
		COMBAT, MOVEMENT, RENDER, WORLD, PLAYER
	}

	/** Toggles the module depending on its previous state. **/
	public void toggle() {
		this.toggled = !this.toggled;
		if (this.toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	/**
	 * Called when the module is turned on. (Registers the module so it can use
	 * events).
	 **/
	public void onEnable() {
		Fisc.INSTANCE.EVENT_MANAGER.register(this);
	}

	/**
	 * Called then the module is turned off. (Unregisters the module so none of the
	 * events are usable.
	 **/
	public void onDisable() {
		Fisc.INSTANCE.EVENT_MANAGER.unregister(this);
	}

	/** Returns weather the module is module is toggled or not. **/
	public boolean isToggled() {
		return this.toggled;
	}

	/** Returns the name of the module. **/
	public String getName() {
		return name;
	}

	/** Returns the key that the module is currently bound too. **/
	public int getKeyCode() {
		return keyCode;
	}

	/** Sets what key the module is bound too. **/
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	/** Gets the category that the module belongs too. **/
	public Category getCategory() {
		return category;
	}

}
