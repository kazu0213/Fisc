package Client.Fisc.command.commands;

import Client.Fisc.Fisc;
import Client.Fisc.command.Command;
import Client.Fisc.module.Module;
import org.lwjgl.input.Keyboard;

public class BindCommand implements Command {

	@Override
	public boolean run(String[] args) {
		if (args.length == 3) {
			Module m = Fisc.INSTANCE.MODULE_MANAGER.getModule(args[1]);
			m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
			Fisc.INSTANCE.tellPlayer(m.getName() + " has been bound to " + args[2] + ".");
			return true;
		}
		return false;
	}

	@Override
	public String usage() {
		return "USAGE: -bind [module] [key]";
	}

}
