
package business.ordersubsystem;

import java.util.List;

import business.externalinterfaces.IOrder;


class Order implements IOrder{
    private String orderId;
    private String orderDate;
    private String totalPrice;
    @SuppressWarnings("unchecked")
	private List orderItems;
    
    Order(String orderId,String orderDate,String totalPrice){
        
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
    @SuppressWarnings("unchecked")
	public void setOrderItems(List orderItems){
    	this.orderItems = orderItems;
    }
    @SuppressWarnings("unchecked")
	public List getOrderItems(){
        return orderItems;
    }

	public String getOrderDate() {
		return orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getTotalPrice() {
		return totalPrice;
	}
}
