package business.ordersubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.DbConfigKey;
import middleware.externalinterfaces.IDbClass;
import business.externalinterfaces.ICustomerProfile;
import business.externalinterfaces.IOrder;
import business.externalinterfaces.IOrderItem;

class DbClassOrder implements IDbClass {
	private String query;
	private String queryType;
	private final String GET_ORDER_ITEMS = "GetOrderItems";
	private final String GET_ORDER_IDS = "GetOrderIds";
	private final String GET_ORDER_DATA = "GetOrderData";
	private final String SAVE_ORDER = "SaveOrder";
	private ICustomerProfile customerProfile;
	private String orderId;
	private List<String> orderIds;
	private List<IOrderItem> orderItems;
	private IOrder orderData;

	public DbClassOrder() {
	}
	
	public DbClassOrder(IOrder order, ICustomerProfile customerProfile) {
		this.orderData = order;
		this.customerProfile = customerProfile;
	}
	
	public void submitOrder() throws DatabaseException {
		this.queryType = SAVE_ORDER;
		DataAccessSubsystemFacade.INSTANCE.save(this);
	}
	
	public List<String> getAllOrderIds(ICustomerProfile customerProfile)
			throws DatabaseException {
		// implement -- finished
		this.customerProfile = customerProfile;
		this.queryType = GET_ORDER_IDS;
		DataAccessSubsystemFacade.INSTANCE.read(this);
		return orderIds;

	}

	public IOrder getOrderData(String orderId) throws DatabaseException {
		// implement -- finished
		this.orderId = orderId;
		this.queryType = GET_ORDER_DATA;
		DataAccessSubsystemFacade.INSTANCE.read(this);
		return orderData;
	}

	public List<IOrderItem> getOrderItems(String orderId)
			throws DatabaseException {
		// implement -- finished
		this.orderId = orderId;
		this.queryType = GET_ORDER_ITEMS;
		DataAccessSubsystemFacade.INSTANCE.read(this);
		return orderItems;

	}

	public void buildQuery() {
		if (queryType.equals(GET_ORDER_ITEMS)) {
			buildGetOrderItemsQuery();
		} else if (queryType.equals(GET_ORDER_IDS)) {

			buildGetOrderIdsQuery();
		} else if (queryType.equals(GET_ORDER_DATA)) {
			buildGetOrderDataQuery();
		} else if (queryType.equals(SAVE_ORDER)) {
			buildSaveOrderQuery();
		}

	}

	private void buildSaveOrderQuery() {
		//TODO copy and paste from Rajesh
		query = "INSERT INTO Ord VALUES();";
		
	}

	private void buildGetOrderDataQuery() {
		query = "SELECT orderdate, totalpriceamount FROM Ord WHERE orderid = '"
				+ orderId + "'";

	}

	private void buildGetOrderIdsQuery() {
		query = "SELECT orderid FROM Ord WHERE custid = '"
				+ customerProfile.getCustId() + "'";

	}

	private void buildGetOrderItemsQuery() {
		query = "SELECT * FROM OrderItem WHERE orderid = '" + orderId + "'";

	}

	private void populateOrderItems(ResultSet rs) throws DatabaseException {
		orderItems = new LinkedList<IOrderItem>();
		// implement -- finished
		if (rs != null) {
			try {
				while (rs.next()) {
					String lineitemid = rs.getString("orderitemid");
					String productid = rs.getString("productid");
					String orderid = rs.getString("orderid");
					String quantity = rs.getString("quantity");
					String totalPrice = rs.getString("totalprice");
					orderItems.add(new OrderItem(lineitemid, productid,
							orderid, quantity, totalPrice));
				}
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}

	}

	private void populateOrderIds(ResultSet rs) throws DatabaseException {
		orderIds = new LinkedList<String>();
		// implement -- finished
		if (rs != null) {
			try {
				while (rs.next()) {
					orderIds.add(rs.getString("orderid"));
				}
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
	}

	private void populateOrderData(ResultSet rs) throws DatabaseException {
		// implement -- finished
		if (rs != null) {
			try {
				while (rs.next()) {
					String orderDate = rs.getString("orderdate");
					String totalPrice = rs.getString("totalpriceamount");
					orderData = new Order(this.orderId, orderDate, totalPrice);
				}
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
	}

	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		if (queryType.equals(GET_ORDER_ITEMS)) {
			populateOrderItems(resultSet);
		} else if (queryType.equals(GET_ORDER_IDS)) {
			populateOrderIds(resultSet);
		} else if (queryType.equals(GET_ORDER_DATA)) {
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

	public void setOrderId(String orderId) {
		this.orderId = orderId;

	}

}
