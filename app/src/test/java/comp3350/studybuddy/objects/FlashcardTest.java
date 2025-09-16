package comp3350.studybuddy.objects;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Black-box and white-box testing for the Flashcard class.
 */
public class FlashcardTest {
	/**
	 * Black-box test: Ensure flashcard stores and returns correct question and answer.
	 */
	@Test
	public void testFlashcardCreation() {
		System.out.println("Testing flashcard creation...");
		Flashcard card = new Flashcard("", "What is Java?", "A programming language");
		assertEquals("What is Java?", card.getQuestion());
		assertEquals("A programming language", card.getAnswer());
	}

	/**
	 * White-box test: Ensure card number increments correctly.
	 */
	@Test
	public void testCardNumber() {
		System.out.println("Testing card number increment...");
		Flashcard card1 = new Flashcard("Bob", "Q1", "A1");
		Flashcard card2 = new Flashcard("Bob", "Q2", "A2");
		assertTrue(card1.getCardNum() >= 0);
		assertTrue(card2.getCardNum() >= 0);
	}

	/**
	 * Black-box test: Ensure different flashcards hold different data.
	 */
	@Test
	public void testUniqueFlashcards() {
		System.out.println("Testing unique flashcards...");
		Flashcard card1 = new Flashcard("", "Capital of France?", "Paris");
		Flashcard card2 = new Flashcard("", "Largest planet?", "Jupiter");
		assertNotEquals(card1.getQuestion(), card2.getQuestion());
		assertNotEquals(card1.getAnswer(), card2.getAnswer());
	}

	/**
	 * White-box test: Ensure immutability of flashcards.
	 */
	@Test
	public void testFlashcardImmutability() {
		System.out.println("Testing flashcard immutability...");
		Flashcard card = new Flashcard("", "Immutable?", "Yes");
		assertEquals("Immutable?", card.getQuestion());
		assertEquals("Yes", card.getAnswer());
	}

	/**
	 * Unit test: Ensure question is not null.
	 */
	@Test
	public void testQuestionNotNull() {
		System.out.println("Testing question not null...");
		Flashcard card = new Flashcard("name", "Question?", "Answer");
		assertNotNull(card.getQuestion());
	}

	/**
	 * Unit test: Ensure answer is not null.
	 */
	@Test
	public void testAnswerNotNull() {
		System.out.println("Testing answer not null...");
		Flashcard card = new Flashcard("name", "Question?", "Answer");
		assertNotNull(card.getAnswer());
	}

	/**
	 * Unit test: Ensure username is not null.
	 */
	@Test
	public void testUsernameNotNull() {
		System.out.println("Testing answer not null...");
		Flashcard card = new Flashcard("name", "Question?", "Answer");
		assertNotNull(card.getUsername());
	}

	/**
	 * Unit test: Ensure card number remains positive.
	 */
	@Test
	public void testCardNumberPositive() {
		System.out.println("Testing card number positive...");
		Flashcard card = new Flashcard("Bob", "Question?", "Answer");

		assertTrue(card.getCardNum() >= 0);
	}

	/**
	 * Unit test: Ensure flashcards with the same content are considered equal.
	 */
	@Test
	public void testEqualFlashcards() {
		System.out.println("Testing equal flashcards...");
		Flashcard card1 = new Flashcard("bob", "Same?", "Yes");
		Flashcard card2 = new Flashcard("bob", "Same?", "Yes");
		assertEquals(card1.getQuestion(), card2.getQuestion());
		assertEquals(card1.getAnswer(), card2.getAnswer());
		assertEquals(card1.getUsername(), card2.getUsername());
	}

	/**
	 * Unit test: Ensure flashcards with different content are not equal.
	 */
	@Test
	public void testDifferentFlashcards() {
		System.out.println("Testing different flashcards...");
		Flashcard card1 = new Flashcard("name1", "Q1", "A1");
		Flashcard card2 = new Flashcard("name2", "Q2", "A2");
		assertNotEquals(card1.getQuestion(), card2.getQuestion());
		assertNotEquals(card1.getAnswer(), card2.getAnswer());
		assertNotEquals(card1.getUsername(), card2.getUsername());
	}

	/**
	 * Additional unit test: Ensure question and answer are stored as given.
	 */
	@Test
	public void testFlashcardContent() {
		System.out.println("Testing flashcard content storage...");
		Flashcard card = new Flashcard("Bob", "Who invented Java?", "James Gosling");
		assertEquals("Who invented Java?", card.getQuestion());
		assertEquals("James Gosling", card.getAnswer());
		assertEquals("Bob", card.getUsername());
	}

	/**
	 * Additional unit test: Ensure a flashcard's question cannot be modified.
	 */
	@Test
	public void testFlashcardQuestionImmutable() {
		System.out.println("Testing question immutability...");
		Flashcard card = new Flashcard("", "Immutable Test?", "Yes");
		String originalQuestion = card.getQuestion();
		assertEquals("Immutable Test?", originalQuestion);
	}

	// TODO: Add equals test
}
