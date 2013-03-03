
class HelpCommand extends Command {
	private CommandWords commandWords;

	public HelpCommand(CommandWords words) {
		this.commandWords = words;
	}

	@Override
	public boolean execute(Player player) {
		System.out
				.println("The murderer must be found, this case must be closed.");
		System.out.println("Investigate, question, discover the truth.");
		System.out.println();
		System.out.println("Your command words are:");
		this.commandWords.showAll();
		return false;
	}
}