package comp3350.studybuddy.application;

import android.util.Log;

public class Main {
	private static String dbPathName;

	public static void setDBPathName(final String path) {
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		dbPathName = path;
	}

	public static String getDBPathName() {
		return dbPathName;
	}
}
