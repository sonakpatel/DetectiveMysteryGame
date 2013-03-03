import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognise commands as they are typed in.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class CommandWords {
	private HashMap<String, Command> commands = new HashMap<String, Command>();

	/**
	 * Constructor - initialise the command words.
	 */
	public CommandWords() {
		this.commands.put("go", new GoCommand());
		this.commands.put("quit", new QuitCommand());
		this.commands.put("exit", new QuitCommand());
		this.commands.put("help", new HelpCommand(this));
		this.commands.put("take", new TakeCommand());
		this.commands.put("drop", new DropCommand());
		this.commands.put("look", new LookCommand());
		this.commands.put("items", new ItemsCommand());
		this.commands.put("up", new UpCommand());
		this.commands.put("down", new DownCommand());
		this.commands.put("left", new LeftCommand());
		this.commands.put("right", new RightCommand());
		this.commands.put("back", new BackCommand());
		this.commands.put("give", new GiveCommand());
	}

	/**
	 * Given a command word, find and return the matching command object.
	 * 
	 * @param word
	 *            The command word.
	 * @return The command invoked by the given word. Return null if there is no
	 *         match.
	 */
	public Command getCommand(String word) {
		return this.commands.get(word);
	}

	/**
	 * Print all valid commands to System.out.
	 */
	public void showAll() {
		Iterator<String> iterator = this.commands.keySet().iterator();

		while (iterator.hasNext()) {
			System.out.print(iterator.next() + " ");
		}

		System.out.println();
	}
}