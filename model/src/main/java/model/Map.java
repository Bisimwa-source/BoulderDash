 package model;

import java.io.BufferedReader;

import entity.IElement;
import entity.IMap;
import entity.mobile.MobileElementsFactory;
import entity.motionless.MotionlessElementsFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * <h1>The Map Class.</h1>
 *
 * @author Laetitia
 * @version 0.1
 */


class Map extends Observable implements IMap {

	/** La longueur. */
	private int width;

	/** La largeur. */
	private int height;

	/** element sur la carte. */
	private IElement[][] onTheMap;

	private boolean isCorrect = true;

	private List<Integer> hasChanged = new ArrayList<Integer>();

	/**
	 * Instantier une nouvelle carte avec le contenu de the file fileName.
	 *
	 * @param fileName 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Map(final String fileName) throws IOException {
		super();
		this.loadFile(fileName);
	}

	/**
	 * chargement du dossier.
	 *
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void loadFile(final String fileName) throws IOException {
		final BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line;

		try {
			line = buffer.readLine();
			this.setWidth(Integer.parseInt(line));
			line = buffer.readLine();
			this.setHeight(Integer.parseInt(line));
		} catch (Exception | Error e) {
			isCorrect = false;
		}

		if (isCorrect) {
			this.onTheMap = new IElement[this.getWidth()][this.getHeight()];

			int y = 0;
			line = buffer.readLine();
			while (line != null) {
				if (line.toCharArray().length == this.width) {
					for (int x = 0; x < line.toCharArray().length; x++) {
						if (line.toCharArray()[x] == 'R' || line.toCharArray()[x] == 'O' || line.toCharArray()[x] == 'G' || line.toCharArray()[x] == '*') {
							this.setOnTheMapXY(MobileElementsFactory.getFromFileSymbol(line.toCharArray()[x]), x, y);
						} else {
							this.setOnTheMapXY(MotionlessElementsFactory.getFromFileSymbol(line.toCharArray()[x]), x, y);
						}
					}
				} else {
					this.isCorrect = false;
				}
				line = buffer.readLine();
				y++;
			}
			buffer.close();

			if (y != this.height) {
				this.isCorrect = false;
			}
		}
	}

	/*
	 * méthode nous permettant de retourner un  élement on the map
	 *
	 */
	@Override
	public final IElement getOnTheMapXY(final int x, final int y) {
		return this.onTheMap[x][y];
	}

	/**
	 * méthode nous permettant de modifier the on the map XY.
	 *
	 * @param element the element
	 * @param x       the x
	 * @param y       the y
	 */
	public void setOnTheMapXY(IElement element, int x, int y) {
		this.onTheMap[x][y] = element;
		this.hasChanged.add(x);
		this.hasChanged.add(y);

	}

	/*
	 * méthode nous permettant de modifier  mobile has changed
	 *
	 */
	@Override
	public final void setMobileHasChanged() {
		this.setChanged();
		this.notifyObservers();
	}

	/*
	 * méthode nous permettant de retourner la largeur
	 * 
	 */
	@Override
	public final int getWidth() {
		return this.width;
	}

	/**
	 * méthode nous permettant de modifier la largeur.
	 *
	 * @param width the new width
	 */
	private void setWidth(final int width) {
		this.width = width;
	}

	/*
	 * méthode nous permettant de retourner la hauteur
	 * 
	 */
	@Override
	public final int getHeight() {
		return this.height;
	}

	/**
	 * méthode nous permettant de modifier la hauteur.
	 *
	 * @param height the new height
	 */
	private void setHeight(final int height) {
		this.height = height;
	}

	/*
	 * méthode nous permettant de retourner l'objet observable
	 * 
	 */
	@Override
	public Observable getObservable() {
		return this;
	}

	public boolean isCorrect() {
		return isCorrect;
	}


	public List<Integer> getHasChanged() {
		return hasChanged;
	}

}
