package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import comp3350.studybuddy.R;
import comp3350.studybuddy.business.AccessUsers;
import comp3350.studybuddy.objects.User;

public class CreateUserActivity extends AppCompatActivity {
	private AccessUsers accessUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// initialize logic class instance
		accessUsers = new AccessUsers();

		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_user_create);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

	public void buttonCreateUserOnClick(View v) {
		// get username from login box
		EditText usernameText = findViewById(R.id.login_textbox);
		String username = usernameText.getText().toString();

		User newUser = new User(username);
		User user = accessUsers.addUser(newUser);

		if (user != null) {
			// move to logged in state
			Intent loginIntent = new Intent(CreateUserActivity.this, HomeActivity.class);
			loginIntent.putExtra("loggedInUser", newUser.getUsername());
			CreateUserActivity.this.startActivity(loginIntent);
		} else {
			Toast toast = Toast.makeText(CreateUserActivity.this, getString(R.string.user_exists_error), Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
