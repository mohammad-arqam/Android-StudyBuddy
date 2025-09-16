package comp3350.studybuddy.business;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import comp3350.studybuddy.objects.User;
import comp3350.studybuddy.application.Services;
import comp3350.studybuddy.persistence.UserTable;

public class AccessUsers {
	private final UserTable table;

	public AccessUsers() {
		table = Services.getUserTable();
	}

	public AccessUsers(@NonNull UserTable table) {
		this.table = table;
		Services.setUserTable(table);
	}

	/**
	 * Searches the user table for the given name
	 * @param name The username to search
	 * @return The first user found, otherwise null
	 */
	@Nullable
	public User searchName(@NonNull String name) {
		List<User> users = table.searchName(name);

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	/**
	 * Adds `newUser` to the table if the name is unique
	 * @param user The user to be added to the table
	 * @return The user that was added, or null if the name already exists
	 */
	@Nullable
	public User addUser(@NonNull User user) {
		// check if user already exists
		List<User> users = table.searchName(user.getUsername());

		// return null if user already exists, return user if successfully added
		if (users.isEmpty()) {
			table.add(user);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Deletes the user with the specified name from the table
	 * @param name The name to search for
	 * @return The user that was deleted, otherwise null
	 */
	@Nullable
	public User deleteUser(@NonNull String name) {
		List<User> users = table.searchName(name);

		// if user exists, delete it, else return null on failure
		if (!users.isEmpty()) {
			table.deleteUser(name);
			return users.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Deletes the entire user table
	 */
	public void deleteAll() {
		table.deleteAll();
	}

	/**
	 * Gets the entire user table
	 * @return A list of all the users currently stored
	 */
	@NonNull
	public List<User> getUserList() {
		return table.getAll();
	}
}
