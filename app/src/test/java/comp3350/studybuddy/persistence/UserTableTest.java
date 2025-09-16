package comp3350.studybuddy.persistence;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.studybuddy.objects.User;

public class UserTableTest {
	private final UserTable table = new UserTableStub();

	@Before
	public void setup() {
		table.deleteAll();
		var rem = table.getAll();
		assertEquals(0, rem.size());
	}

	private void testSearch(String name) {
		testSearch(name, 1);
	}

	private void testSearch(String name, int expected) {
		var got = table.searchName(name);
		assertNotNull(got);
		assertEquals(expected, got.size());
		for (User u : got) {
			assertEquals(name, u.getUsername());
		}
	}

	@Test
	public void testSearch() {
		table.add(new User("Bob"));
		table.add(new User("Jack"));
		table.add(new User("Sally"));
		table.add(new User("Frank"));
		testSearch("Bob");
		testSearch("Jack");
		testSearch("Sally");
		testSearch("Frank");
		testSearch("Alice", 0);
		testSearch("", 0);
		testSearch(null, 0);
	}

	@Test
	public void testGetAll() {
		table.add(new User("Bob"));
		table.add(new User("Jack"));
		table.add(new User("Sally"));
		table.add(new User("Frank"));
		assertNotNull(table.getAll());
		assertEquals(4, table.getAll().size());
	}

	@Test
	public void testInsert() {
		testSearch("Alice", 0);
		testSearch("Bob", 0);
		table.add(new User("Alice"));
		testSearch("Alice", 1);
		testSearch("Bob", 0);
		table.add(new User("Alice"));
		testSearch("Alice", 2);
		testSearch("Bob", 0);
		table.add(new User("Bob"));
		testSearch("Alice", 2);
		testSearch("Bob", 1);
		table.add(new User("Bob"));
		testSearch("Alice", 2);
		testSearch("Bob", 2);
	}

	@Test
	public void testDelete() {
		table.add(new User("Alice"));
		table.add(new User("Bob"));
		table.add(new User("Charlie"));

		// Ensure we can delete an entry
		table.deleteUser("Alice");
		testSearch("Alice", 0);

		table.deleteUser("Bob");
		testSearch("Bob", 0);

		// Make sure nothing else got changed
		testSearch("Charlie");
	}
}
