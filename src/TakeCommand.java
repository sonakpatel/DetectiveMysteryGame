/**
 * 
 *
 */
class TakeCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			Item pickUp = player.getItem(getSecondWord());

			if (pickUp == null) {
				player.print("No such Item found in this area.");
			} else if (player.getInventWeight() >= player.getInventMaxSize()) {
				player.print("You have too many items, drop something.");
			} else if (player.canTake(pickUp)) {
				player.addItem(pickUp);
				player.print(pickUp.getName() + " added to items.");
				player.print("Info - " + pickUp.getDescription());
				player.removeItemFromRoom(pickUp);
			} else {
				player.print("You can't have that.");
			}
		} else {
			player.print("Take What? eg. take [item]");
		}
		return false;
	}
}