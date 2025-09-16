package comp3350.studybuddy.objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Flashcard {
	private static int cardCounter = 1; // Ensure IDs start from 1

	private final String username;
	private String question;
	private String answer;
	private final int cardID;

	// Constructor for NEW flashcards (Auto-assigns ID)
	public Flashcard(@NonNull final String username, @NonNull final String question, @NonNull final String answer) {
		this.username = username;
		this.question = question;
		this.answer = answer;
		this.cardID = cardCounter++; // Assign and increment counter
	}

	public Flashcard(int cardID, @NonNull final String username, @NonNull final String question, @NonNull final String answer) {
		this.username = username;
		this.question = question;
		this.answer = answer;
		this.cardID = cardID;
	}

	@NonNull
	public String getUsername() {
		return username;
	}

	@NonNull
	public String getQuestion() {
		return question;
	}

	@NonNull
	public String getAnswer() {
		return answer;
	}

	public int getCardNum() {
		return cardID;
	}

	@NonNull
	@Override
	public String toString() {
		return
			"Flashcard{" +
			"ID=" + cardID +
			", User='" + username + "'" +
			", Question='" + question + "'" +
			", Answer='" + answer + "'" +
			"}";
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != Flashcard.class) return false;

		Flashcard c = (Flashcard)obj;
		return cardID == c.cardID || (username.equals(c.username) && question.equals(c.question) && answer.equals(c.answer));
	}

	public void setQuestion(String updateQuestion) {
		question = updateQuestion;
	}

	public void setAnswer(String updateAnswer) {
		answer = updateAnswer;
	}
}
