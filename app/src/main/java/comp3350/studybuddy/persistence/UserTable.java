package comp3350.studybuddy.persistence;

import androidx.annotation.NonNull;

import java.util.List;

import comp3350.studybuddy.objects.User;

public interface UserTable extends BasicTable<User> {
	/**
	 * Gets a list of all users whose username matches the parameter
	 * @param name The name to search for
	 * @return The list of user objects
	 */
	@NonNull List<User> searchName(@NonNull String name);

	/**
	 * Deletes the first user found with the given username
	 * @param name The name to search for
	 */
	void deleteUser(@NonNull String name);
}
