import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and tries
 * to interpret the line as a two word command. It returns the command as an
 * object of class Command.
 * 
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 */
public class Parser {
	private CommandWords commands; // holds all valid command words
	private Scanner reader; // source of command input
	private Scanner tokenizer;

	public Parser() {
		this.commands = new CommandWords();
		this.reader = new Scanner(System.in);
	}

	/**
	 * @return The next command from the user.
	 */
	public Command getCommand() {
		String word1 = null;
		String word2 = null;
		String word3 = null;

		System.out.print("> ");

		String inputLine = this.reader.nextLine();

		// Find up to two words on the line.
		tokenizer = new Scanner(inputLine);
		if (tokenizer.hasNext()) {
			word1 = tokenizer.next();
			if (tokenizer.hasNext()) {
				word2 = tokenizer.next();
				// note: we just ignore the rest of the input line.

				if (tokenizer.hasNext()) {
					word3 = tokenizer.next();
				}
			}

		}

		Command command = this.commands.getCommand(word1);
		if (command != null) {
			command.setSecondWord(word2);
			command.setThirdWord(word3);
		}
		return command;
	}

	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		this.commands.showAll();
	}
}
