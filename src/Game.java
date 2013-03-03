import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 */
public class Game {
	private Parser parser;
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	private List<NPC> npcs = new ArrayList<NPC>();
	private int progression = 0;

	private Random rand = new Random();
	private int totalRooms;

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		createRooms();
		this.parser = new Parser();
	}

	/**
	 * Play a new game!
	 */
	public static void main(String[] args) {
		new Game().play();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		Room outside = new Room("outside");
		Room office = new Room("at the police department");
		Room sergeantOffice = new Room("at your sergeants office");
		Room cafe = new Room("in the cafe");
		Room bar = new Room("in the pub");
		Room witnessHouse = new Room("at the key witness' house");
		Room victimHouse = new Room("at the victims house");
		Room playerHouse = new Room("at your house");
		Room woods = new Room("in the woods, near the murder scene");
		Room shack = new Room("at a shack hidden in the woods");
		Room murderScene = new Room("at the murder scene");
		Room teleporter = new Room("in front of a mysterious light, it shines intensily behind the trees...you move closer...");
		teleporter.setTeleportation();

		NPC sergeant = new NPC("sgt-miller", "your Sergeant", false);
		NPC partner = new NPC("det.wills", "your partner", false);
		NPC barOwner = new NPC("stan", "the bar owner", false);
		NPC witness = new NPC("mary", "the key witness", false);
		NPC shackOwner = new NPC("willard", "the shack owner", false);

		office.setExit("west", sergeantOffice);
		office.setExit("north", outside);
		office.addItem("notepad", "\n--Notepad-- \n-Notes are empty", true, 1);
		office.addItem("gun", ".357 Magnum", true, 10);
		office.addItem("mug", "mug of cold coffee, stained with multiple uses", false, 10);
		office.addItem("badge", "detective id, granting you the authority to make arrests", true, 5);
		partner.setCurrentRoom(office);

		sergeantOffice.setExit("east", office);
		sergeant.setCurrentRoom(sergeantOffice);

		outside.setExit("south", office);
		outside.setExit("north", woods);
		outside.setExit("east", bar);
		outside.setExit("west", cafe);
		outside.addItem("cuffs", "A pair of cuffs, left outside. Handy.", true, 10);

		cafe.setExit("east", outside);
		cafe.setExit("west", playerHouse);
		cafe.setExit("north", witnessHouse);

		witnessHouse.setExit("south", cafe);
		witness.setCurrentRoom(witnessHouse);

		playerHouse.setExit("east", cafe);

		bar.setExit("west", outside);
		bar.setExit("north", victimHouse);
		barOwner.setCurrentRoom(bar);

		victimHouse.setExit("south", bar);

		woods.setExit("south", outside);
		woods.setExit("north", murderScene);
		woods.setExit("east", shack);

		murderScene.setExit("south", woods);
		murderScene.setExit("north", teleporter);

		shack.setExit("west", woods);
		shackOwner.setCurrentRoom(shack);

		teleporter.setExit("south", murderScene);

		this.rooms.put("office", office);
		this.rooms.put("outside", outside);
		this.rooms.put("sergeantOffice", sergeantOffice);
		this.rooms.put("cafe", cafe);
		this.rooms.put("bar", bar);
		this.rooms.put("witnessHouse", witnessHouse);
		this.rooms.put("victimHouse", victimHouse);
		this.rooms.put("playerHouse", playerHouse);
		this.rooms.put("woods", woods);
		this.rooms.put("shack", shack);
		this.rooms.put("murderScene", murderScene);

		this.totalRooms = this.rooms.size();

		this.npcs.add(witness);
		this.npcs.add(sergeant);
		this.npcs.add(barOwner);
		this.npcs.add(partner);
		this.npcs.add(shackOwner);
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	private void play() {
		printWelcome();
		Player player = createPlayer();
		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean finished = false;
		boolean complete = false;
		String caseStatus = "OPEN";

		player.print(player.getCurrentRoom().getLongDescription());
		while (!finished) {
			Command command = this.parser.getCommand();
			if (command != null) {
				finished = command.execute(player);
				moveNPC();
				complete = checkCompletion(player);
			} else {
				player.print("Your thoughts are broken, nothing makes sense...");
			}

		}

		if (complete) {
			caseStatus = "CLOSED";
			player.print("Case " + caseStatus);
			player.print("Feel free to quit, a rest is well deserved.");
		} else if (finished) {
			player.print("Thank you for playing. Case " + caseStatus);
		}
	}

	private Player createPlayer() {
		return new Player(this.rooms.get("office"));
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("Detective Briggs - Homicide.");
		System.out.println("Sitting behind the desk won't solve this...the town of Crestwalk.");
		System.out.println("The murderer of the late Sophie must be found and brought to justice.");
		System.out.println("If not for the bureau or town, but for her family.");
		System.out.println("Type 'help' if you need assistance.");
		System.out.println();
	}

	private void moveNPC() {
		double prob = 1.0D;
		Room room;
		double r;

		for (Iterator<Room> i$ = this.rooms.values().iterator(); i$.hasNext();) {
			room = i$.next();

			r = Math.random();
			for (NPC n : this.npcs)
				if ((room.npcIsInRoom(n)) && (n.moovable()) && (r < prob)) {
					room.removeNPC(n);
					Room nextRoom = room.removeThenRoom(n);
					nextRoom.addNPC(n);
				}
		}

	}

	private boolean checkCompletion(Player player) {
		Item playerNotepad = player.itemInInventory("notepad");
		switch (this.progression) {
		case 0:
			if (player.getCurrentRoom().equals(this.rooms.get("witnessHouse")))
				if (playerNotepad != null) {
					player.print("You have spoken to the witness, much has been talked about.");
					playerNotepad.description = "\n--Notepad--\n-Intimate conversation with the key witness generates lead, \nsomeone else not yet spoken to holds some information.\n\t - Find someone in the bureau to talk to.";

					player.replaceItemInvetory("notepad", playerNotepad);
					player.print("Key information added to notepad, thrilling information!");
					player.print("Use the look command on your notepad.");
					this.progression += 1;
				} else {
					player.print("If only I had my notepad to take an interview.");
				}
			break;
		case 1:
			if (player.getCurrentRoom().equals(this.rooms.get("office"))) {
				Item npcNotepad = this.npcs.get(3).itemInInventory("notepad");
				if (npcNotepad != null) {
					npcNotepad.description += "\n\n- Your partner says that if anyone is witholding information, \nit will be the man in the woods. I think he is being purposely vague...\n\t - Speak to someone who resides in the woods. You have a warrant to search the premises too.";

					player.addItem(npcNotepad);
					player.newItem("warrant", "A warrant to search the house/shack and question the resident", true, 10);
					player.print("Det. Wills - I've given you a warrant to search the shack in the woods, \nwasn't too hard to get condering his strage activities.");
					player.print("Key information added to notepad, thrilling information!");
					this.progression += 1;
				} else {
					player.print("You need to give your partner your findings.");
				}
			}
			break;
		case 2:
			if (player.getCurrentRoom().equals(this.rooms.get("shack")))
				if (this.npcs.get(4).itemInInventory("warrant") != null) {
					player.print("Talking extensively with the shack owner has pressured him into a corner.\nHis only leverage for how he knows this information is some key evidence \nwhich he carelessly mentions he left somewhere...?");

					player.print("Find that piece of evidence!");

					Room randRoom = randRoom(this.rooms.get("sergeantOffice"));
					randRoom.addItem(
							"evidence",
							"Incriminating evidence - Keg Stopper covered with blood, possibly the victims. There is only one person who would carry this on their person.",
							true, 5);
					player.print("HINT: Item dropped " + randRoom.getShortDescription());
					this.progression += 1;
				} else {
					player.print("The forceful shack owner refuses to talk since no crime was commited by himself. Some leverage is needed on my part...");
				}
			break;
		case 3:
			if (player.itemInInventory("evidence") != null)
				if ((player.itemInInventory("cuffs") != null) || (player.itemInInventory("badge") != null)) {
					this.rooms.get("bar").removeNPC(this.npcs.get(2));

					this.rooms.get("bar").addItem("criminal",
							"the bar owner, Stan, now able to be dropped off at the police station and taken into custody.", true, 1);
					player.print("Bar owner, cuffed, reprimanded. Ready to be taken into custody. Time to get him to the sergeant.");
					this.progression += 1;
				} else {
					player.print("I can't make an arrest without proving I'm a detective...");
				}
			break;
		case 4:
			if (this.npcs.get(1).itemInInventory("criminal") != null) {
				player.print("At last, this case may be closed, and the victims family may have the closure they desperately needed. Now to work on the next case...");
				return true;
			}

			break;
		}

		return false;
	}

	public Room randRoom(Room playerRoom) {
		Room randomRoom = null;

		int item = this.rand.nextInt(this.totalRooms);
		int i = 0;
		for (Room r : this.rooms.values()) {
			if ((i == item) && (r != playerRoom)) {
				randomRoom = r;
			}
			i++;
		}
		return randomRoom;
	}
}