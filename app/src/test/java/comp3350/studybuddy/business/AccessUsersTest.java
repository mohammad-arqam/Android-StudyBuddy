package comp3350.studybuddy.business;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import comp3350.studybuddy.objects.User;
import comp3350.studybuddy.persistence.UserTableStub;

public class AccessUsersTest {
	@Test
	public void testAccessUsers() {
		System.out.println("Starting AccessUsers Tests");

		AccessUsers accessUsers = new AccessUsers(new UserTableStub());

		List<User> userList;

		// test delete all
		accessUsers.deleteAll();
		userList = accessUsers.getUserList();
		assert(userList.isEmpty());

		// test insert
		accessUsers.addUser(new User("Bob"));
		assertNotNull(accessUsers.getUserList());
		assertEquals(accessUsers.getUserList().size(),1);

		// search user
		assertNull(accessUsers.searchName("Jack"));
		assertNotNull(accessUsers.searchName("Bob"));

		// don't insert duplicate names
		assertNull(accessUsers.addUser(new User("Bob")));
		assertEquals(accessUsers.getUserList().size(),1);

		// multiple inserts
		accessUsers.addUser(new User("Jack"));
		accessUsers.addUser(new User("Sally"));
		accessUsers.addUser(new User("Frank"));
		assertEquals(accessUsers.getUserList().size(),4);

		// delete user that doesn't exist
		assertNull(accessUsers.deleteUser("NOT A PERSON"));

		// delete user
		User userJack = accessUsers.searchName("Jack");
		assertEquals(accessUsers.deleteUser("Jack"),userJack);
		assertEquals(accessUsers.getUserList().size(),3);

		System.out.println("Finished AccessUsers Tests");
	}
}
