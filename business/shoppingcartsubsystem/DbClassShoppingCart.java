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
import business.externalinterfaces.IShoppingCart;

public class DbClassShoppingCart implements IDbClass {
	IDataAccessSubsystem dataAccess;
	IShoppingCart cart;
	List<ICartItem> cartItemsList;
	String custId;
	String cartId;
	String query;
	final String GET_ID = "GetId";
	final String GET_SAVED_ITEMS = "GetSavedItems";
	final String SAVE_LIVE_CART = "SaveLiveCart";
	String queryType;

	public DbClassShoppingCart() {
	}

	public DbClassShoppingCart(IShoppingCart cart) {
		this.cart = cart;
	}

	public void buildQuery() {
		if (queryType.equals(GET_ID)) {
			buildGetIdQuery();
		} else if (queryType.equals(GET_SAVED_ITEMS)) {
			buildGetSavedItemsQuery();
		} else if (queryType.equals(SAVE_LIVE_CART)) {
			buildSaveLiveCartQuery();
		}

	}

	private void buildSaveLiveCartQuery() {
		// implement
		query = "";
	}

	private void buildGetIdQuery() {
		query = "SELECT shopcartid FROM ShopCartTbl WHERE custid = '" + custId + "';";
	}

	private void buildGetSavedItemsQuery() {
		// implement
		query = "";

	}

	public String getShoppingCartId(String custId) throws DatabaseException {
		this.custId = custId;
		queryType = GET_ID;
		// implement
		return "1";
	}

	public List<ICartItem> getSavedCartItems(String cartId)
			throws DatabaseException {
		this.cartId = cartId;
		queryType = GET_SAVED_ITEMS;
		// implement
		return new LinkedList<ICartItem>();

	}

	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		if (queryType.equals(GET_ID)) {
			populateShopCartId(resultSet);
		} else if (queryType.equals(GET_SAVED_ITEMS)) {
			populateCartItemsList(resultSet);
		}

	}

	private void populateShopCartId(ResultSet rs) {
		// implement

	}

	private void populateCartItemsList(ResultSet rs) throws DatabaseException {
		// implement
	}

	public String getDbUrl() {
		DbConfigProperties props = new DbConfigProperties();
		return props.getProperty(DbConfigKey.ACCOUNT_DB_URL.getVal());
	}

	public String getQuery() {
		return query;
	}

}
