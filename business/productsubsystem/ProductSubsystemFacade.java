/*
 * Created on Mar 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package business.productsubsystem;

import business.DbClassQuantity;
import business.Quantity;
import business.externalinterfaces.IProductFromDb;
import business.externalinterfaces.IProductFromGui;
import business.externalinterfaces.IProductSubsystem;
import business.util.TwoKeyHashMap;
import middleware.DatabaseException;

public class ProductSubsystemFacade implements IProductSubsystem {

	public TwoKeyHashMap<String, String, IProductFromDb> getProductTable()
			throws DatabaseException {
		DbClassProduct dbClass = new DbClassProduct();
		return dbClass.readProductTable();

	}

	/* reads quantity avail and stores in the Quantity argument */
	public void readQuantityAvailable(String prodName, Quantity quantity)
			throws DatabaseException {
		DbClassQuantity dbclass = new DbClassQuantity();
		dbclass.setQuantity(quantity);
		dbclass.readQuantityAvail(prodName);

				
	}    
	
	/** creates an IProductFromGui when user creates a product */
	public IProductFromGui createProduct(String productId, String productName, String quantity, String uintPrice,String mfgDate, String catalogId, String description){
		Product p = new Product(productId,  productName,  quantity,  uintPrice,  mfgDate,  catalogId,  description);
		return p;
	}
	
//note: don't invoke rules for quantity from here
	
	public void saveNewProduct(IProductFromGui product, String catalogType) throws DatabaseException{
		
		DbClassProduct dbClass = new DbClassProduct();
		dbClass.saveProductData(product,catalogType);
		
	}
	
	public void saveNewCatalogName(String name) throws DatabaseException{
		DbClassProduct dbClass = new DbClassProduct();
		dbClass.buildSaveCatalogQuery(name);
	}
	
	public IProductFromDb getProductFromId(String prodId) throws DatabaseException{
		DbClassProduct dbClass = new DbClassProduct();
		
		dbClass.getProductFromId(prodId);
		return null;
		
	}

}