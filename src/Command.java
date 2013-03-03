/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This class holds information about a command that was issued by the user. A
 * command currently consists of two strings: a command word and a second word
 * (for example, if the command was "take map", then the two strings obviously
 * are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid command
 * words. If the user entered an invalid command (a word that is not known) then
 * the command word is <null>.
 * 
 * If the command had only one word, then the second word is <null>.
 * 
 * @author
 * @version
 */

public abstract class Command {
	private String secondWord;
	private String thirdWord;

	/**
	 * Create a command object. First and second word must be supplied, but
	 * either one (or both) can be null.
	 * 
	 * @param firstWord
	 *            The first word of the command. Null if the command was not
	 *            recognised.
	 * @param secondWord
	 *            The second word of the command.
	 */
	public Command() {
		this.secondWord = null;
		this.thirdWord = null;
	}

	public String getSecondWord() {
		return this.secondWord;
	}

	public boolean hasSecondWord() {
		return this.secondWord != null;
	}

	public void setSecondWord(String secondWord) {
		this.secondWord = secondWord;
	}

	public String getThirdWord() {
		return this.thirdWord;
	}

	public boolean hasThirdWord() {
		return this.thirdWord != null;
	}

	public void setThirdWord(String thirdWord) {
		this.thirdWord = thirdWord;
	}

	public abstract boolean execute(Player paramPlayer);
}