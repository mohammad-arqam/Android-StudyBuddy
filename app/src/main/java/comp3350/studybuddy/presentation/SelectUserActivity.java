package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import comp3350.studybuddy.R;
import comp3350.studybuddy.business.AccessUsers;
import comp3350.studybuddy.objects.User;

public class SelectUserActivity extends AppCompatActivity {
	private AccessUsers accessUsers;
	private List<User> userList;
	private User loggedInUser;

	private void createList() {
		// Get list of users from database
		userList = accessUsers.getUserList();

		// reset selected user
		loggedInUser = null;

		if (!userList.isEmpty()) {
			// Convert list of objects into view items
			ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, userList) {
				// Override getView function to specify how to convert User objects to view objects
				@Override
				@NonNull
				public View getView(int position, View convertView, @NonNull ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					TextView text1 = view.findViewById(android.R.id.text1);
					text1.setText(userList.get(position).getUsername());
					return view;
				}
			};

			// Find list view by id
			final ListView listView = findViewById(R.id.listUsers);
			// Populate with data from list of objects
			listView.setAdapter(userArrayAdapter);

			// Handle user selection
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
					loggedInUser = userList.get(position);
				}
			});
		} else {
			Toast toast = Toast.makeText(SelectUserActivity.this, getString(R.string.no_users), Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		accessUsers = new AccessUsers();

		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_user_select);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		createList();
	}

	// on resume update userList
	public void onResume() {
		super.onResume();

		createList();
	}

	public void buttonCreateUserOnClick(View v) {
		Intent userCreateIntent = new Intent(SelectUserActivity.this, CreateUserActivity.class);
		SelectUserActivity.this.startActivity(userCreateIntent);
	}

	public void buttonLogInOnClick(View v) {
		if (loggedInUser != null) { // A user must be selected
			Intent loginIntent = new Intent(SelectUserActivity.this, HomeActivity.class);
			loginIntent.putExtra("loggedInUser", loggedInUser.getUsername());
			SelectUserActivity.this.startActivity(loginIntent);
		} else {
			Toast.makeText(SelectUserActivity.this, getString(R.string.no_selection_login_attempt), Toast.LENGTH_SHORT).show();
		}
	}
}
