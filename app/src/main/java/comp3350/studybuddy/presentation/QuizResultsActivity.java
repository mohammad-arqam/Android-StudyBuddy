package comp3350.studybuddy.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.studybuddy.R;

public class QuizResultsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_results);

		TextView resultsTextView = findViewById(R.id.resultsTextView);

		Intent intent = getIntent();
		int correctAnswers = intent.getIntExtra("correctAnswers", 0);
		int totalQuestions = intent.getIntExtra("totalQuestions", 0);
		String loggedInUser = intent.getStringExtra("loggedInUser"); // Receive it

		resultsTextView.setText("You got " + correctAnswers + " out of " + totalQuestions + " correct!");

		Button returnHomeButton = findViewById(R.id.returnHomeButton);
		returnHomeButton.setOnClickListener(v -> {
			finish();
		});
	}
}
