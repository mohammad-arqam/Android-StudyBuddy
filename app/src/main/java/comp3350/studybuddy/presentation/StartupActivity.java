package comp3350.studybuddy.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.studybuddy.application.Main;

/**
 * This sets up the database
 */
public class StartupActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		copyDatabaseToDevice();

		Intent userSelectIntent = new Intent(StartupActivity.this, SelectUserActivity.class);
		StartupActivity.this.startActivity(userSelectIntent);
	}

	// From HomeActivity.java in sample project
	private void copyDatabaseToDevice() {
		final String DB_PATH = "db";

		String[] assetNames;
		Context context = getApplicationContext();
		File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
		AssetManager assetManager = getAssets();

		try {
			assetNames = assetManager.list(DB_PATH);
			if (assetNames == null) {
				System.err.println("DB Path appears to be empty");
			}

			for (int i = 0; i < assetNames.length; i++) {
				assetNames[i] = DB_PATH + "/" + assetNames[i];
			}

			copyAssetsToDirectory(assetNames, dataDirectory);

			// Set the full database path
			String dbPath = dataDirectory.toString() + "/" + "SC";
			Main.setDBPathName(dbPath);
		} catch (final IOException ioe) {
			Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
		}
	}

	// From HomeActivity.java in sample project
	public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
		AssetManager assetManager = getAssets();

		for (String asset : assets) {
			String[] components = asset.split("/");
			String copyPath = directory.toString() + "/" + components[components.length - 1];

			char[] buffer = new char[1024];
			int count;

			File outFile = new File(copyPath);

			if (!outFile.exists()) {
				InputStreamReader in = new InputStreamReader(assetManager.open(asset));
				FileWriter out = new FileWriter(outFile);

				count = in.read(buffer);
				while (count != -1) {
					out.write(buffer, 0, count);
					count = in.read(buffer);
				}

				out.close();
				in.close();
			}
		}
	}
}
