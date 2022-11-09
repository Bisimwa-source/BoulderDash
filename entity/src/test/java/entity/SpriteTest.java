package entity;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import entity.mobile.Boulder;
import entity.mobile.Diamond;
import entity.mobile.MonsterG;
import entity.mobile.MonsterR;

public class SpriteTest {
	// on met  les attributs ;
	static Boulder boulder;
	static MonsterG monsterG;
	static MonsterR monsterR ;
	static Diamond diamond ;

	@BeforeClass
	// instanciation d'une classe (on crée les objets);
	public static void setUpBeforeClass() throws Exception {
		boulder = new Boulder();
		monsterG = new MonsterG();
		monsterR = new MonsterR();
		diamond  = new Diamond ();
	}

	@Test
	public void testGetImage() {
		//fail("Not yet implemented");
		// on crée des attributs qui nous permettrons de faire la comparaison;
		final String testboulder="boulder.png";
		final String testmonsterG="greenMonster.png";
		final String testmonsterR="redMonster.png";
		final String testdiamond="diamond.png";
		//assertEquals nous permettra de comparer chaque image qui se trouve dans le sprite;
		assertEquals(testboulder, this.boulder.getSprite().getImageName() );
		assertEquals(testmonsterG, this.monsterG.getSprite().getImageName());
		assertEquals(testmonsterR, this.monsterR.getSprite().getImageName());
		assertEquals(testdiamond, this.diamond.getSprite().getImageName());
	}

	@Test
	public void testGetConsoleImage() {
		//pour comparer le symbol par defaut au niveau de la console;
		//fail("Not yet implemented");
		final char nouvelleconsole= '*';
		assertEquals(nouvelleconsole,this.diamond.getSprite().getConsoleImage());
		final char nouvelleconsole1= 'O';
		assertEquals(nouvelleconsole1,this.boulder.getSprite().getConsoleImage());
		final char nouvelleconsole2= 'G';
		assertEquals(nouvelleconsole2,this.monsterG.getSprite().getConsoleImage());
		final char nouvelleconsole3= 'R';
		assertEquals(nouvelleconsole3,this.monsterR.getSprite().getConsoleImage());
		
	}

}
