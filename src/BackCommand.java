/**
 * 
 *
 */
class BackCommand extends Command {
	@Override
	public boolean execute(Player player) {
		player.getBack();
		return false;
	}
}