
package business.externalinterfaces;

import middleware.DatabaseException;


public interface IShoppingCartSubsystem {
	void setCustomerProfile(ICustomerProfile customerProfile);
    void retrieveSavedCart() throws DatabaseException ;

}
