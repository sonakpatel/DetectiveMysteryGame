/**
 * 
 *
 */class DropCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			Item dropItem = player.itemInInventory(getSecondWord());
			if (dropItem == null) {
				player.print("You do not have that item.");
			} else {
				player.dropItem(dropItem);
				player.print(dropItem.getName() + " has been dropped.");
			}
		} else {
			player.print("Drop What?");
		}
		return false;
	}
}