package comp3350.studybuddy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studybuddy.business.AccessFlashcardsTest;
import comp3350.studybuddy.business.AccessUsersTest;
import comp3350.studybuddy.business.QuizManagerTest;
import comp3350.studybuddy.objects.FlashcardTest;
import comp3350.studybuddy.objects.UserTest;
import comp3350.studybuddy.persistence.FlashcardTableTest;
import comp3350.studybuddy.persistence.UserTableTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AccessUsersTest.class,
	AccessFlashcardsTest.class,
	FlashcardTest.class,
	UserTest.class,
	UserTableTest.class,
	FlashcardTableTest.class,
	QuizManagerTest.class
})
public class AllTests {
}
