package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import comp3350.studybuddy.R;
import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.business.AccessFlashcards;

public class HomeActivity extends AppCompatActivity {
	private String loggedInUser;
	AccessFlashcards accessFlashcards = new AccessFlashcards();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_home);

		TextView welcomeMessage = findViewById(R.id.welcome_message);
		loggedInUser = getIntent().getStringExtra("loggedInUser");

		welcomeMessage.setText(getString(R.string.user_welcome, loggedInUser));
	}

	public void buttonAddFlashCardOnClick(View v) {
		Intent createFlashCard = new Intent(HomeActivity.this, CreateFlashcardActivity.class);
		createFlashCard.putExtra("loggedInUser", loggedInUser);
		HomeActivity.this.startActivity(createFlashCard);
	}

	public void buttonViewFlashcardOnClick(View v) {
		Intent viewFlashCard = new Intent(HomeActivity.this, ViewFlashcardsActivity.class);
		viewFlashCard.putExtra("loggedInUser", loggedInUser);
		HomeActivity.this.startActivity(viewFlashCard);
	}

	public void buttonStartQuizOnClick(View v) {
		List<Flashcard> flashcards = accessFlashcards.getUsersFlashcards(loggedInUser);
		if (!flashcards.isEmpty()) {
			// Flashcards exist, proceed to start quiz
			Intent quizIntent = new Intent(HomeActivity.this, QuizActivity.class);
			quizIntent.putExtra("loggedInUser", loggedInUser);
			HomeActivity.this.startActivity(quizIntent);
		} else {
			// No flashcards found for the user
			Toast.makeText(HomeActivity.this, getString(R.string.no_flashcards_quiz_start_error), Toast.LENGTH_SHORT).show();
		}
	}
}
