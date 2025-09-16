package comp3350.studybuddy.application;

import androidx.annotation.NonNull;

import comp3350.studybuddy.persistence.FlashcardTable;
import comp3350.studybuddy.persistence.FlashcardTableStub;
import comp3350.studybuddy.persistence.HSQLDB.FlashcardTableHSQLDB;
import comp3350.studybuddy.persistence.HSQLDB.UserTableHSQLDB;
import comp3350.studybuddy.persistence.UserTable;
import comp3350.studybuddy.persistence.UserTableStub;

public class Services {
	private static FlashcardTable flashcardTable;
	private static UserTable userTable;

	/**
	 * Gets the current flashcard table
	 * @implNote Creates the table if it hasn't been initialized yet
	 * @return The flashcard table
	 */
	@NonNull
	public static synchronized FlashcardTable getFlashcardTable() {
		if (flashcardTable == null) {
			flashcardTable = new FlashcardTableHSQLDB(Main.getDBPathName());
		}

		return flashcardTable;
	}

	/**
	 * Allows setting the flashcard table object to something else
	 * @implNote Does not transfer the contents of one table to the other
	 * @param tab The table object to replace the existing one with
	 */
	public static synchronized void setFlashcardTable(@NonNull FlashcardTable tab) {
		flashcardTable = tab;
	}

	/**
	 * Gets the current user table
	 * @implNote Creates the table if it hasn't been initialized yet
	 * @return The user table
	 */
	@NonNull
	public static synchronized UserTable getUserTable() {
		if (userTable == null) {
			userTable = new UserTableHSQLDB(Main.getDBPathName());
		}

		return userTable;
	}

	/**
	 * Allows setting the user table object to something else
	 * @implNote Does not transfer the contents of one table to the other
	 * @param tab The table object to replace the existing one with
	 */
	public static synchronized void setUserTable(@NonNull UserTable tab) {
		userTable = tab;
	}
}
