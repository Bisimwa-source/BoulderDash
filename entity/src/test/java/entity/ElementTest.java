package entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.mobile.Boulder;
import entity.mobile.MonsterG;
import entity.mobile.MonsterR;

public class ElementTest {
	//on crée les attributs;
static Boulder boulder;
static MonsterG monsterG;
static MonsterR monsterR;
	@BeforeClass
	// création des nouveaux objets avec le mot clé "new";
	public static void setUpBeforeClass() throws Exception {
	boulder = new Boulder();
	monsterG = new MonsterG();
	monsterR = new MonsterR();
	}
	@Test
	public void testgetPermeability() {
		//créer les attributs qui seront comparés;
		//fail("Not yet implemented");
		final Permeability test= Permeability.BOULDER;
		final Permeability test1= Permeability.KILLABLE;
		final Permeability test2= Permeability.KILLABLE;
		//ensuite on compare;
		assertEquals(test, this.boulder.getPermeability());
		assertEquals(test1, this.monsterG.getPermeability());
		assertEquals(test2, this.monsterR.getPermeability());
		
		//on verifie si le sprite(les images) qui est contenu dans entity n'est pas vide ;
		assertNotNull(this.boulder.getSprite());
		assertNotNull(this.monsterG.getSprite());
		assertNotNull(this.monsterR.getSprite());
		
	}

}
