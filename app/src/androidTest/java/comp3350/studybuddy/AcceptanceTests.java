package comp3350.studybuddy;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.studybuddy.presentation.StartupActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTests {
    @Rule
    public ActivityScenarioRule<StartupActivity> UsersTestRule = new ActivityScenarioRule<>(StartupActivity.class);

    @Test
    public void createUser() {
		// click create user button
		onView(withId(R.id.createUserButton)).perform(click());

		// type username into text box
		onView(withId(R.id.login_textbox)).perform(typeText("TestUser1"));
		Espresso.closeSoftKeyboard();

		// click create user
		onView(withId(R.id.createUserButton)).perform(click());

		//go back to home page NOTE: pressBack() sometimes fails!
		Espresso.pressBack();
		Espresso.pressBack();

		// verify user was added NOTE: position in list hard coded
		onData(anything()).inAdapterView(withId(R.id.listUsers)).atPosition(4).perform(click());
		onView(withId(R.id.loginButton)).perform(click());
		onView(withId(R.id.welcome_message)).check(matches(withText("Hello, TestUser1!")));
    }

	@Test
	public void addFlashcard() {
		// click create user button
		onView(withId(R.id.createUserButton)).perform(click());

		// type username into text box
		onView(withId(R.id.login_textbox)).perform(typeText("TestUser2"));
		Espresso.closeSoftKeyboard();

		// click create user
		onView(withId(R.id.createUserButton)).perform(click());

		// click add flashcard
		onView(withId(R.id.addFlashcardButton)).perform(click());

		// type question and answer
		onView(withId(R.id.createFlashcardQuestion_textbox)).perform(typeText("testQuestion"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardAnswer_textbox)).perform(typeText("testAnswer"));
		Espresso.closeSoftKeyboard();

		// click create flashcard button
		onView(withId(R.id.createFlashcardButton)).perform(click());

		//go back to home page, NOTE: pressBack() sometimes fails!
		Espresso.pressBack();

		// click view flashcard
		onView(withId(R.id.viewFlashcardsButton2)).perform(click());

		// enter edit flashcard and check question and answer matches
		onData(anything()).inAdapterView(withId(R.id.listFlashcards)).atPosition(0).perform(click());

		onView(withId(R.id.textQuestion)).check(matches(withText("testQuestion")));
		onView(withId(R.id.textAnswer)).check(matches(withText("testAnswer")));

	}

	@Test
	public void deleteFlashcard() {
		// Create a new user
		onView(withId(R.id.createUserButton)).perform(click());
		onView(withId(R.id.login_textbox)).perform(typeText("TestUser3"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createUserButton)).perform(click());

		// Add a flashcard
		onView(withId(R.id.addFlashcardButton)).perform(click());
		onView(withId(R.id.createFlashcardQuestion_textbox)).perform(typeText("deleteQuestion"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardAnswer_textbox)).perform(typeText("deleteAnswer"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardButton)).perform(click());
		Espresso.pressBack();

		// View flashcards
		onView(withId(R.id.viewFlashcardsButton2)).perform(click());

		// Select flashcard to delete
		onData(anything()).inAdapterView(withId(R.id.listFlashcards)).atPosition(0).perform(click());

		// Delete the flashcard
		onView(withId(R.id.DeleteFlashcardButton)).perform(click());

		// Verify flashcard is deleted
		onView(withId(R.id.listFlashcards)).check(matches(hasChildCount(0)));

	}

	@Test
	public void editFlashcard() {
		// Create a new user
		onView(withId(R.id.createUserButton)).perform(click());
		onView(withId(R.id.login_textbox)).perform(typeText("TestUser4"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createUserButton)).perform(click());

		// Add a flashcard
		onView(withId(R.id.addFlashcardButton)).perform(click());
		onView(withId(R.id.createFlashcardQuestion_textbox)).perform(typeText("originalQuestion"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardAnswer_textbox)).perform(typeText("originalAnswer"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardButton)).perform(click());

		Espresso.pressBack();

		// View flashcards
		onView(withId(R.id.viewFlashcardsButton2)).perform(click());

		// Select flashcard to edit
		onData(anything()).inAdapterView(withId(R.id.listFlashcards)).atPosition(0).perform(click());

		// Click edit flashcard
		onView(withId(R.id.EditFlashcardButton)).perform(click());

		// Change the question and answer
		onView(withId(R.id.editQuestion)).perform(clearText(), typeText("UpdatedQuestion"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.editAnswer)).perform(clearText(), typeText("UpdatedAnswer"));
		Espresso.closeSoftKeyboard();

		// Save changes
		onView(withId(R.id.buttonSave)).perform(click());

		// Verify changes
		onData(anything()).inAdapterView(withId(R.id.listFlashcards)).atPosition(0).perform(click());
		onView(withId(R.id.textQuestion)).check(matches(withText("UpdatedQuestion")));
		onView(withId(R.id.textAnswer)).check(matches(withText("UpdatedAnswer")));
	}
	@Test
	public void quizProvidesGradeOnCompletion() {
		// Create a new user
		onView(withId(R.id.createUserButton)).perform(click());
		onView(withId(R.id.login_textbox)).perform(typeText("QuizUser"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createUserButton)).perform(click());

		// Add a flashcard for the user
		onView(withId(R.id.addFlashcardButton)).perform(click());
		onView(withId(R.id.createFlashcardQuestion_textbox)).perform(typeText("What is 2 + 2?"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardAnswer_textbox)).perform(typeText("4"));
		Espresso.closeSoftKeyboard();
		onView(withId(R.id.createFlashcardButton)).perform(click());
		Espresso.pressBack(); // Go back to HomeActivity

		// Start the quiz
		onView(withId(R.id.startQuizButton)).perform(click());

		// Enter number of questions as 1 in the dialog
		onView(withId(R.id.dialog_question_count_input)).perform(typeText("1"));
		Espresso.closeSoftKeyboard();
		onView(withText("Start")).perform(click());;

		// Enter the correct answer
		onView(withId(R.id.quizFlashcardAnswer_textbox)).perform(typeText("4"));
		Espresso.closeSoftKeyboard();

		// Click "Next Question"
		onView(withId(R.id.nextButton)).perform(click());

		// Verify the results screen displays the correct grade
		onView(withId(R.id.resultsTextView)).check(matches(withText("You got 1 out of 1 correct!")));
	}
}
