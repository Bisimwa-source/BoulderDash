package contract;

import fr.exia.showboard.BoardFrame;

public interface IView {

	void displayMessage(final String message);
	
	void followMyPlayer();

	void updateView();

	BoardFrame getBoardFrame();

}
