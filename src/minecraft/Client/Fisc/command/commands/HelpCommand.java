package Client.Fisc.command.commands;

import Client.Fisc.Fisc;
import Client.Fisc.command.Command;

public class HelpCommand implements Command {

	@Override
	public boolean run(String[] args) {
		Fisc.INSTANCE.tellPlayer("Here are the list of commands:");
		for(Command c : Fisc.INSTANCE.COMMAND_MANAGER.getCommands().values()) {
			Fisc.INSTANCE.tellPlayer(c.usage());
		}
		return true;
	}

	@Override
	public String usage() {
		return "USAGE: -help";
	}

}
