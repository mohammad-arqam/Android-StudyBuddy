package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import comp3350.studybuddy.R;
import comp3350.studybuddy.business.AccessFlashcards;
import comp3350.studybuddy.objects.Flashcard;

public class FlashcardItem extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashcard_item);

		// Ensure IDs match what's in activity_flashcard_item.xml
		TextView textQuestion = findViewById(R.id.textQuestion);
		TextView textAnswer = findViewById(R.id.textAnswer);

		// Get data from Intent
		String question = getIntent().getStringExtra("question");
		String answer = getIntent().getStringExtra("answer");

		// Set the text, ensure no null values
		textQuestion.setText(question != null ? question : "No question available");
		textAnswer.setText(answer != null ? answer : "No answer available");
	}

	public void buttonDeleteFlashcardOnClick(View v) {
		int flashcardID = getIntent().getIntExtra("flashCardID", -1);
		AccessFlashcards accessFlashcards = new AccessFlashcards();
		Flashcard currFlashcard = accessFlashcards.getCard(flashcardID);
		if (currFlashcard != null) {
			accessFlashcards.deleteFlashcard(currFlashcard);
			Toast toast = Toast.makeText(FlashcardItem.this, getString(R.string.flashcard_deleted), Toast.LENGTH_SHORT);
			toast.show();
		} else {
			Toast toast = Toast.makeText(FlashcardItem.this, "Error deleting flashcard: Not Found", Toast.LENGTH_SHORT);
			toast.show();
		}
		finish();
	}

	public void buttonEditFlashcardOnClick(View v) {
		Intent intent = new Intent(this, EditFlashcardActivity.class);
		intent.putExtra("flashCardID", getIntent().getIntExtra("flashCardID", -1));
		startActivity(intent);
		finish();
	}
}
