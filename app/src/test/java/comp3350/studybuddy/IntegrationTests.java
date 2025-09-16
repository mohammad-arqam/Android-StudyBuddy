package comp3350.studybuddy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studybuddy.business.AccessFlashcardsIntegrationTest;
import comp3350.studybuddy.business.AccessUsersIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AccessUsersIntegrationTest.class,
	AccessFlashcardsIntegrationTest.class,
})
public class IntegrationTests {
}
