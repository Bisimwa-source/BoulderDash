package model.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Laetitia
 *
 */
public class DBConnection {

	/**
	 * the database url
	 */
	private static String URL = "jdbc:mysql://localhost/jpublankproject?autoReconnect=true&useSSL=false";
	/**
	 * the user
	 */
	private static String USER = "root";
	/**
	 * the password 
	 */
	private static String PASSWD = "";

	private Connection connection = null;

	private DBConnection INSTANCE;

	/**
	 * Gets the instance
	 */
	public DBConnection getInstance() {
		if (INSTANCE != null) {
			INSTANCE = new DBConnection();
		}
		return INSTANCE;
	}

	/**
	 * connection à la base de données
	 */
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection(URL, USER, PASSWD);
	}
	
	/**
	 * méthode retournant la connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * méthode retournant l'URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * méthode retournant l'utilisateur 
	 */
	public  String getUSER() {
		return USER;
	}

	/**
	 * méthode retournant le mot de passe
	 * @return
	 */
	public  String getPASSWD() {
		return PASSWD;
	}
}