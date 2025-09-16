package comp3350.studybuddy.business;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import comp3350.studybuddy.objects.Flashcard;

public class QuizManagerTest {
	@Test
	public void testQuizManager() {
		System.out.println("Starting QuizManager Tests");

		// Create test flashcards
		List<Flashcard> testFlashcards = new ArrayList<>();
		testFlashcards.add(new Flashcard("What is 2+2?", "4", "User1"));
		testFlashcards.add(new Flashcard("What is the capital of Canada?", "Ottawa", "User1"));
		testFlashcards.add(new Flashcard("What is the square root of 16?", "4", "User1"));

		// Initialize QuizManager
		QuizManager quizManager = new QuizManager(testFlashcards);
		assertNotNull(quizManager);

		// Ensure flashcards are loaded
		assertEquals(3, quizManager.getFlashcards().size());

		// Set max questions and check limit
		quizManager.setMaxQuestions(2);
		assertEquals(2, quizManager.getMaxQuestions());
		assertEquals(2, quizManager.getFlashcards().size());

		// Check first question
		Flashcard firstFlashcard = quizManager.getCurrentFlashcard();
		assertNotNull(firstFlashcard);

		// Move to next question
		quizManager.moveToNextFlashcard();
		Flashcard secondFlashcard = quizManager.getCurrentFlashcard();
		assertNotNull(secondFlashcard);
		assertNotEquals(firstFlashcard, secondFlashcard);

		// Ensure quiz completes
		quizManager.moveToNextFlashcard();
		assertFalse(quizManager.hasNextFlashcard());

		System.out.println("Finished QuizManager Tests");
	}
}
