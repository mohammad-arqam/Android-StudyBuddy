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

import comp3350.studybuddy.business.AccessFlashcards;
import comp3350.studybuddy.objects.Flashcard;

import comp3350.studybuddy.R;

public class ViewFlashcardsActivity extends AppCompatActivity {
	private List<Flashcard> flashcardList;

	private void createList() {
		String loggedInUser = getIntent().getStringExtra("loggedInUser");

		// If we managed to get here with a null user, just return back to the user select screen
		if (loggedInUser == null) {
			Intent selectUser = new Intent(ViewFlashcardsActivity.this, SelectUserActivity.class);
			ViewFlashcardsActivity.this.startActivity(selectUser);
			return;
		}

		// Get list of Flashcards from database
		AccessFlashcards accessFlashcards = new AccessFlashcards();
		flashcardList = accessFlashcards.getUsersFlashcards(loggedInUser);

		// If not empty, populate list, else display no flashcards
		if (!flashcardList.isEmpty()) {
			// Convert list of objects into view items
			ArrayAdapter<Flashcard> flashcardArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, flashcardList) {
				// Override getView function to specify how to convert Flashcard objects to view objects
				@Override
				@NonNull
				public View getView(int position, View convertView, @NonNull ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					TextView text1 = view.findViewById(android.R.id.text1);
					TextView text2 = view.findViewById(android.R.id.text2);

					text1.setText(flashcardList.get(position).getQuestion());
					text2.setText(flashcardList.get(position).getAnswer());
					return view;
				}
			};

			// Find list view by id
			ListView listView = findViewById(R.id.listFlashcards);
			// Populate with data from list of objects
			listView.setAdapter(flashcardArrayAdapter);
			// View each Flashcard in the list independently
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Flashcard selectedFlashcard = flashcardList.get(position);

					Intent intent = new Intent(ViewFlashcardsActivity.this, FlashcardItem.class);
					intent.putExtra("question", selectedFlashcard.getQuestion());
					intent.putExtra("answer", selectedFlashcard.getAnswer());
					intent.putExtra("loggedInUser", loggedInUser);
					intent.putExtra("flashCardID", selectedFlashcard.getCardNum());
					startActivity(intent);
				}
			});
		} else {
			// Set listview to null if no cards remaining
			ListView listView = findViewById(R.id.listFlashcards);
			listView.setAdapter(null);
			Toast toast = Toast.makeText(ViewFlashcardsActivity.this, getString(R.string.no_flashcards), Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_view_flashcards);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		createList();
	}

	// On resume update userList
	public void onResume() {
		super.onResume();

		createList();
	}
}
