package comp3350.studybuddy.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
	@Test
	public void testUser() {
		System.out.println("Starting User Object Tests");

		User user1 = new User("Bob");

		assertNotNull(user1);
		assertNotNull(user1.getUsername());
		assertEquals("Bob", user1.getUsername());

		System.out.println("Finished User Object Tests");
	}
}
