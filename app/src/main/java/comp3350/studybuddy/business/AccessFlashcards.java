package comp3350.studybuddy.business;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.application.Services;
import comp3350.studybuddy.persistence.FlashcardTable;

public class AccessFlashcards {
	private final FlashcardTable table;

	public AccessFlashcards() {
		table = Services.getFlashcardTable();
	}

	public AccessFlashcards(@NonNull FlashcardTable table) {
		this.table = table;
		Services.setFlashcardTable(table);
	}

	@NonNull
	public List<Flashcard> getFlashcardsAll() {
		return table.getAll();
	}

	@NonNull
	public List<Flashcard> getUsersFlashcards(@NonNull String name){
		return table.getUsersFlashcards(name);
	}

	public Flashcard addFlashcard(@NonNull Flashcard card) {
		// Check if the card already exists
		List<Flashcard> userCards = getUsersFlashcards(card.getUsername());
		for (Flashcard c : userCards) {
			if (c.getQuestion().equalsIgnoreCase(card.getQuestion())) {
				return null;
			}
		}

		table.add(card);
		return card;
	}

	public void deleteFlashcard(@NonNull Flashcard card) {
		table.deleteCard(card);
	}

	@Nullable
	public Flashcard getCard(int num) {
		return table.getCard(num);
	}

	public void updateFlashcard(@NonNull Flashcard updatedFlashcard) {
		table.updateCard(updatedFlashcard); // Ensure this method exists in FlashcardTable
	}
}

