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
	 * l'url de la base de données
	 */
	private static String URL = "jdbc:mysql://localhost/jpublankproject?autoReconnect=true&useSSL=false";
	/**
	 *identification de l'uilisateur
	 */
	private static String USER = "root";
	/**
	 * mot de passe 
	 */
	private static String PASSWD = "";

	private Connection connection = null;

	private DBConnection INSTANCE;

	/**
	 * méthode nous permettant de retourner les instances au niveau de la base de données.
	 */
	public DBConnection getInstance() {
		if (INSTANCE != null) {
			INSTANCE = new DBConnection();
		}
		return INSTANCE;
	}

	/**
	 * méthode permettant de nous conncter à la base de données.
	 */
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection(URL, USER, PASSWD);
	}
	
	/**
	 * méthode nous permettant de retourner la connectio avec la base de données
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * méthode nous permettant de retourner l'url de la base de données.
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * méthode nous permettant de retourner le nom de l'utilisateur au niveau de la base de données.
	 */
	public  String getUSER() {
		return USER;
	}

	/**
	 * méthode nous permettant de retourner le mot de passe mis au nveau de la base de données.
	 * @return
	 */
	public  String getPASSWD() {
		return PASSWD;
	}
}