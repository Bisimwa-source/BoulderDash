package contract;
import entity.IMap;
import entity.mobile.IMobile;
public interface IModel {

	IMap getMap();

    IMobile getMyPlayer();

}
