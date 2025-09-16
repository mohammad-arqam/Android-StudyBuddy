package comp3350.studybuddy.persistence;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * All the things that all our tables ought to have
 * Collected into a basic interface to ensure consistency
 * @param <T> The type of data the table stores
 */
public interface BasicTable<T> {
	/**
	 * Adds an item object to the table
	 * @param item The object to add
	 */
	void add(@NonNull T item);

	/**
	 * Gets a list of all items in the table
	 * @return The list of all items
	 */
	@NonNull List<T> getAll();

	/**
	 * Clears the entire table
	 */
	void deleteAll();
}
