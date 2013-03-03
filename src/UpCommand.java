/**
 * 
 * 
 */
class UpCommand extends Command {
	@Override
	public boolean execute(Player player) {
		String direction = "north";

		if (hasSecondWord())
			System.out.println("You know where you are going.");
		else {
			player.move(direction);
		}
		return false;
	}
}
