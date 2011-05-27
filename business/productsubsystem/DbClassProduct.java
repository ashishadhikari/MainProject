
package business.productsubsystem;

import java.sql.ResultSet;

import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.DbConfigKey;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import business.externalinterfaces.IProductFromDb;
import business.util.TwoKeyHashMap;

class DbClassProduct implements IDbClass {
    private IDataAccessSubsystem dataAccess;
	/**
	 * the productTable matches product ID with
	 * Product object. It is static so that requests
	 * for "read product" based on product ID can
	 * be handled without extra db hits
	 */
    private static TwoKeyHashMap<String,String,IProductFromDb> productTable;
  
    private String queryType;
    private String query;
    private final String LOAD_PROD_TABLE = "LoadProdTable";
    public void buildQuery() {
        if(queryType.equals(LOAD_PROD_TABLE)){
            buildProdTableQuery();
        }
        
    }
    private void buildProdTableQuery() {
        query = "SELECT * FROM product";
    }
    public TwoKeyHashMap<String,String,IProductFromDb> readProductTable() throws DatabaseException {
		if(productTable != null){
			return productTable.clone();
		}
		return refreshProductTable();
    }
	/**
	 * Force a database call
	 */
	public TwoKeyHashMap<String,String,IProductFromDb> refreshProductTable() throws DatabaseException {
        queryType = LOAD_PROD_TABLE;
        dataAccess = new DataAccessSubsystemFacade();
        dataAccess.read(this);
		//return a clone since productTable must not be corrupted
        return productTable.clone();
        
    }	

    /* (non-Javadoc)
     * @see middleware.externalinterfaces.IDbClass#populateEntity(java.sql.ResultSet)
     */
    public void populateEntity(ResultSet resultSet) throws DatabaseException {
        if(queryType.equals(LOAD_PROD_TABLE)){
            populateProdTable(resultSet);
        }
        
    }
    private void populateProdTable(ResultSet rs) throws DatabaseException {
        productTable = new TwoKeyHashMap<String,String,IProductFromDb>();
        //implement
        
        
    }

    public String getDbUrl() {
    	DbConfigProperties props = new DbConfigProperties();	
    	return props.getProperty(DbConfigKey.PRODUCT_DB_URL.getVal());
        
    }
    public String getQuery() {
        
        return query;
    }

}
