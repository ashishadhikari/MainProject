/*
 * Created on Mar 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package business.externalinterfaces;

import middleware.DatabaseException;
import business.Quantity;
import business.util.TwoKeyHashMap;

public interface IProductSubsystem {
	public TwoKeyHashMap<String,String,IProductFromDb> getProductTable() throws DatabaseException;
	public void readQuantityAvailable(String prodName, Quantity quantity) throws DatabaseException;
}
