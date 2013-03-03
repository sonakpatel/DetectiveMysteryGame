/**
 * This class allows for the creation of items that will be used in the game.
 * 
 * 
 */

public class Item {
	String name;
	String description;
	boolean takeable;
	int weight;

	/**
	 * Create an item. Enter the description of the item into the description
	 * field. Enter the weight of the item into the weight field.
	 */
	public Item(String name, String description, boolean takeable, int weight) {
		this.name = name;
		this.description = description;
		this.takeable = takeable;
		this.weight = weight;
	}

	/**
	 * Returns the name of the item. This will be shown to the player when
	 * picking up an item.
	 * 
	 * @return The name of the item
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the weight of the item. This will be shown to the player when
	 * picking up an item.
	 * 
	 * @return The description of the item
	 */
	public String getDescription() {
		return this.description;
	}

	public int getWeight() {
		return this.weight;
	}

	public boolean isTakeable() {
		if (this.takeable) {
			return true;
		}
		return false;
	}

	/**
	 * Concantenates the name and the description of the item.
	 * 
	 * @return The name and description of the item
	 */
	@Override
	public String toString() {
		return this.name + " - " + this.description;
	}
}
