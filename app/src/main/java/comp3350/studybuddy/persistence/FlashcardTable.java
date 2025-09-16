package comp3350.studybuddy.persistence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import comp3350.studybuddy.objects.Flashcard;

public interface FlashcardTable extends BasicTable<Flashcard> {
	/**
	 * Get all flashcards by a given user
	 * @param name The name of the user
	 * @return The found flashcards
	 */
	@NonNull List<Flashcard> getUsersFlashcards(@NonNull String name);

	/**
	 * Deletes the card with the same number as the given card
	 * @param card The card to search for
	 */
	void deleteCard(@NonNull Flashcard card);

	/**
	 * Gets the flashcard with the matching number, or null if none are found
	 * @param num The card number to search for
	 * @return The found flashcard, or null
	 */
	@Nullable Flashcard getCard(int num);

	/**
	 * Overrides the card with the same number
	 * @param updatedCard The new card to store in the table
	 */
	void updateCard(@NonNull Flashcard updatedCard);
}
