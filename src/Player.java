import java.util.ArrayList;

public class Player {
	private ArrayList<Item> inventory;
	private Room previousRoom;
	private Room currentRoom;
	private int invetorySize = 100;

	public Player(Room currentRoom) {
		setCurrentRoom(currentRoom);
		this.inventory = new ArrayList<Item>();
	}

	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	public void getBack() {
		Room lastRoom = this.currentRoom;
		this.currentRoom = this.previousRoom;
		this.previousRoom = lastRoom;
		print("You went back, you are now in "
				+ this.currentRoom.getShortDescription());
	}

	private void setCurrentRoom(Room room) {
		this.previousRoom = this.currentRoom;
		this.currentRoom = room;
	}

	public void move(String direction) {
		Room nextRoom = this.currentRoom.getExit(direction);

		if (nextRoom == null) {
			print("You have lost your way, you try to find your bearings.");
		} else {
			setCurrentRoom(nextRoom);
			print("You are " + this.currentRoom.getShortDescription());
			if (getCurrentRoom().isTeleporter()) {
				Room newRoom = new Game().randRoom(this.currentRoom);
				setCurrentRoom(newRoom);
				print("You have a throbbing headache from a burst of blinding light, you seem to have crossed time and space...\nyou are now "
						+ this.currentRoom.getShortDescription() + "?!");
			}
		}
	}

	/**
	 * Adds an item to the players inventory
	 */
	public void addItem(Item item) {
		this.inventory.add(item);
	}

	public void newItem(String name, String description, boolean takeable,
			int weight) {
		this.inventory.add(new Item(name, description, takeable, weight));
	}

	/**
	 * Removes an item from the players inventory and drops in the current room
	 */
	public boolean dropItem(Item item) {
		if (this.inventory.contains(item)) {
			removeItemInvetory(item);
			this.currentRoom.addItem(item);
			return true;
		}
		return false;
	}

	public void removeItemInvetory(Item item) {
		this.inventory.remove(item);
	}

	public Item itemToRemove(String itemName) {
		for (Item i : this.inventory) {
			if (itemName.equals(i.getName())) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Check if an item is in the inventory.
	 * 
	 * @return the item if it is, or null if it is not found
	 */
	public Item itemInInventory(String itemName) {
		for (Item i : this.inventory) {
			if (itemName.equals(i.getName())) {
				return i;
			}
		}
		return null;
	}

	public String listInventory() {
		String items = "";
		if (this.inventory.isEmpty())
			items = "  ~~Empty~~\n";
		else {
			for (Item i : this.inventory) {
				items = items + i.getName() + "\t" + i.getWeight() + "\n";
			}
		}
		return items;
	}

	public int getInventMaxSize() {
		return this.invetorySize;
	}

	public int getInventWeight() {
		int weight = 0;
		for (Item i : this.inventory) {
			weight += i.getWeight();
		}
		return weight;
	}

	public Item getItem(String item) {
		return this.currentRoom.itemInRoom(item);
	}

	public void removeItemFromRoom(Item item) {
		this.currentRoom.removeItemFromRoom(item);
	}

	public void print(String string) {
		System.out.println(string);
	}

	public boolean canTake(Item pickUp) {
		if (pickUp.isTakeable()) {
			return true;
		}
		return false;
	}

	public NPC getNPC(String npcName) {
		return this.currentRoom.getNPC(npcName);
	}

	void replaceItemInvetory(String itemName, Item newItem) {
		if (this.inventory.remove(itemToRemove(itemName)))
			addItem(newItem);
	}
}