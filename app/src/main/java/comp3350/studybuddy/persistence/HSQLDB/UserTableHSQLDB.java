package comp3350.studybuddy.persistence.HSQLDB;

import androidx.annotation.NonNull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import comp3350.studybuddy.objects.User;
import comp3350.studybuddy.persistence.UserTable;

public class UserTableHSQLDB implements UserTable {
	private final String dbPath;

	public UserTableHSQLDB(final String dbPath) {
		this.dbPath = dbPath;
	}

	private Connection connection() throws SQLException {
		System.out.println("Connecting to database: " + dbPath);
		return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
	}

	private User fromResultSet(final ResultSet rs) throws SQLException {
		final String newUsername = rs.getString("USERNAME");
		return new User(newUsername);
	}

	@Override
	public void add(@NonNull User user) {
		System.out.println("Adding user: " + user.getUsername());
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("INSERT INTO PUBLIC.USERS (USERNAME) VALUES (?)");
			st.setString(1, user.getUsername());

			st.executeUpdate();
			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@NonNull
	public List<User> getAll() {
		System.out.println("Retrieving all users");
		final List<User> userList = new ArrayList<>();
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PUBLIC.USERS");

			final ResultSet rs = st.executeQuery();
			while (rs.next()) {
				final User record = fromResultSet(rs);
				userList.add(record);
			}

			rs.close();
			st.close();
			return userList;
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void deleteAll() {
		System.out.println("Deleting all users");
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("DELETE FROM PUBLIC.USERS");

			st.executeUpdate();
			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@NonNull
	public List<User> searchName(@NonNull String name) {
		System.out.println("Searching for user: " + name);
		final List<User> userList = new ArrayList<>();
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PUBLIC.USERS WHERE USERNAME = ?");
			st.setString(1, name);

			final ResultSet rs = st.executeQuery();

			while (rs.next()) {
				final User record = fromResultSet(rs);
				userList.add(record);
			}

			rs.close();
			st.close();
			return userList;
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void deleteUser(@NonNull String name) {
		System.out.println("Deleting user: " + name);
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("DELETE FROM PUBLIC.USERS WHERE USERNAME = ?");
			st.setString(1, name);

			st.executeUpdate();
			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}
}
