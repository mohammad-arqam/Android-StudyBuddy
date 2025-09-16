package comp3350.studybuddy.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import comp3350.studybuddy.Utilities.TestUtilities;
import comp3350.studybuddy.objects.Flashcard;

public class AccessFlashcardsIntegrationTest {
	private AccessFlashcards accessFlashcards;
	private File tempDB;

	@Before
	public void setup() throws IOException {
		this.tempDB = TestUtilities.copyDB();
		this.accessFlashcards = new AccessFlashcards();
	}

	@Test
	public void testAccessFlashcards() {
		List<Flashcard> flashcardList;
		Flashcard testFlashcard;

		// Test getFlashcardsAll()
		flashcardList = accessFlashcards.getFlashcardsAll();
		assertNotNull("2 flashcards should exist", flashcardList);
		System.out.println("Finished test getFlashcardsAll()");

		// Test addFlashcard()
		testFlashcard = new Flashcard("TEST USER", "QUESTION", "ANSWER");
		accessFlashcards.addFlashcard(testFlashcard);
		flashcardList = accessFlashcards.getUsersFlashcards("TEST USER");
		assertNotNull("flashcard should exist", flashcardList);
		testFlashcard = flashcardList.getFirst();
		assertEquals("TEST USER", testFlashcard.getUsername());
		assertEquals("QUESTION", testFlashcard.getQuestion());
		assertEquals("ANSWER", testFlashcard.getAnswer());
		assert(testFlashcard.getCardNum() >= 0);
		System.out.println("Finished test addFlashcard()");

		// Test getUsersFlashcards()
		flashcardList = accessFlashcards.getUsersFlashcards("Bob");
		assertNotNull("user should exist", testFlashcard);
		assertEquals("TEST USER", testFlashcard.getUsername());
		System.out.println("Finished test addFlashcard()");

		// Test getCard()
		flashcardList = accessFlashcards.getUsersFlashcards("TEST USER");
		testFlashcard = flashcardList.getFirst();
		int cardNum = flashcardList.getFirst().getCardNum();

		// checks that the card number manually retrieved matches the card number from getCard(int) function
		assertEquals(cardNum, Objects.requireNonNull(accessFlashcards.getCard(cardNum)).getCardNum());
		System.out.println("Finished test getCard()");

		// Test deleteFlashcard()
		accessFlashcards.deleteFlashcard(testFlashcard);
		flashcardList = accessFlashcards.getUsersFlashcards("TEST USER");
		assert(flashcardList.isEmpty());
		testFlashcard = accessFlashcards.getCard(cardNum);
		assertNull(testFlashcard);
		System.out.println("Finished test deleteFlashcard()");

		System.out.println("Finished integration test AccessFlashcards");
	}

	@After
	public void tearDown() {
		// reset DB
		this.tempDB.delete();
	}
}
