package business.productsubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;

import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.DbConfigKey;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import business.externalinterfaces.IProductFromDb;
import business.externalinterfaces.IProductFromGui;
import business.util.TwoKeyHashMap;

class DbClassProduct implements IDbClass {
	private IDataAccessSubsystem dataAccess;
	/**
	 * the productTable matches product ID with Product object. It is static so
	 * that requests for "read product" based on product ID can be handled
	 * without extra db hits
	 */
	private static TwoKeyHashMap<String, String, IProductFromDb> productTable;

	private String queryType;
	private String query;
	private final String LOAD_PROD_TABLE = "LoadProdTable";
	private final String LOAD_PROD_IDS = "LoadProdIds";
	private final String SAVE_PROD_TABLE = "SaveProdTable";
	//private final String LOAD_CAT_TABLE = "LoadCatTable";
	private final String SAVE_CAT_TABLE = "SaveCatTable";
	private String productId;
	
	// Product related variables
	private String name;
	private String quantity;
	private String price;
	private String date;
	private String desc;
	private int catid;
	
	// Catalog related variables
	private String catalogName;

	/**
	 * We are using this class also for saving, adding and deleting the Catalogs
	 * other than products Generally this is not a good Idea and we could have
	 * Created another class "DbClassCatalog.java" in order to perform the
	 * database operations with Catalogs
	 */
	public void buildQuery() {
		if (queryType.equals(LOAD_PROD_TABLE)) {
			buildProdTableQuery();
		}
		if (queryType.equals(LOAD_PROD_IDS)) {
			buildProdIdTableQuery();
		}
		
		if(queryType.equals(SAVE_PROD_TABLE)){
			buildSaveProductQuery();
		}
		if(queryType.equals(SAVE_CAT_TABLE)){
			buildSaveCatalogQuery();
		}
	}

	private void buildSaveCatalogQuery() {
		// TODO Auto-generated method stub
		query = "INSERT INTO CatalogType values('','" + catalogName + "')";
	}

	private void buildSaveProductQuery() {
		// TODO Auto-generated method stub
		query = "INSERT INTO Product values(''," +catid+ ",'" + name+ "'," + quantity + "," + price + ",'" + date + "','" + desc+ "')";
		
	}

	private void buildProdIdTableQuery() {
		// TODO Auto-generated method stub
		query = "SELECT * from Product where productid = "+this.productId;
	}

	/**
	 * Saves the Catalog information to the database
	 * 
	 * @param name
	 */
	public void buildSaveCatalogQuery(String catalogName) {
		queryType = SAVE_CAT_TABLE;
		this.catalogName = catalogName;
		dataAccess = DataAccessSubsystemFacade.INSTANCE;
		try {
			dataAccess.save(this);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Saves the product information to the database
	 * 
	 * @param pro
	 * @param str
	 */
	public void saveProductData(IProductFromGui pro, String str) {
		this.name = pro.getProductName();
		this.quantity = pro.getQuantityAvail();
		this.price = pro.getUnitPrice();
		this.date = pro.getMfgDate();
		this.desc = pro.getDescription();
		this.catid = Integer.parseInt(str);
		queryType = SAVE_PROD_TABLE;
		
		dataAccess = DataAccessSubsystemFacade.INSTANCE;
		try {
			dataAccess.save(this);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getProductFromId(String prodId) {
		this.queryType = LOAD_PROD_IDS;
		this.productId = prodId;
		try {
			DataAccessSubsystemFacade.INSTANCE.read(this);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildProdTableQuery() {
		query = "SELECT * FROM Product";
	}

	public TwoKeyHashMap<String, String, IProductFromDb> readProductTable()
			throws DatabaseException {
		if (productTable != null) {
			return productTable.clone();
		}
		return refreshProductTable();
	}

	/**
	 * Force a database call
	 */
	public TwoKeyHashMap<String, String, IProductFromDb> refreshProductTable()
			throws DatabaseException {
		queryType = LOAD_PROD_TABLE;
		dataAccess = DataAccessSubsystemFacade.INSTANCE;
		dataAccess.read(this);
		// return a clone since productTable must not be corrupted
		return productTable.clone();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * middleware.externalinterfaces.IDbClass#populateEntity(java.sql.ResultSet)
	 */
	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		if (queryType.equals(LOAD_PROD_TABLE)) {
			populateProdTable(resultSet);
		}

	}

	private void populateProdTable(ResultSet rs) throws DatabaseException {
		productTable = new TwoKeyHashMap<String, String, IProductFromDb>();
		// implement
		try {
			while(rs.next()){
				String productid = rs.getString("productid");
				String catalogid = rs.getString("catalogid");
				String productname = rs.getString("productname");
				String totalquantity = rs.getString("totalquantity");
				String priceperunit = rs.getString("priceperunit");
				String mfgdate = rs.getString("mfgdate");
				String description = rs.getString("description");
			   
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDbUrl() {
		DbConfigProperties props = new DbConfigProperties();
		return props.getProperty(DbConfigKey.PRODUCT_DB_URL.getVal());

	}

	public String getQuery() {

		return query;
	}

}