package Client.Fisc;

import Client.Fisc.event.EventTarget;
import Client.Fisc.module.Module;
import Client.Fisc.setting.SettingManager;
import org.lwjgl.opengl.Display;

import com.mojang.realmsclient.gui.ChatFormatting;

import Client.Fisc.command.CommandManager;
import Client.Fisc.event.EventManager;
import Client.Fisc.event.events.misc.EventKeyboard;
import Client.Fisc.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Fisc {

	public static Fisc INSTANCE = new Fisc();

	/** The variables that hold the Client Name and the Client Version. **/
	public final String CLIENT_NAME = "Fisc";
	public final double CLIENT_VERSION = 1.0;

	/** Declaring all the objects, required for the client to function. **/
	public EventManager EVENT_MANAGER;
	public ModuleManager MODULE_MANAGER;
	public SettingManager SETTING_MANAGER;
	public CommandManager COMMAND_MANAGER;

	/**
	 * This method is called after minecraft is done loading. This method
	 * instantiates all the objects required for the client to function.
	 **/
	public void onEnable() {
		/** Instantiating all the objects. **/
		EVENT_MANAGER = new EventManager();
		MODULE_MANAGER = new ModuleManager();
		SETTING_MANAGER = new SettingManager();
		COMMAND_MANAGER = new CommandManager();

		/**
		 * Registers this class in the EventManager so it can look for methods that
		 * require an event to function.
		 **/
		EVENT_MANAGER.register(this);

		/** Sets the title of the window. (Seen on top left of screen). **/
		Display.setTitle(CLIENT_NAME + " 1.8.8");

		/** Loading **/
		MODULE_MANAGER.loadMods();
		COMMAND_MANAGER.loadCommands();
	}

	/**
	 * This method is called when minecraft is shutting down. Usually the only thing
	 * that goes here is saving the files.
	 **/
	public void onDisable() {
		EVENT_MANAGER.unregister(this);

	}

	/**
	 * This method can be used to send your player a message. This message can only
	 * be seen by you, no one else.
	 **/
	public void tellPlayer(String text) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
				ChatFormatting.WHITE + "[" + ChatFormatting.RED + CLIENT_NAME + ChatFormatting.WHITE + "] " + text));
	}

	/**
	 * This method is listening for a keyboard key press and checks if that key
	 * matches any of the keys for the modules. If it matches it toggles the module.
	 **/
	@EventTarget
	public void onKey(EventKeyboard e) {
		for (Module m : MODULE_MANAGER.getMods()) {
			if (m.getKeyCode() == e.getKey()) {
				m.toggle();
			}
		}
	}

}
