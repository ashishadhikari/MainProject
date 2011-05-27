
package business.shoppingcartsubsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import middleware.DatabaseException;
import business.externalinterfaces.ICartItem;
import business.externalinterfaces.ICustomerProfile;
import business.externalinterfaces.IShoppingCartSubsystem;


public enum ShoppingCartSubsystemFacade implements IShoppingCartSubsystem {
	//This makes ShoppingCartSubsystemFacade a singleton (see Effective Java
	//2nd ed.). This approach ensures that the current state of the shopping
	//cart is globally accessible. This is important to do because 
	//at certain times (i.e. after login) this subsystem is under the
	//control of Customer Subsystem, and at other times (i.e. during browse and
	//select) it is not. 
	INSTANCE;
    ShoppingCart liveCart;
    ShoppingCart savedCart;
    String shopCartId;
    ICustomerProfile customerProfile;
	Logger log = Logger.getLogger(this.getClass().getPackage().getName());
    
    //interface methods
	public void setCustomerProfile(ICustomerProfile customerProfile){
		this.customerProfile=customerProfile;
	}
	
    @SuppressWarnings("unchecked")
	public void retrieveSavedCart() throws DatabaseException {
        
        String val = getShoppingCartId();
        if(val != null){
            shopCartId = val;
            log.info("cart id: "+shopCartId);
            List items = getCartItems(shopCartId);
            log.info("list of items: "+items);
            savedCart = new ShoppingCart(items);
        }
        
        
    }
    
    
    
    //supporting methods
    
    String getShoppingCartId() throws DatabaseException {
		//implement -- read database; pass in customerProfile
    	//so that the sql query can extract custId in order to
    	//find this customer's shopping cart id
		 return "1";
    }
    
    List<CartItem> getCartItems(String shopCartId) throws DatabaseException {
		//implement

        return new ArrayList<CartItem>();
    }

    private ShoppingCartSubsystemFacade(){
        liveCart = new ShoppingCart(new ArrayList<ICartItem>());
        savedCart = new ShoppingCart(new ArrayList<ICartItem>());
    }
}
