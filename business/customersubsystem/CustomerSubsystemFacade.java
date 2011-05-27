
package business.customersubsystem;

import java.util.Collections;
import java.util.List;

import middleware.DatabaseException;
import middleware.EBazaarException;
import business.RuleException;
import business.externalinterfaces.IAddress;
import business.externalinterfaces.ICustomerSubsystem;
import business.externalinterfaces.IOrder;
import business.externalinterfaces.IOrderSubsystem;
import business.externalinterfaces.IRules;
import business.externalinterfaces.IShoppingCartSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;



public class CustomerSubsystemFacade implements ICustomerSubsystem {
    
    IShoppingCartSubsystem shoppingCartSubsystem;
    IOrderSubsystem orderSubsystem;
    List<IOrder> orderHistory;
    Address defaultShipAddress;
    Address defaultBillAddress;
    CreditCard defaultPaymentInfo;
    CustomerProfile customerProfile;
 
    public void initializeCustomer(String id) throws DatabaseException {
    	loadCustomerProfile(id);
        
        loadDefaultShipAddress();
        loadDefaultBillAddress();
        loadDefaultPaymentInfo();
        
		
		//get the user's saved cart from the database and store in the Customer -- 
		//he may or may not decide to use it
        shoppingCartSubsystem = ShoppingCartSubsystemFacade.INSTANCE;
        shoppingCartSubsystem.setCustomerProfile(customerProfile);
        shoppingCartSubsystem.retrieveSavedCart();
		
        //retrieve the order history for the customer and store here
        orderSubsystem = new OrderSubsystemFacade(customerProfile);
        orderHistory = orderSubsystem.getOrderHistory();
        
    }
	void loadCustomerProfile(String custId) throws DatabaseException {
		DbClassCustomerProfile dbclass = new DbClassCustomerProfile();
		dbclass.readCustomerProfile(custId);
		customerProfile = dbclass.getCustomerProfile();
	}    
    void loadDefaultShipAddress() throws DatabaseException {
		//implement
		//replace this line with the real default ship address
        defaultShipAddress = new Address();
    }
    
    void loadDefaultBillAddress() throws DatabaseException {
		//implement
		//replace this line with the real default ship address
        defaultBillAddress = new Address();
        
    }
    
    void loadDefaultPaymentInfo() throws DatabaseException {
		//implement
		//replace this line with real data
        defaultPaymentInfo = new CreditCard("","","","");
               
    }
    public IAddress createAddress(String street, String city, String state, String zip) {
        return new Address(street,city,state,zip);
    }
 
    
	/**
	 * Return an (unmodifiable) copy of the order history.
	 */
    public List<IOrder> getOrderHistory() {
    	return Collections.unmodifiableList(orderHistory);
    }

	/** Sample of how a save is done */
    public void saveNewAddress(IAddress addr) throws DatabaseException {
        DbClassAddress dbClass = new DbClassAddress();
        dbClass.setAddress(addr);
        dbClass.saveAddress(customerProfile);
        
    }
    /**
     * sample of how a read is done
     */
    public List<IAddress> getAllAddresses() throws DatabaseException {
        DbClassAddress dbClass = new DbClassAddress();
        dbClass.readAllAddresses(customerProfile);
        return Collections.unmodifiableList(dbClass.getAddressList());
    }

    public IAddress runAddressRules(IAddress addr) throws RuleException, EBazaarException {
       	
		IRules transferObject = new RulesAddress(addr);
		transferObject.runRules();
			    	
    	//updates are in the form of a List; 0th object is the necessary Address
    	Address update = (Address)transferObject.getUpdates().get(0);
    	return update;
    }
	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public static final long serialVersionUID = 805820108;

}
