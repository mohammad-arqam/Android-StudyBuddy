package comp3350.studybuddy.persistence;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import comp3350.studybuddy.objects.User;

public class UserTableStub implements UserTable {
	private final List<User> table = new ArrayList<>();

	public UserTableStub() {
		// Create the dummy data
		add(new User("Bob"));
		add(new User("Jack"));
		add(new User("Sally"));
		add(new User("Frank"));
	}

	public void add(@NonNull User user) {
		System.out.println("Adding user: " + user.getUsername());
		table.add(user);
	}

	@NonNull
	public List<User> getAll() {
		System.out.println("Retrieving all users");
		return new ArrayList<>(table);
	}

	public void deleteAll() {
		System.out.println("Deleting all users");
		table.clear();
	}

	@NonNull
	public List<User> searchName(@NonNull String name) {
		System.out.println("Searching for user: " + name);
		List<User> users = getAll();
		users.removeIf(u -> !u.getUsername().equals(name));
		return users;
	}

	public void deleteUser(@NonNull String name) {
		System.out.println("Deleting user: " + name);
		table.removeIf(u -> u.getUsername().equals(name));
	}
}
