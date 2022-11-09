package entity ;

import java.util.List;
import java.util.Observable;

/**
 * <h1>The Interface IMap.</h1>
 *
 * @author Laetitia
 * @version 0.1
 */

public interface IMap {

    /**
     * méthode pour retourner the width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * méthode pour retourner the height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * méthode pour retourner on the map XY.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return the on the road XY
     */
    IElement getOnTheMapXY(int x, int y);
    
    /**
     * méthode pour modifier on the Map XY 
     * @param element
     * @param x
     * @param y
     */
    void setOnTheMapXY(IElement element, final int x, final int y);

    /**
     * méthode pour modifier the mobile has changed.
     */
    void setMobileHasChanged();

    /**
     * méthode pour retourner the observable.
     *
     * @return the observable
     */
    Observable getObservable();

    boolean isCorrect();

    List<Integer> getHasChanged();


}