package comp3350.studybuddy.persistence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import comp3350.studybuddy.objects.Flashcard;

public class FlashcardTableStub implements FlashcardTable {
	private static final AtomicInteger idCounter = new AtomicInteger(1); // Ensures unique IDs

	private final List<Flashcard> table = new ArrayList<>();

	public FlashcardTableStub() {
		// Use explicit IDs for initial data
		add(new Flashcard(idCounter.getAndIncrement(), "Bob", "What province is Winnipeg located?", "Manitoba"));
		add(new Flashcard(idCounter.getAndIncrement(), "Bob", "What country is Manitoba located?", "Canada"));
	}

	@Override
	public void add(@NonNull Flashcard card) {
		Flashcard newCard = new Flashcard(idCounter.getAndIncrement(), card.getUsername(), card.getQuestion(), card.getAnswer());
		System.out.println("Adding card: " + newCard);
		table.add(newCard);
	}

	@NonNull
	@Override
	public List<Flashcard> getAll() {
		System.out.println("Retrieving all flashcards");
		return new ArrayList<>(table);
	}

	@Override
	public void deleteAll() {
		System.out.println("Deleting all flashcards");
		table.clear();
	}

	@NonNull
	@Override
	public List<Flashcard> getUsersFlashcards(@NonNull String name) {
		System.out.println("Getting flashcards of user: " + name);
		List<Flashcard> cards = getAll();
		cards.removeIf(u -> !u.getUsername().equals(name));
		return cards;
	}

	@Override
	public void deleteCard(@NonNull Flashcard card) {
		System.out.println("Deleting card: " + card);
		System.out.println("Before Deletion: " + table);
		table.removeIf(c -> c.equals(card));
		System.out.println("After Deletion: " + table);
	}

	@Nullable
	@Override
	public Flashcard getCard(int num) {
		System.out.println("Getting card number: " + num);
		for (Flashcard card : table) {
			if (card.getCardNum() == num) {
				return card;
			}
		}

		return null;
	}

	@Override
	public void updateCard(@NonNull Flashcard updatedCard) {
		for (Flashcard card : table) {
			if (card.getCardNum() == updatedCard.getCardNum()) {
				card.setQuestion(updatedCard.getQuestion());
				card.setAnswer(updatedCard.getAnswer());
				return;
			}
		}
	}
}
