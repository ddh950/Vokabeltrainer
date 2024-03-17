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

	private final static String loadVocs = "SELECT * FROM daten.vokabeln";
	private final static String deleteFullVocables = "Truncate table daten.vokabeln";
	private final static String deleteOneVocable = "DELETE from daten.vokabeln WHERE german=";
	private final static String insertVocable = "INSERT INTO daten.vokabeln (`german`, `english`) VALUES (";

	public static DatabaseHandler databaseController = new DatabaseHandler();
	public Map<Integer, String> vocablesEnglish = new HashMap<>();
	public Map<Integer, String> vocablesGerman = new HashMap<>();
	private Connection con;
	private Statement statement;
	public static int counter = 0;


	public DatabaseHandler() {

		try {

			con = DriverManager.getConnection(url, user, password);
			statement = con.createStatement();
			System.out.println("Datenbank erfolgreich verbunden");

		}

		catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void setLoeschen() {

		try {
			
			statement.executeUpdate(deleteFullVocables);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		vocablesEnglish.clear();
		vocablesGerman.clear();

		System.gc();

	}


	public void loadVocs() {

		try {
		
			
			ResultSet gotSet = statement.executeQuery(loadVocs);
			
			
			while(gotSet.next()) {
				
				vocablesGerman.put(counter, gotSet.getString("german"));
				vocablesEnglish.put(counter, gotSet.getString("english"));
				counter++;
			}
			

		} catch (SQLException e) {

			System.out.println("NoVocsbeloaded");
			e.printStackTrace();
		} finally {

		}

	}

	public void insertdata(String engVoc, String gerVoc) {

		try {
			
			System.out.println(insertVocable+"'" +gerVoc +"',"+"'"+engVoc+"');");
			
			statement.executeUpdate(insertVocable+"'" +gerVoc +"',"+"'"+engVoc+"');");
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void removeData(int wert) {

		try {
			statement.executeUpdate(deleteOneVocable +"'" + vocablesGerman.get(wert) + "';");
			DatabaseHandler.counter--;

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
