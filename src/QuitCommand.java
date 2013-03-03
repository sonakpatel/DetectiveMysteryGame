
class QuitCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			System.out.println("Quit..what?");
			return false;
		}
		return true;
	}
}