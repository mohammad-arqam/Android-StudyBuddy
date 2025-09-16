package comp3350.studybuddy.business;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import comp3350.studybuddy.objects.Flashcard;

public class QuizManager {
	private List<Flashcard> flashcards;
	private int currentIndex;
	private int maxQuestions;

	public QuizManager(List<Flashcard> flashcards) {
		this.flashcards = flashcards;
		Collections.shuffle(this.flashcards);
		currentIndex = 0;
		maxQuestions = flashcards.size();
	}

	public void setMaxQuestions(int requestedMax) {
		if (requestedMax < 1 || requestedMax > flashcards.size()) {
			maxQuestions = flashcards.size();
		} else {
			maxQuestions = requestedMax;
		}

		flashcards = flashcards.subList(0, maxQuestions);
	}

	public boolean hasNextFlashcard() {
		return currentIndex < flashcards.size();
	}

	@Nullable
	public Flashcard getCurrentFlashcard() {
		if (!hasNextFlashcard()) return null;

		return flashcards.get(currentIndex);
	}

	public void moveToNextFlashcard() {
		currentIndex++;
	}

	public int getCurrentIndex() {
		return currentIndex + 1;
	}

	public int getMaxQuestions() {
		return maxQuestions;
	}

	public List<Flashcard> getFlashcards() {
		return flashcards;
	}
}
