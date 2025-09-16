package comp3350.studybuddy.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.List;

import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.persistence.FlashcardTableStub;
import comp3350.studybuddy.persistence.HSQLDB.FlashcardTableHSQLDB;

public class AccessFlashcardsTest {
	@Test
	public void testAccessFlashcards(){
		System.out.println("Starting AccessFlashcards Tests");

		AccessFlashcards accessFlashcards = new AccessFlashcards(new FlashcardTableStub());

		// test getFlashcardsAll()
		List<Flashcard> flashcardList = accessFlashcards.getFlashcardsAll();
		assertNotNull(flashcardList);
		assertEquals(flashcardList.size(), 2);
		assertEquals("What province is Winnipeg located?", flashcardList.getFirst().getQuestion());
		assertEquals("Manitoba", flashcardList.getFirst().getAnswer());
		assertEquals("Bob", flashcardList.getFirst().getUsername());

		assertEquals("What country is Manitoba located?", flashcardList.getLast().getQuestion());
		assertEquals("Canada", flashcardList.getLast().getAnswer());
		assertEquals("Bob", flashcardList.getFirst().getUsername());

		// test addFlashcard()
		Flashcard newFlashcard = new Flashcard("123", "abc", "def");
		accessFlashcards.addFlashcard(newFlashcard);
		flashcardList = accessFlashcards.getFlashcardsAll();

		assertEquals(flashcardList.size(), 3);
		assertEquals("123", flashcardList.getLast().getUsername());
		assertEquals("abc", flashcardList.getLast().getQuestion());
		assertEquals("def", flashcardList.getLast().getAnswer());

		// test getUsersFlashcards()
		flashcardList = accessFlashcards.getUsersFlashcards("Bob");
		assertEquals(flashcardList.size(), 2);
		assertEquals("Bob", flashcardList.getLast().getUsername());
		assertEquals("Bob", flashcardList.getFirst().getUsername());

		System.out.println("Finished AccessFlashcards Tests");
	}
}
