package model;

import java.io.IOException;

import contract.IModel;
import entity.IMap;
import entity.mobile.IMobile;
import entity.mobile.MyPlayer;

/**
 * <h1>The Class BoulderDashModel.</h1>
 *
 * @author Laetitia
 *
 */
/**
 * @author User
 *
 */
public class BoulderDashModel implements IModel {

	/** attribut privé map. */
	private IMap map;

	/** attribut privé MyPlayer. */
	private IMobile myPlayer;

	/**
	 * instanciation des objets map et MyPlayer au niveau du constructeur BoulderDashModel.
	 *
	 * @param myPlayerStartX défini comme abscisse lors du déplacement de Myplayer
	 * @param myPlayerStartY défini comme ordonnée lors du déplacement de Myplayer
	 * @throws IOException signales qu'une exception s'est passée (I/O exception) .
	 */
	public BoulderDashModel(final String mapFile, final int myPlayerStartX, final int myPlayerStartY) {
		try {
			this.setMap(new Map(mapFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.setMyPlayer(new MyPlayer(myPlayerStartX, myPlayerStartY, this.getMap()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *méthode permettant de retourner la carte 
	 */
	@Override
	public final IMap getMap() {
		return this.map;
	}

	/**
	 *  méthode permettant de modifier la carte.
	 *
	 * @param map 
	 */
	private void setMap(final IMap map) {
		this.map = map;
	}

	/**
	 * méthode permettant retourner le joueur à partir de MyPayer.
	 */
	@Override
	public final IMobile getMyPlayer() {
		return this.myPlayer;
	}

	/**
	 * méthode permettant de modifier le joueur.
	 *
	 * @param myPlayer 
	 */
	private void setMyPlayer(final IMobile myPlayer) {
		this.myPlayer = myPlayer;
	}

}
