package comp3350.studybuddy.objects;

import androidx.annotation.NonNull;

public class User {
	private final String username;

	public User(@NonNull final String newUsername) {
		this.username = newUsername;
	}

	@NonNull
	public String getUsername() {
		return username;
	}
}
