class GiveCommand extends Command {
	@Override
	public boolean execute(Player player) {
		if (hasSecondWord()) {
			String itemName = getSecondWord();
			Item itemInInventory = player.itemInInventory(itemName);
			if (itemInInventory != null) {
				if (hasThirdWord()) {
					String npcName = getThirdWord();
					NPC person = player.getCurrentRoom().getNPC(npcName);
					if (person != null) {
						person.addItem(itemInInventory);
						player.removeItemInvetory(itemInInventory);
						player.print(itemInInventory.getName() + " given to "
								+ person.getName());
					} else {
						player.print("That person is not here. Take a 'look' to see who is.");
					}
				} else {
					player.print("To who?");
				}
			} else
				player.print("You do not have that item. Check your 'items'");
		} else {
			player.print("Give what?");
		}
		return false;
	}
}