
package business.shoppingcartsubsystem;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.externalinterfaces.DbConfigKey;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import business.externalinterfaces.ICartItem;


public class DbClassShoppingCart implements IDbClass {
    IDataAccessSubsystem dataAccess;
    ShoppingCart cart;
    List<ICartItem> cartItemsList;
    String custId;
    String cartId;
    String query;
    final String GET_ID="GetId";
    final String GET_SAVED_ITEMS="GetSavedItems";
    String queryType;
    public void buildQuery() {
        if(queryType.equals(GET_ID)){
            buildGetIdQuery();
        }
        else if(queryType.equals(GET_SAVED_ITEMS)){
            buildGetSavedItemsQuery();
        }
        
    }
    private void buildGetIdQuery(){
    	//implement
        query = "";
    }
    
    private void buildGetSavedItemsQuery() {
    	//implement
        query = "";
        
    }
    
    public String getShoppingCartId(String custId) throws DatabaseException {
        this.custId = custId;
        //implement
        return "1";
    }
    public List<ICartItem> getSavedCartItems(String cartId) throws DatabaseException {
        this.cartId= cartId;
        //implement 
        return new LinkedList<ICartItem>();
        
    }


    public void populateEntity(ResultSet resultSet) throws DatabaseException {
        if(queryType.equals(GET_ID)) {
            populateShopCartId(resultSet);
        }
        else if(queryType.equals(GET_SAVED_ITEMS)){
            populateCartItemsList(resultSet);
        }
        
    }
    private void populateShopCartId(ResultSet rs){
        //implement
        
    }
    private void populateCartItemsList(ResultSet rs) throws DatabaseException {
    	//implement
    }

 
    public String getDbUrl() {
    	DbConfigProperties props = new DbConfigProperties();	
    	return props.getProperty(DbConfigKey.ACCOUNT_DB_URL.getVal());
    }

    public String getQuery() {
        return query;
    }
 
}
