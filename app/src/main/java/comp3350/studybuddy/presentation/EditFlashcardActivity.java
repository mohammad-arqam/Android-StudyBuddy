package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import comp3350.studybuddy.R;
import comp3350.studybuddy.business.AccessFlashcards;
import comp3350.studybuddy.objects.Flashcard;

public class EditFlashcardActivity extends AppCompatActivity {
	private EditText editQuestion, editAnswer;
	private int flashcardID;
	private AccessFlashcards accessFlashcards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_flashcard);

		editQuestion = findViewById(R.id.editQuestion);
		editAnswer = findViewById(R.id.editAnswer);
		Button btnSave = findViewById(R.id.buttonSave);

		accessFlashcards = new AccessFlashcards();

		// Get flashcard details
		flashcardID = getIntent().getIntExtra("flashCardID", -1);
		Flashcard currFlashcard = accessFlashcards.getCard(flashcardID);

		if (currFlashcard != null) {
			editQuestion.setText(currFlashcard.getQuestion());
			editAnswer.setText(currFlashcard.getAnswer());
		} else {
			Toast.makeText(this, "Error loading flashcard", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	public void buttonSaveFlashcardOnClick(View v) {
		String newQuestion = editQuestion.getText().toString().trim();
		String newAnswer = editAnswer.getText().toString().trim();

		if (!newQuestion.isEmpty() && !newAnswer.isEmpty()) {
			Flashcard currFlashcard = accessFlashcards.getCard(flashcardID);

			if (currFlashcard != null) {
				currFlashcard.setQuestion(newQuestion);
				currFlashcard.setAnswer(newAnswer);

				accessFlashcards.updateFlashcard(currFlashcard); // Persist update

				// Notify previous activity that data changed
				Intent resultIntent = new Intent();
				setResult(RESULT_OK, resultIntent);

				Toast.makeText(this, "Flashcard updated", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(this, "Flashcard not found", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Both fields must be filled", Toast.LENGTH_SHORT).show();
		}
	}

	public void buttonCancelFlashcardOnClick(View v) {
		finish();
	}
}
