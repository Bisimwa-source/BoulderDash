package entity.mobile;

import entity.Permeability;
import entity.Sprite;

public class MonsterG extends Mobile{

	/** The Constant SPRITE. */
	private static final Sprite SPRITE = new Sprite('G', "greenMonster.png");

	/**
	 * Instantiates a new monster.
	 */
	public MonsterG() {
		super(SPRITE, Permeability.KILLABLE);
	}

	@Override
	public void win() {
		return;
	}

	@Override
	public int getDiamonds() {
		return 0;
	}
	/**
	 *Gets the diamonds
	 */
	@Override
	public void grabDiamond() {
		return;
	}
}