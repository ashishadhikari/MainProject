
package business.ordersubsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import middleware.DatabaseException;
import business.externalinterfaces.ICustomerProfile;
import business.externalinterfaces.IOrder;
import business.externalinterfaces.IOrderItem;
import business.externalinterfaces.IOrderSubsystem;


public class OrderSubsystemFacade implements IOrderSubsystem {
	Logger log = Logger.getLogger(this.getClass().getPackage().getName());
    ICustomerProfile customerProfile;
    
    public OrderSubsystemFacade(ICustomerProfile customerProfile){
        this.customerProfile = customerProfile;
    }

    List<String> getAllOrderIds() throws DatabaseException {
        
        DbClassOrder dbClass = new DbClassOrder();
        return dbClass.getAllOrderIds(customerProfile);
        
    }
    List<IOrderItem> getOrderItems(String orderId) throws DatabaseException {
        //need to implement
		return new ArrayList<IOrderItem>();
    }
    
    Order getOrderData(String orderId) throws DatabaseException {
		//need to implement
    	return new Order("","","");
    }
    
    
    public List<IOrder> getOrderHistory() throws DatabaseException {
		//implement 
        return new ArrayList<IOrder>();
        
    }

        
 
}
