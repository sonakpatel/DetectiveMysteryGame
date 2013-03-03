public class GoCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			String direction = getSecondWord();
			player.move(direction);
		} else {
			player.print("Go where?");
		}
		return false;
	}
}