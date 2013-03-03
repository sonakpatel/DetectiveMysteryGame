import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 * 
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. For each existing exit, the room stores a reference
 * to the neighboring room.
 * 
 */

public class Room {
	private String description;
	private HashMap<String, Room> exits;
	private ArrayList<Item> items;
	private ArrayList<NPC> npcs;
	private boolean teleportation = false;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 * 
	 * @param description
	 *            The room's description.
	 */
	public Room(String description) {
		this.description = description;
		this.exits = new HashMap<String, Room>();
		this.items = new ArrayList<Item>();
		this.npcs = new ArrayList<NPC>();
	}

	/**
	 * Define an exit from this room.
	 * 
	 * @param direction
	 *            The direction of the exit.
	 * @param neighbor
	 *            The room to which the exit leads.
	 */
	public void setExit(String direction, Room neighbor) {
		this.exits.put(direction, neighbor);
	}

	/**
	 * @return The short description of the room (the one that was defined in
	 *         the constructor).
	 */
	public String getShortDescription() {
		return this.description;
	}

	/**
	 * Return a description of the room in the form: You are in the kitchen.
	 * Exits: north west
	 * 
	 * @return A long description of this room
	 */
	public String getLongDescription() {
		String longDesc = "You are " + this.description + ".\n"
				+ getExitString() + "\n";

		longDesc = longDesc + getItemsString() + "\n";

		longDesc = longDesc + getNPCString() + "\n";

		return longDesc;
	}

	/**
	 * Return a string describing the room's exits, for example
	 * "Exits: north west".
	 * 
	 * @return Details of the room's exits.
	 */
	private String getExitString() {
		String returnString = "Exits:\t";
		Set<String> keys = this.exits.keySet();

		for (String exit : keys) {
			returnString = returnString + " " + exit;
		}
		return returnString;
	}

	private String getItemsString() {
		String returnString = "Objects: ";
		for (Item i : this.items) {
			returnString = returnString + " " + i.getName();
		}
		return returnString;
	}

	private String getNPCString() {
		String returnString = "People:\t";
		for (NPC n : this.npcs) {
			returnString = returnString + " " + n.getName() + " - "
					+ n.getDesc();
		}
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 * 
	 * @param direction
	 *            The exit's direction.
	 * @return The room in the given direction.
	 */
	public Room getExit(String direction) {
		return this.exits.get(direction);
	}

	/**
	 * Add item to the room with description
	 * 
	 * @param name
	 *            The name of the item to be added.
	 * @param desc
	 *            The description of the item to be added.
	 */
	public void addItem(String name, String desc, boolean takebale, int weight) {
		this.items.add(new Item(name, desc, takebale, weight));
	}

	/**
	 * Adds an item to the room, alternative constructor
	 * 
	 * @param item
	 *            Reference to the Item to be added.
	 */
	public void addItem(Item item) {
		this.items.add(item);
	}

	public Item itemInRoom(String item) {
		for (Item i : this.items) {
			if (i.getName().equals(item)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * @return name The name of the item to be added.
	 * @return desc The description of the item to be added.
	 */
	public void removeItemFromRoom(Item item) {
		this.items.remove(item);
	}

	public Room removeThenRoom(NPC n) {
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("north");
		directions.add("south");
		directions.add("east");
		directions.add("west");

		Collections.shuffle(directions);
		Room nextRoom = null;

		Iterator<String> i$ = directions.iterator();
		do {
			if (!i$.hasNext())
				break;
			String dir = i$.next();
			nextRoom = getExit(dir);
		} while (nextRoom == null);

		return nextRoom;
	}

	public boolean npcIsInRoom(NPC npc) {
		if (this.npcs.contains(npc)) {
			return true;
		}
		return false;
	}

	public void addNPC(NPC n) {
		this.npcs.add(n);
	}

	NPC getNPC(String npcName) {
		for (NPC n : this.npcs) {
			if (n.getName().equals(npcName)) {
				return n;
			}
		}
		return null;
	}

	public void removeNPC(NPC n) {
		this.npcs.remove(n);
	}

	public void setTeleportation() {
		this.teleportation = true;
	}

	public boolean isTeleporter() {
		return this.teleportation;
	}
}