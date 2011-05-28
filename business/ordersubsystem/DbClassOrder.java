
package business.ordersubsystem;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.DbConfigKey;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import business.externalinterfaces.ICustomerProfile;
import business.externalinterfaces.IOrderItem;

class DbClassOrder implements IDbClass {
    private String query;
    private String queryType;
    private final String GET_ORDER_ITEMS = "GetOrderItems";
    private final String GET_ORDER_IDS = "GetOrderIds";
    private final String GET_ORDER_DATA = "GetOrderData";
    private ICustomerProfile customerProfile;
    private String orderId;
    private List<String> orderIds;
    private List<IOrderItem> orderItems;
    private Order orderData;    
    
    public List<String> getAllOrderIds(ICustomerProfile customerProfile) throws DatabaseException {
        //implement
    	this.customerProfile=customerProfile;
    	orderIds = new LinkedList<String>();
    	queryType = GET_ORDER_IDS;
    	DataAccessSubsystemFacade.INSTANCE.read(this);
    	System.out.println("Printing Order Ids");
    	System.out.println(orderIds);
    	return orderIds;
        
        
    }
    public Order getOrderData(String orderId) throws DatabaseException {
    	//implement
    	orderData = new Order("","","");
    	return orderData;
    }
    
    public List<IOrderItem> getOrderItems(String orderId) throws DatabaseException {
        
        orderItems = new LinkedList<IOrderItem>();
        return orderItems;
        
    }
    public void buildQuery() {
        if(queryType.equals(GET_ORDER_ITEMS)){
            buildGetOrderItemsQuery();
        }
        else if(queryType.equals(GET_ORDER_IDS)){
            
            buildGetOrderIdsQuery();
        }
        else if(queryType.equals(GET_ORDER_DATA)){
        	buildGetOrderDataQuery();
        }

        
    }
    private void buildGetOrderDataQuery() {
        query = "SELECT orderdate, totalpriceamount FROM Ord WHERE orderid = '"+orderId+"'";
    
        
    }
    

    private void buildGetOrderIdsQuery() {
        query = "SELECT orderid FROM Ord WHERE custid = '"+customerProfile.getCustId()+"'";
    
        
    }
    private void buildGetOrderItemsQuery() {
        query = "SELECT * FROM OrderItem WHERE orderid = '"+orderId+"'";
    
    }
    private void populateOrderItems(ResultSet rs) throws DatabaseException {
        orderItems = new LinkedList<IOrderItem>();
        //implement
    }
    private void populateOrderIds(ResultSet resultSet) throws DatabaseException {
        orderIds = new LinkedList<String>();
        //implement
    }
    private void populateOrderData(ResultSet resultSet) throws DatabaseException {
    	//implement
    }    
 
    public void populateEntity(ResultSet resultSet) throws DatabaseException {
        if(queryType.equals(GET_ORDER_ITEMS)){
            populateOrderItems(resultSet);
        }
        else if(queryType.equals(GET_ORDER_IDS)){
            populateOrderIds(resultSet);
        }
        else if(queryType.equals(GET_ORDER_DATA)){
        	populateOrderData(resultSet);
        }
        
    }
    public String getDbUrl() {
    	DbConfigProperties props = new DbConfigProperties();	
    	return props.getProperty(DbConfigKey.ACCOUNT_DB_URL.getVal());
        
    }
    public String getQuery() {
        return query;
    }
    
    
    public void setOrderId(String orderId){
        this.orderId = orderId;
        
    }

    
}
