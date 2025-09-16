package comp3350.studybuddy.Utilities;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import comp3350.studybuddy.application.Main;

public class TestUtilities {
	private static final File DB_SRC = new File("src/main/assets/db/SC.script");

	public static File copyDB() throws IOException {
		final File target = File.createTempFile("temp-db", ".script");
		Files.copy(DB_SRC, target);
		Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
		return target;
	}
}
