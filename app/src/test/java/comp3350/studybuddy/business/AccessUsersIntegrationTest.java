package comp3350.studybuddy.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.studybuddy.Utilities.TestUtilities;
import comp3350.studybuddy.objects.User;

public class AccessUsersIntegrationTest {
	private AccessUsers accessUsers;
	private File tempDB;

	@Before
	public void setup() throws IOException {
		this.tempDB = TestUtilities.copyDB();
		this.accessUsers = new AccessUsers();
	}

	@Test
	public void testAccessUsers() {
		User testUser;

		// Test searchName()
		testUser = accessUsers.searchName("Bob");
		assertNotNull("user should exist", testUser);
		assertEquals("Bob", testUser.getUsername());
		System.out.println("Finished test searchName()");

		// Test addUser()
		testUser = new User("TEST USER");
		accessUsers.addUser(testUser);
		testUser = accessUsers.searchName("TEST USER");
		assertNotNull("user should exist", testUser);
		assertEquals("TEST USER", testUser.getUsername());
		System.out.println("Finished test addUser()");

		// Test deleteUser()
		testUser = accessUsers.deleteUser("TEST USER");
		assertNotNull("user should exist", testUser);
		assertEquals("TEST USER", testUser.getUsername());
		System.out.println("Finished test deleteUser()");

		// Test getUserList()
		List<User> userList = accessUsers.getUserList();
		assertNotNull("user should exist", testUser);
		testUser = accessUsers.searchName("Bob");
		assertNotNull(testUser);
		assertEquals("Bob", testUser.getUsername());
		System.out.println("Finished test getUserList()");

		// Test deleteAll()
		accessUsers.deleteAll();
		userList = accessUsers.getUserList();
		assert(userList.isEmpty());
		testUser = accessUsers.searchName("Bob");
		assertNull(testUser);
		System.out.println("Finished test deleteAll()");

		System.out.println("Finished integration test AccessUsers");
	}

	@After
	public void tearDown() {
		// reset DB
		this.tempDB.delete();
	}
}
