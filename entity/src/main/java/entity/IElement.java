package entity;


import fr.exia.showboard.ISquare;

/**
 *   <h1>The Interface IElement.</h1>
 * @author Laetitia
 *
 */
public interface IElement extends ISquare {
	/**
	 * méthode pour retourner the sprite.
	 *
	 * @return the sprite
	 */
	Sprite getSprite();

	/**
	 * méthode pour retourner  the permeability.
	 *
	 * @return the permeability
	 */
	Permeability getPermeability();

	Boolean getHasChanged();

	void setHasChanged(Boolean hasChanged);

}