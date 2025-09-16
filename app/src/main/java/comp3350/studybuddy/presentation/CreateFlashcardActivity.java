package comp3350.studybuddy.presentation;

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
import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.business.AccessFlashcards;

public class CreateFlashcardActivity extends AppCompatActivity {
	private AccessFlashcards accessFlashcards;
	private String loggedInUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// initialize logic class instance
		accessFlashcards = new AccessFlashcards();

		loggedInUser = getIntent().getStringExtra("loggedInUser");

		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_create_flashcard);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

	public void buttonCreateFlashCardOnClick(View v) {
		// get question and answer text from text boxes
		EditText questionText = findViewById(R.id.createFlashcardQuestion_textbox);
		String question = questionText.getText().toString();

		EditText answerText = findViewById(R.id.createFlashcardAnswer_textbox);
		String answer = answerText.getText().toString();

		// Validate input
		if (question.isEmpty() || answer.isEmpty()) {
			Toast.makeText(this, getString(R.string.flashcard_empty_values), Toast.LENGTH_SHORT).show();
			return;
		}

		Flashcard newFlashcard = new Flashcard(loggedInUser, question, answer);
		Flashcard result = accessFlashcards.addFlashcard(newFlashcard);
		if (result == null) {
			Toast.makeText(this, getString(R.string.duplicate_flashCard) , Toast.LENGTH_SHORT).show();
			return;
		}

		Toast toast = Toast.makeText(CreateFlashcardActivity.this, getString(R.string.flashcard_created), Toast.LENGTH_SHORT);
		toast.show();
	}
}
