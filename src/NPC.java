import java.util.ArrayList;

/**
 * 
 *
 */
public class NPC {
	private String name;
	private String desc;
	private boolean moovable;
	private ArrayList<Item> inventory;

	public NPC(String name, String desc, boolean moovable) {
		this.name = name;
		this.desc = desc;
		this.moovable = moovable;
		this.inventory = new ArrayList<Item>();
	}

	/**
	 * Set the current room for this player.
	 * 
	 * @param room
	 *            The player's room.
	 */
	public void setCurrentRoom(Room room) {
		if (room.npcIsInRoom(this)) {
			room.removeNPC(this);
		}
		room.addNPC(this);
	}

	public boolean moovable() {
		return this.moovable;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void addItem(Item item) {
		this.inventory.add(item);
	}

	public Item itemInInventory(String itemName) {
		for (Item i : this.inventory) {
			if (itemName.equals(i.getName())) {
				return i;
			}
		}
		return null;
	}
}