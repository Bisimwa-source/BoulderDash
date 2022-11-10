package model.DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DBConnectionTest {

//on fait appel aux attributs de la classe DBconnection dans le package model.DAO;
	
	final String URL = "jdbc:mysql://localhost/jpublankproject1?autoReconnect=true&useSSL=false";
	
	final String USER = "root";

	final String PASSWD = "";
	// on crée un attribut instance de type base de donnée;
	DBConnection instance;

	@Before
	public void setUp() throws Exception {
		//on crée un  objet DBConnection;
		instance=new DBConnection();
	}

	@Test
	// nous allons utiliser la methode testGetURL pour faire la comparaison;
	public void testGetURL() {
		
		// on fait appel à la méthode assertEquals pour comparer l'adresse URL  sur la base de donnée; 
		assertEquals(this.URL,this.instance.getURL());
	}

	@Test
	public void testGetUSER() {
		//on compare aussi le USER à celui de la base de donnée; 
		//fail("Not yet implemented");
		assertEquals(this.USER,this.instance.getUSER());
	}

	@Test
	public void testGetPASSWD() {
		//fail("Not yet implemented");
		// on comparre aussi le mot de passe sur la base des donnée; 
		assertEquals(this.PASSWD,this.instance.getPASSWD());
	}

}
