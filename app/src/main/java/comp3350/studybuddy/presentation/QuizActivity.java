package comp3350.studybuddy.presentation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import comp3350.studybuddy.R;
import comp3350.studybuddy.business.QuizManager;
import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.business.AccessFlashcards;

public class QuizActivity extends AppCompatActivity {
	private QuizManager manager;
	private TextView questionTextView, answerTextView, questionProgressTextView;

	private EditText answerInput;

	private int correctAnswersCount = 0;
	private String loggedInUser;
	private boolean answerWasShown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_quiz);

		loggedInUser = getIntent().getStringExtra("loggedInUser");

		// If we managed to get here with a null user, just return back to the user select screen
		if (loggedInUser == null) {
			Intent selectUser = new Intent(QuizActivity.this, SelectUserActivity.class);
			QuizActivity.this.startActivity(selectUser);
			return;
		}

		AccessFlashcards accessFlashcards = new AccessFlashcards();

		manager = new QuizManager(accessFlashcards.getUsersFlashcards(loggedInUser));

		questionTextView = findViewById(R.id.questionTextView);
		answerTextView = findViewById(R.id.answerTextView);
		questionProgressTextView = findViewById(R.id.questionProgressTextView);
		Button showAnswerButton = findViewById(R.id.showAnswerButton);
		Button nextButton = findViewById(R.id.nextButton);
		Button endQuizButton = findViewById(R.id.endQuizButton);
		answerInput = findViewById(R.id.quizFlashcardAnswer_textbox);

		answerTextView.setVisibility(View.GONE);

		// Show input dialog for user to enter the number of questions
		promptUserForMaxQuestions();

		showAnswerButton.setOnClickListener(view -> {
			answerTextView.setVisibility(View.VISIBLE);
			answerWasShown = true;
		});

		nextButton.setOnClickListener(view -> nextFlashcard());

		// Handle End Quiz button click
		endQuizButton.setOnClickListener(view -> {
			Toast.makeText(this, "Quiz Ended Early!", Toast.LENGTH_SHORT).show();
			finish(); // Ends quiz and returns to HomeActivity
		});
	}

	// Prompt the user to enter the number of questions for the quiz
	private void promptUserForMaxQuestions() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter Number of Questions");

		final EditText input = new EditText(this);
		input.setId(R.id.dialog_question_count_input);
		input.setHint("Max: " + manager.getFlashcards().size());
		input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);

		builder.setPositiveButton("Start", null); // Set to null to handle manually

		builder.setNegativeButton("Cancel", (dialog, which) -> {
			Toast.makeText(this, "Quiz cancelled.", Toast.LENGTH_SHORT).show();
			finish(); // Exit the quiz if the user cancels
		});

		AlertDialog dialog = builder.create();

		builder.setCancelable(false); // Prevent dismissal without choosing

		dialog.setOnShowListener(d -> {
			Button startButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
			startButton.setOnClickListener(v -> {
				String inputText = input.getText().toString();
				int maxQuestions = inputText.isEmpty() ? manager.getFlashcards().size() : Integer.parseInt(inputText);

				if (maxQuestions < 1) {
					Toast.makeText(this, "Please enter a number greater than 0.", Toast.LENGTH_SHORT).show();
					return; // Do NOT dismiss the dialog if invalid input
				}

				manager.setMaxQuestions(maxQuestions);
				dialog.dismiss();

				// Start the quiz
				updateFlashcard();
			});
		});

		dialog.show();
	}

	private void updateFlashcard() {
		answerWasShown = false; // also reset whenever a new card is shown for edge cases
		Flashcard currentFlashcard = manager.getCurrentFlashcard();
		if (currentFlashcard != null) {
			questionTextView.setText(currentFlashcard.getQuestion());
			answerTextView.setText(currentFlashcard.getAnswer());
			answerTextView.setVisibility(View.GONE);

			// Update question progress
			questionProgressTextView.setText(manager.getCurrentIndex() + " / " + manager.getMaxQuestions());
		} else {
			// Quiz is complete, show results activity
			Intent intent = new Intent(QuizActivity.this, QuizResultsActivity.class);
			intent.putExtra("correctAnswers", correctAnswersCount);
			intent.putExtra("totalQuestions", manager.getMaxQuestions());
			intent.putExtra("loggedInUser", loggedInUser); // Pass logged in user
			startActivity(intent);
			finish();
		}
	}

	private void nextFlashcard() {
		String userAnswer = answerInput.getText().toString().trim();
		String correctAnswer = manager.getCurrentFlashcard().getAnswer().trim();

		if (answerWasShown) {
			Toast.makeText(this, "You revealed the answer. No points for this question.", Toast.LENGTH_SHORT).show();
		} else if (userAnswer.equalsIgnoreCase(correctAnswer)) {
			correctAnswersCount++;
			Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Incorrect. The correct answer was: " + correctAnswer, Toast.LENGTH_SHORT).show();
		}

		answerInput.setText(""); // Clear input field
		answerWasShown = false;  // Reset for next question

		manager.moveToNextFlashcard();
		updateFlashcard();
	}
}
