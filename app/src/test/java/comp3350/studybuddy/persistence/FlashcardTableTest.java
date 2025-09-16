package comp3350.studybuddy.persistence;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

import comp3350.studybuddy.objects.Flashcard;

public class FlashcardTableTest {
	private final FlashcardTable table = new FlashcardTableStub();

	@Before
	public void setup() {
		table.deleteAll();
		var rem = table.getAll();
		assertEquals(0, rem.size());
	}

	private void testSize(int expected) {
		assertNotNull(table.getAll());
		assertEquals(expected, table.getAll().size());
	}

	@Test
	public void testGetAll() {
		testSize(0);
		table.add(new Flashcard("Bob", "A", "B"));
		testSize(1);
		table.add(new Flashcard("Jack", "C", "D"));
		testSize(2);
		table.add(new Flashcard("Sally", "E", "F"));
		testSize(3);
		table.add(new Flashcard("Frank", "G", "H"));
		testSize(4);
	}

	@Test
	public void testGetUsersFlashcards() {
		testSize(0);
		table.add(new Flashcard("Bob", "A", "B"));
		testSize(1);
		table.add(new Flashcard("Bob", "C", "D"));
		testSize(2);
		table.add(new Flashcard("Sally", "E", "F"));
		testSize(3);
		table.add(new Flashcard("Frank", "G", "H"));
		testSize(4);

		List<Flashcard> flashcardList = table.getUsersFlashcards("Bob");
		assertEquals(flashcardList.size(), 2);
		assertEquals("Bob", flashcardList.getLast().getUsername());
		assertEquals("Bob", flashcardList.getFirst().getUsername());
	}

	@Test
	public void testInsert() {
		testSize(0);
		table.add(new Flashcard("Bob", "A", "B"));
		testSize(1);
		table.add(new Flashcard("Jack", "C", "D"));
		testSize(2);
		table.add(new Flashcard("Sally", "E", "F"));
		testSize(3);
		table.add(new Flashcard("Frank", "G", "H"));
		testSize(4);
	}

	@Test
	public void testDelete() {
		var cardA = new Flashcard("Bob", "A", "B");
		var cardB = new Flashcard("Jack", "C", "D");
		var cardC = new Flashcard("Sally", "E", "F");
		table.add(cardA);
		table.add(cardB);
		table.add(cardC);
		testSize(3);

		// Ensure we can delete an entry
		table.deleteCard(cardA);
		testSize(2);

		var cards = table.getAll();
		for (var c : cards) {
			assertNotEquals(cardA.getCardNum(), c.getCardNum());
		}
	}
}
