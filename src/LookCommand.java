/**
 * 
 *
 */
class LookCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			Item playerItem = player.itemInInventory(getSecondWord());

			Room currentRoom = player.getCurrentRoom();
			Item roomItem = currentRoom.itemInRoom(getSecondWord());

			if ((playerItem == null) && (roomItem == null)) {
				player.print("No such Item found.");
			} else if (playerItem != null)
				player.print(playerItem.getDescription());
			else if (roomItem != null) {
				player.print(roomItem.getDescription());
			}

		} else {
			player.print("You take a look around...");
			Room currentRoom = player.getCurrentRoom();
			player.print(currentRoom.getLongDescription() + "\n");
		}

		return false;
	}
}