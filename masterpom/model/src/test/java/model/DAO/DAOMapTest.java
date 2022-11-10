package model.DAO;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DAOMapTest {
	//on crée l'attribut dao du type DAOMap;
	DAOMap dao;
	// on crée un attribut du type privé qui contiendra la carte map par défaut qui est dans le sprite et peut être ouvert sans base des données;
	private String monDossier="map.txt";
	
	// cette syntaxe est utilisé juste pour tracer et vérifier si le code fournit  lève des exceptions attendues ou non;
	@Test(expected = Exception.class)
	
	public void testApplyToMap()throws Exception {
		
		// verifier le dossier si il se trouve au niveau zero;
		this.dao.loadlevel(monDossier, "0");
	}

}
