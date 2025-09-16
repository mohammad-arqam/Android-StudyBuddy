package comp3350.studybuddy.persistence.HSQLDB;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import comp3350.studybuddy.objects.Flashcard;
import comp3350.studybuddy.persistence.FlashcardTable;

public class FlashcardTableHSQLDB implements FlashcardTable {
	private final String dbPath;

	public FlashcardTableHSQLDB(final String dbPath) {
		this.dbPath = dbPath;
	}

	private Connection connection() throws SQLException {
		System.out.println("Connecting to database " + dbPath);
		return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true;hsqldb.lock_file=false", "SA", "");
	}

	private Flashcard fromResultSet(final ResultSet rs) throws SQLException {
		return new Flashcard(
			rs.getInt("CARDID"),
			rs.getString("USERNAME"),
			rs.getString("QUESTION"),
			rs.getString("ANSWER")
		);
	}

	@Override
	public void add(@NonNull Flashcard newCard) {
		System.out.println("Adding card: " + newCard);

		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement(
				"INSERT INTO PUBLIC.FLASHCARDS (USERNAME, QUESTION, ANSWER) VALUES (?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS
			);

			st.setString(1, newCard.getUsername());
			st.setString(2, newCard.getQuestion());
			st.setString(3, newCard.getAnswer());

			int affectedRows = st.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Failed to insert flashcard.");
			}

			try (ResultSet generatedKeys = st.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int newID = generatedKeys.getInt(1);
					System.out.println("Flashcard added with ID: " + newID);
				} else {
					throw new SQLException("Failed to retrieve flashcard ID.");
				}
			}

			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@NonNull
	public List<Flashcard> getAll() {
		System.out.println("Retrieving all flashcards");
		final List<Flashcard> flashcardList = new ArrayList<>();
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PUBLIC.FLASHCARDS");

			final ResultSet rs = st.executeQuery();
			while (rs.next()) {
				flashcardList.add(fromResultSet(rs));
			}

			rs.close();
			st.close();
			return flashcardList;
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public void deleteAll() {
		System.out.println("Deleting all flashcards");
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("DELETE FROM PUBLIC.FLASHCARDS");
			st.executeUpdate();
			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@NonNull
	public List<Flashcard> getUsersFlashcards(@NonNull String username) {
		System.out.println("Getting flashcards of user: " + username);
		final List<Flashcard> flashcardList = new ArrayList<>();
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PUBLIC.FLASHCARDS WHERE USERNAME = ?");
			st.setString(1, username);

			final ResultSet rs = st.executeQuery();
			while (rs.next()) {
				flashcardList.add(fromResultSet(rs));
			}

			rs.close();
			st.close();
			return flashcardList;
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void deleteCard(@NonNull Flashcard card) {
		System.out.println("Deleting card: " + card);
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("DELETE FROM PUBLIC.FLASHCARDS WHERE CARDID = ?");
			st.setInt(1, card.getCardNum());

			int affectedRows = st.executeUpdate();
			st.close();

			if (affectedRows == 0) {
				System.err.println("No flashcard found with ID: " + card.getCardNum());
			} else {
				System.out.println("Deleted flashcard with ID: " + card.getCardNum());
			}
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Nullable
	@Override
	public Flashcard getCard(int num) {
		System.out.println("Getting card number: " + num);
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PUBLIC.FLASHCARDS WHERE CARDID = ?");
			st.setInt(1, num);

			final ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Flashcard flashcard = fromResultSet(rs);
				rs.close();
				st.close();
				return flashcard;
			} else {
				rs.close();
				st.close();
				System.err.println("Flashcard number " + num + " was not found");
				return null; // No matching flashcard found
			}
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void updateCard(@NonNull Flashcard updatedCard) {
		try (final Connection c = connection()) {
			final PreparedStatement st = c.prepareStatement("UPDATE PUBLIC.FLASHCARDS SET QUESTION = ?, ANSWER = ? WHERE CARDID = ?");
			st.setString(1, updatedCard.getQuestion());
			st.setString(2, updatedCard.getAnswer());
			st.setInt(3, updatedCard.getCardNum());

			int rows = st.executeUpdate();
			if (rows == 0) {
				System.err.println("Card number " + updatedCard.getCardNum() + " not found in database");
			}

			st.close();
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}
}
