import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {

	private String url = "jdbc:mysql://localhost:3306/daten";
	private String user = "root";
	private String password = "";

	private final static String loadEnglischVocs = "SELECT wert FROM daten.englisch;";
	private final static String loadGermanVocs = "SELECT wert FROM daten.deutsch;";
	private final static String loadId = "SELECT id From daten.deutsch;";
	private final static String deleteEng = "Truncate table daten.englisch;";
	private final static String deleteGer = "Truncate table daten.deutsch;";
	private final static String deleteVocEng = "DELETE from daten.englisch WHERE id=";
	private final static String deleteVocGer = "DELETE from daten.deutsch WHERE id=";

	private final static ArrayList<Integer> ids = new ArrayList<>();
	int zaehler = 1;
	private int id;
	public static DatabaseHandler databaseController = new DatabaseHandler();
	public Map<Integer, String> vocablesEnglish = new HashMap<>();
	public Map<Integer, String> vocablesGerman = new HashMap<>();
	private Connection con;
	private Statement statement;
	private Statement statement2;
	private Statement statement3;

	public DatabaseHandler() {

		try {

			con = DriverManager.getConnection(url, user, password);
			System.out.println("Datenbank erfolgreich verbunden");

		}

		catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void setLoeschen() {

		try {
			statement = con.createStatement();
			statement.executeUpdate(deleteEng);
			statement.executeUpdate(deleteGer);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		vocablesEnglish.clear();
		vocablesGerman.clear();

		System.gc();

	}

	public int getid() {
		return id;
	}

	public void loadVocs() {

		try {
			statement = con.createStatement();
			statement2 = con.createStatement();
			statement3 = con.createStatement();
			ResultSet getSetGer = statement.executeQuery(loadGermanVocs);
			ResultSet getSetEng = statement2.executeQuery(loadEnglischVocs);
			ResultSet getId = statement3.executeQuery(loadId);
			System.out.println(ids.size());
			while (getId.next() && getSetGer.next() && getSetEng.next()) {

				vocablesGerman.put(getId.getInt("id"), getSetGer.getString("wert"));
				vocablesEnglish.put(getId.getInt("id"), getSetEng.getString("wert"));
				id = getId.getInt("id");
				ids.add(id);

			}

		} catch (SQLException e) {

			System.out.println("NoVocsbeloaded");
			e.printStackTrace();
		} finally {

		}

	}

	public void insertdata(String engVoc, String gerVoc) {

		try {
			statement = con.createStatement();
			statement.executeUpdate("Insert Into daten.deutsch (wert) values ('" + gerVoc + "');");
			statement.executeUpdate("Insert Into daten.englisch (wert) values ('" + engVoc + "');");
			if (ids.size() > 0) {
				System.out.println(ids.get((ids.size() - 1)) + 1);
				ids.add(ids.get((ids.size() - 1)) + 1);
			} else {

				statement2 = con.createStatement();
				ResultSet getId = statement3.executeQuery(loadId);

				while (getId.next()) {

					id = getId.getInt("id");
					System.out.println(id);
					ids.add(id);
				}

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void removeData(int id) {

		try {

			statement = con.createStatement();
			statement.executeUpdate(deleteVocEng + "'" + ids.get(id) + "';");
			statement.executeUpdate(deleteVocGer + "'" + ids.get(id) + "';");
			ids.remove(id);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
