
package business.externalinterfaces;

import java.util.List;


public interface IOrder {
    @SuppressWarnings("unchecked")
	public List getOrderItems();
    
	public String getOrderDate();
		
	public String getOrderId();
		
	public String getTotalPrice();
		
	
}



