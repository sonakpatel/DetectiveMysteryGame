/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Sonak
 */
class ItemsCommand extends Command {
	@Override
	public boolean execute(Player player) {
		player.print("--------------");
		player.print("Name\tWeight");
		player.print("--------------");
		player.print(player.listInventory());
		return false;
	}
}